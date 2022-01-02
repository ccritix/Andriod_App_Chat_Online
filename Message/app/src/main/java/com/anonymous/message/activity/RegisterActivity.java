package com.anonymous.message.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.anonymous.message.network.NetworkManager;
import com.anonymous.message.R;
import com.anonymous.message.network.API;
import com.anonymous.message.network.Add;
import com.anonymous.message.network.Result;
import com.anonymous.message.network.UserAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilUserRegister, tilPasswordRegister, tilConfirmPassword, tilPhone;
    private TextInputEditText UserRegister, PasswordRegister, ConfirmPassword, Phone;
    private TextView tvError;

    private UserAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initBase();
    }

    private void initBase() {
        tilUserRegister = findViewById(R.id.tilUserRegister);
        tilPasswordRegister = findViewById(R.id.tilPasswordRegister);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        tilPhone = findViewById(R.id.tilPhone);
        UserRegister = findViewById(R.id.UserRegister);
        PasswordRegister = findViewById(R.id.PasswordRegister);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        Phone = findViewById(R.id.Phone);
        tvError = findViewById(R.id.tvErrorRegister);

        UserRegister.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvError.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        PasswordRegister.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvError.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        ConfirmPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvError.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        Phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvError.setVisibility(View.INVISIBLE);
                return false;
            }
        });
    }

    public void startRegister(View view) {
        api = API.getUserAPI();

        String username = tilUserRegister.getEditText().getText().toString();
        String password = tilPasswordRegister.getEditText().getText().toString();
        String confirm = tilConfirmPassword.getEditText().getText().toString();
        String phone = tilPhone.getEditText().getText().toString();
        if (NetworkManager.isOnline(this)){
            if (checkValid(username, password, confirm, phone)){
                api.addUser(username, password, phone).enqueue(new Callback<Add>() {
                    @Override
                    public void onResponse(Call<Add> call, Response<Add> response) {
                        if (response.isSuccessful()){
                            Add result = response.body();
                            if (result.success){
                                createMessage(username);
                                tvError.setVisibility(View.VISIBLE);
                                tvError.setText("Đăng ký thành công! Click vào đăng nhập");
                            }else {
                                tvError.setVisibility(View.VISIBLE);
                                tvError.setText("Tên tài khoản đã tồn tại");
                                UserRegister.requestFocus();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Add> call, Throwable t) {
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText("Lỗi đăng ký! Vui lòng thử lại sau");
                    }
                });
            }
        }else {
            displayNetworkDialog();
        }
    }

    private void createMessage(String username) {
        if (NetworkManager.isOnline(this)){
            api.createRoom(username).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()){
                        Result result = response.body();
                        if (result.success){
                            Log.e("TAG", "Success");
                        }else {
                            Log.e("TAG", "Fail: " + result.message);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.e("TAG", "onFail: " + t.getMessage());
                }
            });
        }else {
            displayNetworkDialog();
        }
    }

    private boolean checkValid(String username, String password, String confirm, String phone) {
        if (username.length() == 0){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Vui lòng nhập tên tài khoản");
            UserRegister.requestFocus();
            return false;
        }else if (password.length() == 0){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Vui lòng nhập mật khẩu");
            PasswordRegister.requestFocus();
            return false;
        }else if (confirm.length() == 0) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Vui lòng xác nhận lại mật khẩu");
            ConfirmPassword.requestFocus();
            return false;
        }else if (phone.length() == 0) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Vui lòng nhập số điện thoại");
            Phone.requestFocus();
            return false;
        }else if (password.length() < 6){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Mật khẩu phải từ 6 ký tự trở lên");
            PasswordRegister.requestFocus();
            return false;
        }else if (!password.equals(confirm)){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("Mật khẩu xác nhận không trùng khớp");
            ConfirmPassword.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    public void startLogin(View view) {
        if (NetworkManager.isOnline(this)) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            displayNetworkDialog();
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
}
