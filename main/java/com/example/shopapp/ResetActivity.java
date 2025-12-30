package com.example.shopapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        EditText etUser = findViewById(R.id.et_user);
        EditText etNewPwd = findViewById(R.id.et_new_pwd);

        findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            String u = etUser.getText().toString();
            String p = etNewPwd.getText().toString();

            // 检查用户是否存在
            String oldPwd = SPHelper.getString(this, "pwd_" + u);
            if (oldPwd.isEmpty()) {
                Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
            } else {
                // 覆盖旧密码
                SPHelper.saveString(this, "pwd_" + u, p);
                Toast.makeText(this, "密码重置成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
