package com.school.cakesell.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.school.cakesell.R;
import com.school.cakesell.adapter.CartAdapter;
import com.school.cakesell.entity.Cake;
import com.school.cakesell.entity.CartItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView listView;

    private Cake cake;

    private String account;

    private List<CartItem> cartItemList = new ArrayList<>();

    private Handler handler;

    private Button button;
    private TextView totalPrice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        listView = findViewById(R.id.cartView);
        totalPrice = findViewById(R.id.Total_Price);
        button = findViewById(R.id.button);

        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        cake = intent.getParcelableExtra("cake");
        getCartData(account, cake);
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
              if(msg.what == 1){
                  cartItemList = (List<CartItem>) msg.obj;
                  Log.i("cartItemList",cartItemList.toString());
                  CartAdapter cartAdapter = new CartAdapter(getBaseContext(), R.layout.cart_item, cartItemList,
                          button, totalPrice,account);
                  listView.setAdapter(cartAdapter);
              }else if(msg.what == 2) {
                  CartItem item = (CartItem) msg.obj;
                  cartItemList.add(item);
                  Log.i("cartItemList",cartItemList.toString());
                  CartAdapter cartAdapter = new CartAdapter(getBaseContext(), R.layout.cart_item, cartItemList,
                          button, totalPrice,account);
                  listView.setAdapter(cartAdapter);
              }
            }
        };
    }

    private void getCartData(String account, Cake cake) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL("http://10.7.90.113:8080/CakeSell_war_exploded/cart?account=" +
                            account +"&cakeId=" + cake.getId() );
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    int responseCode = connection.getResponseCode();
                    Log.i("Response Code",String.valueOf(responseCode));
                    if(responseCode == 300) {
                        CartItem itemNow = new CartItem(cake.getName(),1,cake.getPrice());
                        Message message = Message.obtain(handler, 2);
                        message.obj = itemNow;
                        handler.sendMessage(message);
                    } else if (responseCode == 200) {
                        //有数据，先接受服务器传回来的数据 然后再跳转页面
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                                "utf-8"));
                        String cakeName = "";
                        String cakeNum = "";
                        String cakePrice = "";
                        List<CartItem> items = new ArrayList<>();
                        while((cakeName = bufferedReader.readLine()) != null){
                            cakeNum = bufferedReader.readLine();
                            cakePrice = bufferedReader.readLine();
                            CartItem item = new CartItem(cakeName, Integer.parseInt(cakeNum), Integer.parseInt(cakePrice));
                            items.add(item);
                        }
                        CartItem itemNow = new CartItem(cake.getName(),1,cake.getPrice());
                        items.add(itemNow);
                        Log.i("cartInfo",items.toString());
                        Message message = Message.obtain(handler, 1);
                        message.obj = items;
                        handler.sendMessage(message);
                    }
                    connection.disconnect();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }
}
