package com.school.cakesell.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.school.cakesell.R;
import com.school.cakesell.adapter.GoodsAdapter;
import com.school.cakesell.adapter.MyFragmentAdapter;
import com.school.cakesell.adapter.OrderAdapter;
import com.school.cakesell.entity.Cake;
import com.school.cakesell.entity.Order;
import com.school.cakesell.fragment.GoodsPage;
import com.school.cakesell.fragment.MinePage;
import com.school.cakesell.fragment.OrderPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private TabLayout cursor;
    private ViewPager2 vp2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        /**
         * 关联homepage.xml里面的tabLayout还有viewPager2
         * 关联goodsView和goods_item,orderView和order_item
         * 将绑定好的页面当做Fragment,与VP2进行绑定
         * 关联好tabLayout和VP2
         * 绑定监听器，实现点击商品跳转到对应商品详情页当中去
         * */

        findViews();
        Intent intent = getIntent();
        String account = intent.getStringExtra("account");

        //准备Fragment数据源并关联好Adapter
        List<Fragment> fragments = new ArrayList<>();
        GoodsPage goodsPage = new GoodsPage(account);
        OrderPage orderPage = new OrderPage(account);
        MinePage minePage = new MinePage(account);
        fragments.add(goodsPage);
        fragments.add(orderPage);
        fragments.add(minePage);
        MyFragmentAdapter fragmentAdapter = new MyFragmentAdapter(this, fragments);

        //关联tabLayout和Vp2
        vp2.setAdapter(fragmentAdapter);
        TabLayoutMediator mediator = new TabLayoutMediator(cursor, vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("商品");
                        break;
                    case 1:
                        tab.setText("订单");
                        break;
                    case 2:
                        tab.setText("我的");
                        break;
                }
            }
        });
        mediator.attach();
    }

    private void findViews() {
        cursor = findViewById(R.id.cursor);
        vp2 = findViewById(R.id.vp2);
    }

    }


