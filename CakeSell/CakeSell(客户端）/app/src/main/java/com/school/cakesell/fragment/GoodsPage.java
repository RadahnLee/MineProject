package com.school.cakesell.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.school.cakesell.R;
import com.school.cakesell.activity.GoodsDetailsActivity;
import com.school.cakesell.activity.HomePageActivity;
import com.school.cakesell.adapter.GoodsAdapter;
import com.school.cakesell.adapter.OrderAdapter;
import com.school.cakesell.entity.Cake;
import com.school.cakesell.entity.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class GoodsPage extends Fragment {
    private GridView goodsView;
    private View goods;
    private Handler handler;
    private Activity activity = null;
    private List<Cake> cakeList = new ArrayList<>();
    private String account;

    public GoodsPage(String account) {
        this.account = account;
    }

    public GoodsPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        goods = inflater.inflate(R.layout.goods_view,null);
        goodsView = goods.findViewById(R.id.goodsView);
        List<Cake> list = new ArrayList<>();
        prepareDataSource(list);
        activity = this.getActivity();
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                //很奇怪的是,为什么出了这个方法我就拿不到容器了
                switch (msg.what){
                    case 1 :
                        Bundle cakeBundle = msg.getData();
                        List<Cake> listCakes = cakeBundle.getParcelableArrayList("cakeList");
                        cakeList = listCakes;
                        GoodsAdapter goodsAdapter = new GoodsAdapter(activity,R.layout.goods_item, listCakes);
                        goodsView.setAdapter(goodsAdapter);
                        goodsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent i = new Intent();
                                i.setClass(activity, GoodsDetailsActivity.class);
                                Cake cake = cakeList.get(position);
                                i.putExtra("cake",(Parcelable) cake);
                                i.putExtra("account",account);
                                /*Log.i("cakeInfo",cake.toString());*/
                                startActivity(i);
                            }
                        });
                        break;
                }
            }
        };
        return goodsView;
    }

    public List<Cake> getCakeList() {
        return cakeList;
    }

    private void prepareDataSource(List<Cake> cakeList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取文本数据
                    URL url = new URL("http://10.7.90.113:8080/CakeSell_war_exploded/prepare");
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
                    String s;
                    while((s =reader.readLine()) != null){
                        String[] info = s.split(";");
                        int cakeId = Integer.parseInt(info[0]);
                        String cakeName = info[1];
                        int cakePrice = Integer.parseInt(info[2]);
                        int cakeStock = Integer.parseInt(info[3]);
                        String cakePicURL = info[4];
                        String cakeDescription = info[5];
                        Cake cake = new Cake(cakeId,cakePrice,cakeStock,cakeName,cakeDescription,cakePicURL);
                        cakeList.add(cake);
                    }
                    reader.close();
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("cakeList",(ArrayList<Cake>) cakeList);
                    message.what = 1;
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }
}
