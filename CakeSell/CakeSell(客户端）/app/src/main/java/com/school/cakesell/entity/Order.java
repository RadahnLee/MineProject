package com.school.cakesell.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Date;
@SuppressLint("ParcelCreator")
public class Order implements Parcelable {
    private int id, total_price;
    private int[] goods_num;

    private String[] goods_name;

    private String date;


    public Order(int id, int total_price, String[] goods_name, int[] goods_id, String date) {
        this.id = id;
        this.total_price = total_price;
        this.goods_name = goods_name;
        this.goods_num = goods_id;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }


    public int[] getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int[] goods_num) {
        this.goods_num = goods_num;
    }

    public String[] getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String[] goods_name) {
        this.goods_name = goods_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }
}
