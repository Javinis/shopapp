package com.example.shopapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etUser = findViewById(R.id.et_user);
        EditText etPwd = findViewById(R.id.et_pwd);

        findViewById(R.id.btn_submit).setOnClickListener(v -> {
            String u = etUser.getText().toString();
            String p = etPwd.getText().toString();

            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "请输入完整", Toast.LENGTH_SHORT).show();
            } else {
                // 保存密码，key 是 "pwd_用户名"
                SPHelper.saveString(this, "pwd_" + u, p);
                Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
