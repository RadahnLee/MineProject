package com.school.cakesell.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.school.cakesell.R;
import com.school.cakesell.entity.Cake;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

public class GoodsAdapter extends BaseAdapter {
    private Context context;
    private int Layout;
    private List<Cake> cakeList;
    private TextView goods_name, goods_stock, goods_price;
    private ImageView goods_pic;

    public GoodsAdapter(Context context, int layout, List<Cake> cakeList) {
        this.context = context;
        Layout = layout;
        this.cakeList = cakeList;
    }

    @Override
    public int getCount() {
        return cakeList.size();
    }

    @Override
    public Object getItem(int position) {
        return cakeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(Layout,null);
        }

        //注册goods_item里面的控件
        goods_name = convertView.findViewById(R.id.goods_name);
        goods_stock = convertView.findViewById(R.id.goods_stock);
        goods_price = convertView.findViewById(R.id.goods_price);
        goods_pic = convertView.findViewById(R.id.goods_pic);
        //准备数据源，封装一个方法去访问图片服务器请求图片数据
        goods_name.setText(cakeList.get(position).getName());
        goods_stock.setText("库存:" + cakeList.get(position).getStock());
        goods_price.setText(String.valueOf(cakeList.get(position).getPrice()));
        Glide.with(convertView).load(cakeList.get(position).getPicURL()).into(goods_pic);
        return convertView;
    }

}
