package com.example.shopapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SPHelper {
    // 简单封装 SharedPreferences，用来存账号密码
    public static void saveString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("app_data", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("app_data", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
}
