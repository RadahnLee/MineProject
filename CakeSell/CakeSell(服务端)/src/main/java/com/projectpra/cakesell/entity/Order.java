package com.projectpra.cakesell.entity;

import java.sql.Date;

public class Order {
    private int id, total_price;
    private String goods_name, goods_num, goods_id;
    private Date date;

    public Order(int id, int total_price, String goods_name, String goods_num, String goods_id, Date date) {
        this.id = id;
        this.total_price = total_price;
        this.goods_name = goods_name;
        this.goods_num = goods_num;
        this.goods_id = goods_id;
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

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
