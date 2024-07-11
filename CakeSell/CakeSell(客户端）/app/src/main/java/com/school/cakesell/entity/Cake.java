package com.school.cakesell.entity;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

@SuppressLint("ParcelCreator")
public class Cake implements Parcelable {
    private int id, price, stock;
    private String name, description, picURL;

    public Cake(int id, int price, int stock, String name, String description, String picURL) {
        this.id = id;
        this.price = price;
        this.stock = stock;
        this.name = name;
        this.description = description;
        this.picURL = picURL;
    }


    protected Cake(Parcel in) {
        id = in.readInt();
        price = in.readInt();
        stock = in.readInt();
        name = in.readString();
        description = in.readString();
        picURL = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "id=" + id +
                ", price=" + price +
                ", stock=" + stock +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picURL='" + picURL + '\'' +
                '}';
    }

    public static final Creator<Cake> CREATOR = new Creator<Cake>() {
        @Override
        public Cake createFromParcel(Parcel in) {
            return new Cake(in);
        }

        @Override
        public Cake[] newArray(int size) {
            return new Cake[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(price);
        dest.writeInt(stock);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(picURL);
    }
}



