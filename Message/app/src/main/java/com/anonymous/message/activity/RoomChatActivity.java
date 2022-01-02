package com.anonymous.message.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.anonymous.message.MessageRoom;
import com.anonymous.message.network.NetworkManager;
import com.anonymous.message.R;
import com.anonymous.message.Setting;
import com.anonymous.message.adapter.MessageAdapter;
import com.anonymous.message.database.MessageDAO;
import com.anonymous.message.database.MyDatabase;
import com.anonymous.message.network.API;
import com.anonymous.message.network.Add;
import com.anonymous.message.network.LoadMesssage;
import com.anonymous.message.network.UserAPI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomChatActivity extends AppCompatActivity {

    private String user1 = "";
    private String user2 = "";
    private String phone = "";
    private RecyclerView recyclerChat;
    private ArrayList<MessageRoom> messageRooms = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private UserAPI api;
    private Setting setting;
    private Timer timer;

    private EditText txtSend;

    private MyDatabase db;
    private MessageDAO dao;

    private static final int SELECT_PICTURE = 101;
    public static final String IMAGE_TYPE = "image/*";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomchat_activity);

        initBase();

    }

    private void initBase() {

        txtSend = findViewById(R.id.txtSend);
        Intent intent = getIntent();
        user2 = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        this.setTitle(user2);
        api = API.getUserAPI();
        setting = new Setting(this);

        recyclerChat = findViewById(R.id.recyclerChat);
        messageAdapter = new MessageAdapter(this, messageRooms);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerChat.setLayoutManager(manager);
        recyclerChat.setHasFixedSize(true);
        recyclerChat.setAdapter(messageAdapter);
        user1 = setting.getCurrentUser();

        db = Room.databaseBuilder(this, MyDatabase.class,"MessageRoom.db").allowMainThreadQueries().build();
        dao = db.getMessageDAO();

        timer = new Timer();
        if (NetworkManager.isOnline(RoomChatActivity.this)){
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (NetworkManager.isOnline(RoomChatActivity.this)) {
                                loadMessages();
                            }
                        }
                    });
                }
            }, 0, 1000);
        }else {
            messageRooms.clear();
            for (MessageRoom i:dao.getAll()){
                if (i.getUser2().equals(user2)){
                    messageRooms.add(i);
                }
            }
            messageAdapter.notifyDataSetChanged();
            recyclerChat.scrollToPosition(messageRooms.size()-1);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //intent = new Intent(RoomChatActivity.this, NotificationService.class);
        //intent.setAction("STOP");
        //startService(intent);
    }

    private void loadMessages() {
        if (NetworkManager.isOnline(this)) {
            Call<LoadMesssage> call = api.getMessage(user1, user2);
            call.enqueue(new Callback<LoadMesssage>() {
                @Override
                public void onResponse(Call<LoadMesssage> call, Response<LoadMesssage> response) {
                    if (response.isSuccessful()) {
                        LoadMesssage result = response.body();
                        if (result != null && result.success == true) {
                            List<MessageRoom> st = result.messageRooms;

                            dao.removeAllTable(user2);
                            for (int i = 0; i < st.size(); i++){
                                MessageRoom messageRoom = st.get(i);
                                dao.insert(messageRoom);
                            }

                            if(st.size() != messageRooms.size()){
                                messageRooms.clear();
                                messageAdapter.notifyDataSetChanged();
                                messageRooms.addAll(st);
                                messageAdapter.notifyDataSetChanged();
                                recyclerChat.scrollToPosition(messageRooms.size()-1);
                            }
                        } else {
                            Log.e("TAG", result.message);
                        }
                    } else {

                        Toast.makeText(RoomChatActivity.this, "Fail: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoadMesssage> call, Throwable t) {
                    Toast.makeText(RoomChatActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            displayNetworkDialog();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_room, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_profile:
                Intent intent = new Intent(RoomChatActivity.this, ProfileActivity.class);
                intent.putExtra("name", user2);
                intent.putExtra("phone", phone);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayNetworkDialog() {
        new AlertDialog.Builder(this).setTitle("No network")
                .setMessage("Your devices dose not to a netwwork")
                .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        startActivityForResult(intent, 1);
                    }
                })
                .setNegativeButton("OK", null)
                .setCancelable(false)
                .create().show();
    }


    public void addMessage(String user1, String user2, String message1, String message2){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        String time = String.format("%02d:%02d", hour, minute);
        api.addMessage(user1, user2, message1, message2, time).enqueue(new Callback<Add>() {
            @Override
            public void onResponse(Call<Add> call, Response<Add> response) {
                if (response.isSuccessful()){
                    Add result = response.body();
                    if (result.success){
                        txtSend.setText("");
                    }else {
                        Toast.makeText(RoomChatActivity.this, "Không thể gửi tin nhắn", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Add> call, Throwable t) {
                Toast.makeText(RoomChatActivity.this, "Lỗi đường truyền", Toast.LENGTH_LONG).show();
            }
        });

        api.addMessage(user2, user1, message2, message1, time).enqueue(new Callback<Add>() {
            @Override
            public void onResponse(Call<Add> call, Response<Add> response) {
                if (response.isSuccessful()){
                    Add result = response.body();
                    if (!result.success){
                        Toast.makeText(RoomChatActivity.this, result.message, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Add> call, Throwable t) {
                Toast.makeText(RoomChatActivity.this, "Lỗi đường truyền", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void send(View view) {
        String message = txtSend.getText().toString();
        if (message.length() != 0){
            if (NetworkManager.isOnline(this)){
                addMessage(user1, user2, message, "");
            }else {
                displayNetworkDialog();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Intent service = new  Intent(this, NotificationService.class);
        //service.setAction("START");
        //startService(service);
    }

    /*public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType(IMAGE_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose a picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (SELECT_PICTURE == requestCode && data != null && resultCode == RESULT_OK){
            filePath = data.getData();
            Cursor returnCursor = getContentResolver().query(filePath, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            txtSend.setText(returnCursor.getString(nameIndex));
            upload(returnCursor.getString(nameIndex));
            Log.e("TAG", returnCursor.getString(nameIndex));
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void upload(String string) {

        if (filePath != null){

            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference reference = storageReference.child("images/" + string);
            reference.putFile(filePath).addOnSuccessListener(
                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RoomChatActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RoomChatActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int)progress + "%");
                    }
                });
        };
    }*/
}
