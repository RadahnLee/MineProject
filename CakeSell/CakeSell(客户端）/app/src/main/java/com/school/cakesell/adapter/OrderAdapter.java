package com.school.cakesell.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.school.cakesell.R;
import com.school.cakesell.entity.Cake;
import com.school.cakesell.entity.Order;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private int Layout;

    private List<Order> orderList;
    private List<Cake> cakeList;
    private TextView orderId, orderTime, orderDetail, orderPrice;

    public OrderAdapter(Context context, int layout, List<Order> orderList, List<Cake> cakeList) {
        this.context = context;
        Layout = layout;
        this.orderList = orderList;
        this.cakeList = cakeList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
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
        //获取控件
        orderId = convertView.findViewById(R.id.order_id);
        orderDetail = convertView.findViewById(R.id.order_detail);
        orderTime = convertView.findViewById(R.id.order_time);
        orderPrice = convertView.findViewById(R.id.order_price);
        
        //绑定数据
        String id = "订单编号为:" + String.valueOf(orderList.get(position).getId());
        orderId.setText(id);
        String Time = "订单时间为:" + orderList.get(position).getDate().toString();
        orderTime.setText(Time);
        String totalPrice = "订单总价为:" + String.valueOf(orderList.get(position).getTotal_price());
        orderPrice.setText(totalPrice);
        String detail = "";
        String last = "";
        for (int i = 0; i < orderList.get(position).getGoods_num().length; i++) {
            int t = i;
            if(orderList.get(position).getGoods_num()[t] == 0){
                break;
            }
            last += orderList.get(position).getGoods_name()[t] + '×' + orderList.get(position).getGoods_num()[t];
        }
        detail = "购买商品为:" + last;
        orderDetail.setText(detail);
        return convertView;
    }

    private String getCakeNameById(int goodsId) {
        for(Cake c:cakeList){
            if(c.getId() == goodsId){
                return c.getName();
            }
        }
        return "";
    }
}
