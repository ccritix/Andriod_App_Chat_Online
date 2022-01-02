package com.anonymous.message.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.anonymous.message.network.NetworkManager;
import com.anonymous.message.R;
import com.anonymous.message.Setting;
import com.anonymous.message.network.API;
import com.anonymous.message.network.Data;
import com.anonymous.message.network.UserAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserAPI api;

    private TextInputLayout tilUserName, tilPassword;
    private TextInputEditText UserName, Password;
    private TextView tvError;
    private CheckBox checkBox;

    private Setting setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initBase();
    }

    private void initBase() {
        api = API.getUserAPI();

        setting = new Setting(this);

        tilUserName = findViewById(R.id.tilUserName);
        tilPassword = findViewById(R.id.tilPassword);
        UserName = findViewById(R.id.UserName);
        Password = findViewById(R.id.Password);
        tvError = findViewById(R.id.tvError);
        checkBox = findViewById(R.id.Remember);

        UserName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvError.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        Password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvError.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        String username =  setting.getUsername();
        String password = setting.getPassword();

        if (username.length() != 0 && password.length() != 0){
            UserName.setText(username);
            Password.setText(password);
            checkBox.setChecked(true);
        }
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

    public void login(View view) {
        String username = tilUserName.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        if (NetworkManager.isOnline(this)){
            if (checkLogin(username, password)){
                if (checkBox.isChecked()){
                    setting.setUserName(username);
                    setting.setPassword(password);
                }else {
                    setting.setUserName("");
                    setting.setPassword("");
                }
                api.login(username, password).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        Data result = response.body();
                        if (result!= null && result.success == true){
                            String phone = result.phone;
                            Intent intent = new Intent(LoginActivity.this, MessageActivity.class);
                            intent.putExtra("phone", phone);
                            setting.setCurrentUser(username);
                            setting.setCurrentPassword(password);
                            startActivity(intent);
                            finish();
                        }else {
                            tvError.setVisibility(View.VISIBLE);
                            tvError.setText("Sai tài khoản hoặc mật khẩu");
                            UserName.requestFocus();
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText("Lỗi đăng nhập! Vui lòng thử lại sau");
                    }
                });
            }
        }else {
            displayNetworkDialog();
        }

    }

    private boolean checkLogin(String username, String password) {
        if (username.length() == 0){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Vui lòng nhập tài khoản");
            UserName.requestFocus();
            return false;
        }else if (password.length() == 0){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Vui lòng nhập mật khẩu");
            Password.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public void register(View view) {
        if (NetworkManager.isOnline(this)) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else {
            displayNetworkDialog();
        }
    }
    /*private void deleteStudent(Student student) {
        api.deleteStudent(student.getId()).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response != null && response.body().success){
                    Log.e("TAG", "Delete student success");
                }else {
                    Log.e("TAG", "Delete student fail");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                t.printStackTrace();
                Log.e("TAG", "Update error: " + t.getMessage());
            }
        });
    }*/

    /*private void confirmDelete(Student student, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to remove " + student.getName() + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.deleteItem(position);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(false);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }*/
}