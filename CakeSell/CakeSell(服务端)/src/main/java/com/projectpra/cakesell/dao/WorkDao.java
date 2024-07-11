package com.projectpra.cakesell.dao;

import com.projectpra.cakesell.entity.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WorkDao {
    private int userId;
    public boolean register(String account, String pwd) {
            if(account.isEmpty() || pwd.isEmpty()){
                return false;
            }
            Connection conn = DBUtils.getCon();
            try {
                    Statement sta = conn.createStatement();
                    String sql = "select account, pwd from user;";
                    ResultSet set = sta.executeQuery(sql);
                    while (set.next()) {
                        if (account.equals(set.getString("account"))) {
                            DBUtils.close(conn);
                            return false;
                        }
                    }
                    String insert_sql = "insert into user values('" + account + "'," + "'" + pwd + "');";
                    sta.executeUpdate(insert_sql);
                    return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    public boolean login(String account, String pwd) {
        if(account.isEmpty() || pwd.isEmpty()){
            return false;
        }
        Connection conn = DBUtils.getCon();
        try {
            Statement sta = conn.createStatement();
            String search_sql = "select account, pwd from user;";
            ResultSet set = sta.executeQuery(search_sql);
            while (set.next()) {
                if (account.equals(set.getString("account"))) {
                    if(pwd.equals(set.getString("pwd"))){
                        DBUtils.close(conn);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<String> getData() {
        List<String> cakeList =  new ArrayList<>();
        try {
            Connection conn = DBUtils.getCon();
            Statement sta = conn.createStatement();
            String select_sql = "select id, name, price, state, stock, pic_url, description from tbl_cake;";
            ResultSet set = sta.executeQuery(select_sql);
            while(set.next()){
                if(set.getString("state").equals(String.valueOf(1))){
                    String cake_id = set.getString("id");
                    String cake_name = set.getString("name");
                    String cake_price = set.getString("price");
                    String cake_stock = set.getString("stock");
                    String cake_pic = set.getString("pic_url");
                    String cake_description = set.getString("description");
                    String cake_info = cake_id + ';' + cake_name + ';' + cake_price + ';' + cake_stock + ';' + cake_pic + ";"+ cake_description + ";";
                    cakeList.add(cake_info);
                }
            }
            DBUtils.close(conn);
            return cakeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> searchOrderByUid(String account) {
        Connection coon = DBUtils.getCon();
        List<Order> orderList = new ArrayList<>();
        try {
            //1.先拿id
            Statement get_uid_sta = coon.createStatement();
            String search_uid_sql = "select id from user where account =" + "'" + account + "';";
            ResultSet set = get_uid_sta.executeQuery(search_uid_sql);
            int id = 0;
            if(set.next()){
                id = set.getInt("id");
            }
            //2.拿了id去order表里拿订单
            Statement get_order_sta = coon.createStatement();
            String get_order_sql = "select oid, goods_id, goods_name, goods_num, " +
                    "odate, total_price from orders where userId = " + id;
            ResultSet orderSet = get_order_sta.executeQuery(get_order_sql);
            while(orderSet.next()){
                int oid = orderSet.getInt("oid");
                String goods_id = orderSet.getString("goods_id");
                String goods_name = orderSet.getString("goods_name");
                String goods_num = orderSet.getString("goods_num");
                int total_price = orderSet.getInt("total_price");
                Date date = orderSet.getDate("odate");
                Order o = new Order(oid,total_price,goods_name,goods_num,goods_id,date);
                orderList.add(o);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(String account, String cakeId) {
        Connection conn = DBUtils.getCon();
        try {
            Statement sta = conn.createStatement();
            String searchUid = "select id from user where account = " + '\'' + account + "';";
            String searchCakeInfo = "select id, name, price, stock from tbl_cake where id =" +cakeId;
            ResultSet uSet = sta.executeQuery(searchUid);
            int uid = 0, cakePrice = 0, cakeStock = 0;
            String cakeName = "";
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedTime = currentTime.format(formatter);
            Date date = Date.valueOf(formattedTime);
            if(uSet.next()) {
                uid = uSet.getInt("id");
            }
            ResultSet cSet = sta.executeQuery(searchCakeInfo);
            if(cSet.next()) {
                cakeName = cSet.getString("name");
                cakePrice = cSet.getInt("price");
                cakeStock = cSet.getInt("stock") - 1;
            }
            String insertSql = "insert into orders (userId, goods_name, goods_num, total_price, odate, goods_id) " +
                    "values("+ uid + "," + '\'' + cakeName + "'" + ',' + 1 + ',' + cakePrice + ','+ '\'' + date + "'," + cakeId +");" ;
            sta.executeUpdate(insertSql);
            String updateSql = "update tbl_cake set stock = " + cakeStock +" where id = " + cakeId + ";";
            sta.executeUpdate(updateSql);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean findCartByAccount(String account, String cakeId) {
        Connection conn = DBUtils.getCon();
        try {
            Statement sta = conn.createStatement();
            String searchUid = "select id from user where account = " + '\'' + account + "';";
            int uid = 0;
            ResultSet uSet = sta.executeQuery(searchUid);
            if(uSet.next()) {
                uid = uSet.getInt("id");
            }
            String searchCartIdSql = "select id from cart where uid = " + uid;
            ResultSet set = sta.executeQuery(searchCartIdSql);
            if(set.next()){
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public String[] writeCartCake(int uid) {
        Connection conn = DBUtils.getCon();
        Statement sta = null;
        String cakeName = "";
        try {
            sta = conn.createStatement();
            String searchCakeInfo = "select cakename from cart where uid = " + uid;
            ResultSet cSet = sta.executeQuery(searchCakeInfo);
            if (cSet.next()) {
                cakeName = cSet.getString("cakename");
            }
            String[] name = cakeName.split(",");
            return name;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] writeCartNum(int uid) {
        Connection conn = DBUtils.getCon();
        Statement sta = null;
        String cakeNum = "";
        try {
            sta = conn.createStatement();
            String searchCakeInfo = "select cakenum from cart where uid = " + uid;
            ResultSet cSet = sta.executeQuery(searchCakeInfo);
            if(cSet.next()) {
                cakeNum = cSet.getString("cakenum");
            }
            String[] num = cakeNum.split(",");
            return num;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] writeCartPrice(int uid) {
        Connection conn = DBUtils.getCon();
        Statement sta = null;
        String cakePrice = "";
        try {
            sta = conn.createStatement();
            String searchCakeInfo = "select cakeprice from cart where uid = " + uid;
            ResultSet cSet = sta.executeQuery(searchCakeInfo);
            if(cSet.next()) {
                cakePrice = cSet.getString("cakePrice");
            }
            String[] num = cakePrice.split(",");
            return num;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUid(String account) {
        Connection conn = DBUtils.getCon();
        Statement sta = null;
        try {
            sta = conn.createStatement();
            String searchUid = "select id from user where account = " + '\'' + account + "';";
            int uid = 0;
            ResultSet uSet = sta.executeQuery(searchUid);
            if(uSet.next()) {
                uid = uSet.getInt("id");
            }
            return uid;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCartByUid(int uid, String updateNameSql, String updateNumSql, String updatePriceSql) {
        Connection conn = DBUtils.getCon();
        Statement sta = null;
        try {
            sta = conn.createStatement();
            String updateSql = "update cart set cakename = " + "\'" + updateNameSql + "',"
                    + " cakenum = " + "\'" + updateNumSql + "'," + " cakePrice = " + "\'" + updatePriceSql + "'" + " where uid =" + uid + ";";
            sta.executeUpdate(updateSql);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateInfo(int uid, String newAccount, String newPwd) {
        Connection conn = DBUtils.getCon();
        Statement sta = null;
        try {
            sta = conn.createStatement();
            String updateSql = "update user set account = " + "\'" + newAccount + "',"
                    + " pwd = " + "\'" + newPwd + "'" + " where id =" + uid + ";";
            sta.executeUpdate(updateSql);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
