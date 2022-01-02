package com.anonymous.message;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.anonymous.message.activity.LoginActivity;
import com.anonymous.message.activity.MessageActivity;
import com.anonymous.message.activity.RoomChatActivity;
import com.anonymous.message.database.MessageDAO;
import com.anonymous.message.database.MyDatabase;
import com.anonymous.message.database.UserDAO;
import com.anonymous.message.network.API;
import com.anonymous.message.network.Data;
import com.anonymous.message.network.Length;
import com.anonymous.message.network.UserAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {

    public static final String CHANNEL_NAME = "Chat App";
    public static final String CHANNEL_ID = "001";
    Timer timer ;
    private MyDatabase dbUser, dbMessage;
    private UserDAO daoUser;
    private MessageDAO daoMessage;
    private List<User> users = new ArrayList<>();
    private List<MessageRoom> messageRooms = new ArrayList<>();
    private UserAPI api;
    private boolean run = false;
    private Setting setting;
    private NotificationManagerCompat mNotifManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e( "TAG" , "onStartCommand" );
        super.onStartCommand(intent, flags, startId);
        if (intent.getAction().equals("STOP")){
            stopSelf();
        }else {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                if (run){
                    //timer.cancel();
                    stopSelf();
                }else {
                    checkMessage();
                }
                }
            },0, 5000);
        }


        return START_STICKY ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "onCreate service");
        dbUser = Room.databaseBuilder(this, MyDatabase.class,"UserChat.db").allowMainThreadQueries().build();
        daoUser = dbUser.getUserDAO();
        dbMessage = Room.databaseBuilder(this, MyDatabase.class,"MessageRoom.db").allowMainThreadQueries().build();
        daoMessage = dbMessage.getMessageDAO();


        messageRooms.addAll(daoMessage.getAll());
        api = API.getUserAPI();
        setting = new Setting(this);

        timer = new Timer();
        mNotifManager = NotificationManagerCompat.from(this);

        List<User> users1 = new ArrayList<>();
        users1.addAll(daoUser.getAll());
        for (int i = 0; i < users1.size(); i++){
            User user = users1.get(i);
            if (!user.getUsername().equals(setting.getCurrentUser())){
                users.add(user);
            }
        }
    }

    private void checkMessage(){
        int lengthdb = 0;
        for(int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            lengthdb = 0;
            for (int j = 0; j < messageRooms.size(); j++) {
                MessageRoom messageRoom = messageRooms.get(j);
                if (messageRoom.getUser2().equals(user.getUsername())) {
                    lengthdb += 1;
                }
            }
            //Log.e("TAG", user.getUsername());
            //Log.e("TAG", lengthdb +"db");
            int finalLengthdb = lengthdb;
            api.checkLength(setting.getCurrentUser(), user.getUsername()).enqueue(new Callback<Length>() {
                @Override
                public void onResponse(Call<Length> call, Response<Length> response) {
                    Length result = response.body();
                    if (result != null && result.success == true) {
                        int len = result.length;
                        //Log.e("TAG",  len + "web");
                        if (len > finalLengthdb){
                            run = true;
                            createNotification(CHANNEL_NAME, "Notification Chat");
                            setting.setUserNewMessage(user.getUsername());

                        }
                    }
                }

                @Override
                public void onFailure(Call<Length> call, Throwable t) {
                    Log.e("TAG", t.getMessage());
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Log. e ( "TAG" , "onDestroy" ) ;
    }



    private void createNotification (String name, String description) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(description);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        Intent intent = new Intent(NotificationService.this, MessageActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_message)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Bạn có tin nhắn mới")
                .setContentIntent(pIntent)
                .setAutoCancel(true);
        mNotifManager.notify(1, builder.build());
    }
}
