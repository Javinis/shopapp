package com.example.shopapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
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

        // 1. 绑定控件
        layoutHome = findViewById(R.id.layout_home);
        layoutMine = findViewById(R.id.layout_mine);
        btnTabHome = findViewById(R.id.btn_tab_home);
        btnTabMine = findViewById(R.id.btn_tab_mine);
        tvUser = findViewById(R.id.tv_user_info);

        // 2. 显示用户名
        tvUser.setText("当前用户：" + SPHelper.getString(this, "last_user"));

        // 3. Tab切换
        btnTabHome.setOnClickListener(v -> switchTab(true));
        btnTabMine.setOnClickListener(v -> switchTab(false));

        // 4. 退出登录
        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // 5. 个人中心加分功能
        findViewById(R.id.btn_call).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(android.net.Uri.parse("tel:11111"));
            startActivity(intent);
        });

        findViewById(R.id.btn_share).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "SHARE！");
            startActivity(Intent.createChooser(intent, "分享到"));
        });

        findViewById(R.id.btn_about).setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("关于我们")
                    .setMessage("当前版本：v1.0.0\n开发者：陈文渊")
                    .setPositiveButton("好的", null)
                    .show();
        });

        // 6. 动态生成菜单 (保留！)
        initMenuGrid();

        // 7. 给XML里写死的商品加点击事件 (可选，增加交互感)
        bindStaticProductClicks();
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

    // 辅助方法：给静态写死的商品绑定点击事件
    private void bindStaticProductClicks() {
        GridLayout grid = findViewById(R.id.grid_products);
        // 遍历所有子 View (即那6个商品卡片)
        for (int i = 0; i < grid.getChildCount(); i++) {
            View child = grid.getChildAt(i);
            child.setOnClickListener(v ->
                    Toast.makeText(MainActivity.this, "正在打开商品详情...", Toast.LENGTH_SHORT).show()
            );
        }
    }

    // --- 动态菜单相关 ---
    private android.graphics.drawable.GradientDrawable createCircleBg(String color) {
        android.graphics.drawable.GradientDrawable drawable = new android.graphics.drawable.GradientDrawable();
        drawable.setShape(android.graphics.drawable.GradientDrawable.OVAL);
        drawable.setColor(Color.parseColor(color));
        return drawable;
    }

    private void initMenuGrid() {
        GridLayout grid = findViewById(R.id.grid_menu);
        grid.removeAllViews(); // 防止重复

        String[] menus = {"新品", "聚划算", "国际", "外卖", "超市", "充值", "机票", "领金币", "拍卖", "分类"};
        String[] icons = {"🎁", "🔥", "🌏", "🍔", "🍎", "💰", "✈️", "🪙", "🔨", "📂"};

        // ❌ 删掉了 bgColors 数组

        for (int i = 0; i < menus.length; i++) {
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.width = 0;
            params.setMargins(0, 20, 0, 20);
            item.setLayoutParams(params);

            TextView icon = new TextView(this);
            icon.setText(icons[i]);
            icon.setTextSize(32); // 稍微放大一点，因为没有背景了
            icon.setGravity(Gravity.CENTER);
            icon.setTextColor(Color.BLACK);

            // 下面的布局参数保持简单
            item.addView(icon);

            TextView text = new TextView(this);
            text.setText(menus[i]);
            text.setTextSize(12);
            text.setTextColor(Color.parseColor("#666666")); // 文字稍微深灰一点
            text.setGravity(Gravity.CENTER);
            text.setPadding(0, 10, 0, 0); // 文字和图标拉开一点距离

            item.addView(text);

            final String name = menus[i];
            item.setOnClickListener(v -> Toast.makeText(this, "点击: " + name, Toast.LENGTH_SHORT).show());
            grid.addView(item);
        }
    }
}
