package com.school.cakesell.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.school.cakesell.R;
import com.school.cakesell.entity.Cake;
import com.school.cakesell.entity.CartItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class GoodsDetailsActivity extends AppCompatActivity {
    private int itemId;
    private ImageView goodPic;
    private TextView goodname, goodPrice, goodDescription;
    private Button purchase, addToCart;
    private String account;
    private Activity activity = null;

    private Cake cake;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail);
        Intent intent = getIntent();
        cake = (Cake) intent.getParcelableExtra("cake");
        account = intent.getStringExtra("account");
        activity = this;
        Log.i("cakeInfo",cake.toString());
        /**
         * 注册控件
         * 从Bundle里拿到对应的对象，实现数据的绑定
         * 实现监听器
         * */
        findViews();
        matchData(cake);
        setListeners(cake);
    }

    private void setListeners(Cake cake) {
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //购买功能此处不进入购物车，直接添加用户订单
                buy(cake.getId());
                Log.i("cakemsg",cake.toString());
                Log.i("account",account);
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCake();
            }
        });
    }

    private void addCake() {
        Intent intent = new Intent();
        intent.putExtra("account",account);
        intent.putExtra("cake",cake);
        intent.setClass(GoodsDetailsActivity.this, CartActivity.class);
        startActivity(intent);
    }

    private void buy(int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.7.90.113:8080/CakeSell_war_exploded/purchase?account=" +
                            account + "&cakeId=" + id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                   /* int responseCode = connection.getResponseCode();*/
                   /* Log.i("Response Code",String.valueOf(responseCode));*/
                    connection.disconnect();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void matchData(Cake cake) {
        Glide.with(this).load(cake.getPicURL()).into(goodPic);
        goodname.setText(cake.getName());
        goodDescription.setText(cake.getDescription());
        goodPrice.setText(String.valueOf(cake.getPrice()));
    }

    private void findViews() {
        goodPic = findViewById(R.id.pic);
        goodname = findViewById(R.id.name);
        goodDescription = findViewById(R.id.description);
        goodPrice = findViewById(R.id.price);
        purchase = findViewById(R.id.purchase);
        addToCart = findViewById(R.id.add_to_cart);
    }
}
