package com.anonymous.message.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.anonymous.message.network.NetworkManager;
import com.anonymous.message.R;
import com.anonymous.message.Setting;
import com.anonymous.message.network.API;
import com.anonymous.message.network.Add;
import com.anonymous.message.network.UserAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeActivity extends AppCompatActivity {

    private TextInputLayout tilOldPass, tilNewPass, tilConfirmNew;
    private TextInputEditText OldPass, NewPass, ConfirmPass;
    private TextView tvChange;

    private String username = "";
    private UserAPI api;
    private Setting setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_activity);

        initBase();
    }

    private void initBase() {
        api = API.getUserAPI();

        setting = new Setting(this);

        tilOldPass = findViewById(R.id.tilOldPass);
        tilNewPass = findViewById(R.id.tilNewPass);
        tilConfirmNew = findViewById(R.id.tilConfirmNew);
        OldPass = findViewById(R.id.OldPass);
        NewPass = findViewById(R.id.NewPass);
        ConfirmPass = findViewById(R.id.ConfirmNew);
        tvChange = findViewById(R.id.tvChange);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        OldPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvChange.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        NewPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvChange.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        ConfirmPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                tvChange.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void change(View view) {
        String oldpass = tilOldPass.getEditText().getText().toString();
        String newpass = tilNewPass.getEditText().getText().toString();
        String confirmpass = tilConfirmNew.getEditText().getText().toString();

        if (NetworkManager.isOnline(this)){
            if (checkValid(oldpass, newpass, confirmpass)){
                api.updateUser(username, oldpass, newpass).enqueue(new Callback<Add>() {
                    @Override
                    public void onResponse(Call<Add> call, Response<Add> response) {
                        if (response.isSuccessful()){
                            Add result = response.body();
                            if (result.success){
                                tvChange.setVisibility(View.VISIBLE);
                                tvChange.setText("Đổi mật khẩu thành công! Hãy đăng nhập lại");
                                setting.setPassword("");
                                setting.setUserName("");
                            }else {
                                tvChange.setVisibility(View.VISIBLE);
                                tvChange.setText("Mật khẩu hiện tại không chính xác");
                                OldPass.requestFocus();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Add> call, Throwable t) {
                        tvChange.setVisibility(View.VISIBLE);
                        tvChange.setText("Lỗi cập nhật! Vui lòng thử lại sau");
                    }
                });
            }
        }else {
            displayNetworkDialog();
        }
    }

    private boolean checkValid(String oldpass, String newpass, String confirmpass) {
        if (oldpass.length() == 0){
            tvChange.setVisibility(View.VISIBLE);
            tvChange.setText("Vui lòng nhập mật khẩu hiện tại");
            OldPass.requestFocus();
            return false;
        }else if (newpass.length() == 0){
            tvChange.setVisibility(View.VISIBLE);
            tvChange.setText("Vui lòng nhập mật khẩu mới");
            NewPass.requestFocus();
            return false;
        }else if (confirmpass.length() == 0){
            tvChange.setVisibility(View.VISIBLE);
            tvChange.setText("Vui lòng xác nhận lại mật khẩu");
            ConfirmPass.requestFocus();
            return false;
        }else if (newpass.length() < 6){
            tvChange.setVisibility(View.VISIBLE);
            tvChange.setText("Mật khẩu mới phải từ 6 ký tự trở lên");
            NewPass.requestFocus();
            return false;
        }
        else if (!newpass.equals(confirmpass)){
            tvChange.setVisibility(View.VISIBLE);
            tvChange.setText("Mật khẩu xác nhận không trùng khớp");
            ConfirmPass.requestFocus();
            return false;
        }else if (oldpass.equals(newpass)){
            tvChange.setVisibility(View.VISIBLE);
            tvChange.setText("Mật khẩu đang được sử dụng");
            NewPass.requestFocus();
            return false;
        }
        else {
            return true;
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

    public void loginChange(View view) {
        Intent intent = new Intent(ChangeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
