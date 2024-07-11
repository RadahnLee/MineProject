package com.school.cakesell.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.school.cakesell.R;
import com.school.cakesell.entity.CartItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<CartItem> cartItemList;
    private Button add,sub,save;
    private TextView name, num, price,totalPrice;

    private String account;

    
    public CartAdapter(Context context, int layout, List<CartItem> cartItemList, Button button, TextView totalPrice, String account) {
        this.context = context;
        this.layout = layout;
        this.cartItemList = cartItemList;
        this.save = button;
        this.totalPrice = totalPrice;
        this.account = account;
    }

    @Override
    public int getCount() {
        return cartItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }

        CartItem item = cartItemList.get(position);
        name = convertView.findViewById(R.id.cartGoods_name);
        price = convertView.findViewById(R.id.cartGoods_price);
        num = convertView.findViewById(R.id.num);
        add = convertView.findViewById(R.id.add);
        sub = convertView.findViewById(R.id.sub);
        notifyDataSetChanged();
        name.setText(item.getName());
        price.setText("商品单价为:" + item.getPrice());
        num.setText(String.valueOf(item.getNum()));
        setListener(cartItemList,position,totalPrice);
        return convertView;
    }

    //给两个按钮绑定监听器
    private void setListener(List<CartItem> cartItemList, int position, TextView totalPrice) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = cartItemList.get(position).getNum();
                count++;
                cartItemList.get(position).setNum(count);
                num.setText(String.valueOf(count));
                setTotalPrice(totalPrice,cartItemList);
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItemList.get(position).getNum() == 0) {
                    cartItemList.remove(position);
                    notifyDataSetChanged();
                }
                int count = cartItemList.get(position).getNum();
                count--;
                cartItemList.get(position).setNum(count);
                num.setText(String.valueOf(count));
                setTotalPrice(totalPrice,cartItemList);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(cartItemList);
            }
        });
    }

    private void saveData(List<CartItem> cartItemList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String updateName = "";
                    String updateNum = "";
                    String updatePrice = "";
                    for (CartItem x: cartItemList) {
                         String a = x.getName() + ",";
                         String b = x.getNum() + ",";
                         String c = x.getPrice() + ",";
                         updateName += a;
                         updateNum += b;
                         updatePrice += c;
                    }
                    URL url = new URL("http://10.7.90.113:8080/CakeSell_war_exploded/change?account=" +account
                            + "&updateName=" + updateName + "" + "&updateNum=" + updateNum + "&updatePrice="
                            + updatePrice);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.getResponseCode();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    private void setTotalPrice(TextView totalPrice, List<CartItem> cartItemList) {
        int tPrice = 0;
        for(CartItem x : cartItemList) {
            int p = x.getNum() * x.getPrice();
            tPrice += p;
        }
        totalPrice.setText("总价为:" + tPrice);
    }
}
