package com.school.cakesell.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.school.cakesell.R;
import com.school.cakesell.adapter.OrderAdapter;
import com.school.cakesell.entity.Order;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class OrderPage extends Fragment {

    private ListView OrderView;
    private String account;
    private List<Order> orderList = new ArrayList<>();
    private Activity activity = null;
    private Handler handler;

    public OrderPage(String account){
        this.account = account;
    }

    public OrderPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View orders = inflater.inflate(R.layout.order_view,null);
        OrderView = orders.findViewById(R.id.lv_order);
        activity = getActivity();
        getOrderData(account, orderList);
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 2:
                        Bundle orderBundle = msg.getData();
                        List<Order> listOrders = orderBundle.getParcelableArrayList("orderList");
                        orderList = listOrders;
                        //TODO 这个地方要改 拿不到商品名字
                        OrderAdapter orderAdapter = new OrderAdapter(activity,R.layout.order_item, orderList, new GoodsPage("").getCakeList());
                        OrderView.setAdapter(orderAdapter);
                        break;
                }
                }

        };
        return OrderView;
    }
    private void getOrderData(String account, List<Order> orderList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //向servlet发送当前用户的账户名，以方便从数据库中获取该用户的订单数据
                    URL url = new URL("http://10.7.90.113:8080/CakeSell_war_exploded/order?account="+account);
                    URLConnection coon = url.openConnection();
                    coon.setDoInput(true);
                    coon.setDoOutput(true);

                    //接收数据，准备数据源
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(coon.getInputStream(),"utf-8"));
                    String s;
                    while((s = bufferedReader.readLine()) != null){
                        String[] split = s.split(";");
                        int orderId = Integer.parseInt(split[0]);
                        String goodsName = split[1];
                        String[] seperateName = getSeperateName(goodsName);
                        String goodsNum = split[2];
                        int[] seperateNum = getSeperateNum(goodsNum);
                        String date = split[3];
                        int totalPrice = Integer.parseInt(split[4]);
                        Order order = new Order(orderId,totalPrice,seperateName,seperateNum,date);
                        orderList.add(order);
                    }
                    bufferedReader.close();

                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("orderList", (ArrayList<Order>) orderList);
                    message.what = 2;
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private int[] getSeperateNum(String goodsNum) {
        String[] split = goodsNum.split(",");
        int[] num = new int[100];
        for (int i = 0; i < split.length; i++) {
            num[i] = Integer.parseInt(split[i]);
        }
        return num;
    }

    private String[] getSeperateName(String goodsName) {
        String[] split = goodsName.split(",");
        /*int[] Id = new int[100];
        for (int i = 0; i < split.length; i++) {
            Id[i] = Integer.parseInt(split[i]);
        }*/
        return split;
    }
}

