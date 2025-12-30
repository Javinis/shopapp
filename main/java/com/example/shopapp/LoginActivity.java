package com.example.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUser = findViewById(R.id.et_user);
        EditText etPwd = findViewById(R.id.et_pwd);

        // 自动回显上次登录的账号
        etUser.setText(SPHelper.getString(this, "last_user"));

        // 点击登录
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            String u = etUser.getText().toString();
            String p = etPwd.getText().toString();
            // 取出注册时存的密码
            String realPwd = SPHelper.getString(this, "pwd_" + u);

            if (!u.isEmpty() && p.equals(realPwd)) {
                // 保存这次登录的用户名
                SPHelper.saveString(this, "last_user", u);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish(); // 销毁登录页
            } else {
                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        });

        // 跳转注册
        findViewById(R.id.btn_reg).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        // 跳转找回密码
        findViewById(R.id.btn_reset).setOnClickListener(v ->
                startActivity(new Intent(this, ResetActivity.class)));
    }
}
