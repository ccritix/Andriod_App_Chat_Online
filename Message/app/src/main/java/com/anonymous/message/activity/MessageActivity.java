package com.anonymous.message.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.anonymous.message.NotificationService;
import com.anonymous.message.adapter.MyAdapter;
import com.anonymous.message.database.MyDatabase;
import com.anonymous.message.network.NetworkManager;
import com.anonymous.message.R;
import com.anonymous.message.Setting;
import com.anonymous.message.User;
import com.anonymous.message.database.UserDAO;
import com.anonymous.message.network.API;
import com.anonymous.message.network.Load;
import com.anonymous.message.network.UserAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<User> users = new ArrayList<>();
    private MyAdapter adapter;
    private UserAPI api;
    private Setting setting;

    private MyDatabase db;
    private UserDAO dao;

    private Timer timer;

    public String userService = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        initBase();
    }

    private void initBase() {
        setting = new Setting(this);

        if (setting.getCurrentUser().length() == 0 && setting.getCurrentPassword().length() == 0){
            Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        api = API.getUserAPI();


        recycler = findViewById(R.id.recycler);

        db = Room.databaseBuilder(this, MyDatabase.class,"UserChat.db").allowMainThreadQueries().build();
        dao = db.getUserDAO();

        adapter = new MyAdapter(this, users);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);

        adapter.setMessageActivity(MessageActivity.this);

        userService = setting.getUserNewMessage();
        //Log.e("TAG", "Main " + userService);

        if (!NetworkManager.isOnline(MessageActivity.this)){
            displayNetworkDialog();
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (NetworkManager.isOnline(MessageActivity.this)){
                            loadUsers();
                        }else {
                            users.clear();
                            for (User i:dao.getAll()){
                                if (!setting.getCurrentUser().equals(i.getUsername())) {
                                    users.add(i);
                                }
                            }

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }, 0, 10000);
        //Log.e("TAG", "CREATE");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.e("TAG", "RESUME");
        Intent intent = new Intent(MessageActivity.this, NotificationService.class);
        intent.setAction("STOP");
        startService(intent);
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

    private void loadUsers(){
        Call<Load> call = api.getUser();
        call.enqueue(new Callback<Load>() {
            @Override
            public void onResponse(Call<Load> call, Response<Load> response) {
                if (response.isSuccessful()){
                    Load result = response.body();
                    if (result!= null && result.success == true){
                        List<User> st = result.users;
                        String current = setting.getCurrentUser();
                        dao.nukeTable();
                        users.clear();

                        for (int i = 0; i < st.size(); i++){
                            User user = st.get(i);
                            dao.insert(user);
                        }

                        for (int i = 0; i < st.size(); i++){
                            User user = st.get(i);
                            if (user.getUsername().equals(current)){
                                st.remove(i);
                            }
                        }
                        users.addAll(st);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(MessageActivity.this, "Empty", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(MessageActivity.this, "Fail: "+ response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Load> call, Throwable t) {
                Toast.makeText(MessageActivity.this, "onFailure: "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent view = new Intent(MessageActivity.this, ProfileActivity.class);
                view.putExtra("name", setting.getCurrentUser());
                view.putExtra("phone", dao.getPhone(setting.getCurrentUser()));
                startActivity(view);
                break;
            case R.id.logout:
                setting.setCurrentUser("");
                setting.setCurrentPassword("");
                Intent intent = new Intent(MessageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.changePassword:
                Intent change = new Intent(MessageActivity.this, ChangeActivity.class);
                change.putExtra("username", setting.getCurrentUser());
                startActivity(change);
                timer.cancel();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "DESTROY");
        Intent service = new  Intent(this, NotificationService.class);
        service.setAction("START");
        startService(service);
    }
}
