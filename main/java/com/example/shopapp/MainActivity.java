package com.example.shopapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ScrollView layoutHome;
    private LinearLayout layoutMine;
    private TextView btnTabHome, btnTabMine, tvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutHome = findViewById(R.id.layout_home);
        layoutMine = findViewById(R.id.layout_mine);
        btnTabHome = findViewById(R.id.btn_tab_home);
        btnTabMine = findViewById(R.id.btn_tab_mine);
        tvUser = findViewById(R.id.tv_user_info);

        // 设置用户名
        tvUser.setText("当前用户：" + SPHelper.getString(this, "last_user"));

        // 底部Tab点击事件
        btnTabHome.setOnClickListener(v -> switchTab(true));
        btnTabMine.setOnClickListener(v -> switchTab(false));

        // 退出登录
        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // 动态生成UI (加分项)
        initMenuGrid();
        initProductGrid();
    }

    private void switchTab(boolean isHome) {
        if (isHome) {
            layoutHome.setVisibility(View.VISIBLE);
            layoutMine.setVisibility(View.GONE);
            btnTabHome.setTextColor(Color.RED);
            btnTabMine.setTextColor(Color.BLACK);
        } else {
            layoutHome.setVisibility(View.GONE);
            layoutMine.setVisibility(View.VISIBLE);
            btnTabHome.setTextColor(Color.BLACK);
            btnTabMine.setTextColor(Color.RED);
        }
    }

    private void initMenuGrid() {
        GridLayout grid = findViewById(R.id.grid_menu);
        // 1. 定义两组数组：一个是名字，一个是对应的 Emoji
        String[] menus = {"新品", "聚划算", "国际", "外卖", "超市", "充值", "机票", "领金币", "拍卖", "分类"};
        String[] icons = {"🎁", "🔥", "🌏", "🍔", "🍎", "💰", "✈️", "🪙", "🔨", "📂"};

        for (int i = 0; i < menus.length; i++) {
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.width = 0;
            params.setMargins(0, 20, 0, 20);
            item.setLayoutParams(params);

            // --- 修改开始：用 Emoji 代替 ImageView ---
            TextView icon = new TextView(this);
            icon.setText(icons[i]);       // 设置 Emoji
            icon.setTextSize(30);         // 字体设置大一点，看起来像图标
            icon.setGravity(Gravity.CENTER);
            icon.setTextColor(Color.WHITE);
            icon.setPadding(0,0,0,10);    //稍微把图标往上提一点
            // --- 修改结束 ---

            TextView text = new TextView(this);
            text.setText(menus[i]);
            text.setTextSize(12);
            text.setTextColor(Color.BLACK);
            text.setGravity(Gravity.CENTER);

            item.addView(icon);
            item.addView(text);

            final String name = menus[i];
            item.setOnClickListener(v -> Toast.makeText(this, "点击: " + name, Toast.LENGTH_SHORT).show());
            grid.addView(item);
        }
    }

    private void initProductGrid() {
        GridLayout grid = findViewById(R.id.grid_products);
        // 准备一堆商品 Emoji
        String[] productEmojis = {"📱", "💻", "⌚", "📷", "🎧", "👟", "👜", "👓", "💄", "🚲"};

        for (int i = 0; i < 20; i++) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setBackgroundColor(Color.WHITE);
            // 给卡片加一点圆角效果 (利用 View 的特性，API 21+ 支持)
            card.setElevation(5f);
            card.setPadding(20, 20, 20, 20);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = getResources().getDisplayMetrics().widthPixels / 2 - 30; // 调整间距
            params.setMargins(15, 15, 15, 15);
            card.setLayoutParams(params);

            // --- 修改开始：商品图变成超大 Emoji ---
            TextView imgPlaceholder = new TextView(this);
            imgPlaceholder.setText(productEmojis[i % productEmojis.length]); // 循环使用表情
            imgPlaceholder.setTextSize(50); // 超大号
            imgPlaceholder.setTextColor(Color.BLACK);
            imgPlaceholder.setGravity(Gravity.CENTER);
            imgPlaceholder.setBackgroundColor(Color.parseColor("#F5F5F5")); // 浅灰背景
            imgPlaceholder.setHeight(300); // 固定高度
            imgPlaceholder.setGravity(Gravity.CENTER); // 表情居中
            // --- 修改结束 ---

            TextView title = new TextView(this);
            title.setText("【热销】好物推荐系列 " + (i + 1));
            title.setTextSize(14);
            title.setTextColor(Color.BLACK);
            title.setPadding(0, 20, 0, 0);

            TextView price = new TextView(this);
            price.setText("￥ " + (99 + i * 10));
            price.setTextColor(Color.parseColor("#FF5000")); // 淘宝橙
            price.setTextSize(16);
            price.setPadding(0, 10, 0, 0);

            card.addView(imgPlaceholder);
            card.addView(title);
            card.addView(price);

            card.setOnClickListener(v -> Toast.makeText(this, "打开商品详情...", Toast.LENGTH_SHORT).show());
            grid.addView(card);
        }
    }
}
