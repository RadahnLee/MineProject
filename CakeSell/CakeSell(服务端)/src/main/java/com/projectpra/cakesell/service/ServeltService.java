package com.projectpra.cakesell.service;

import com.projectpra.cakesell.dao.WorkDao;
import com.projectpra.cakesell.entity.Order;

import java.util.Date;
import java.util.List;

public class ServeltService {

    public boolean register(String account, String pwd) {
        WorkDao dao = new WorkDao();
        return dao.register(account, pwd);
    }

    public boolean login(String account, String pwd) {
        WorkDao dao = new WorkDao();
        return dao.login(account,pwd);
    }

    public List<String> getData(){
        WorkDao dao = new WorkDao();
        return dao.getData();
    }

    public List<Order> searchOrderByAccount(String account) {
        WorkDao dao = new WorkDao();
        return dao.searchOrderByUid(account);
    }

    public void buy(String account, String cakeId) {
        WorkDao dao = new WorkDao();
        dao.insert(account, cakeId);
    }

    public boolean search(String account, String cakeId) {
        WorkDao dao = new WorkDao();
        return dao.findCartByAccount(account,cakeId);
    }

    public int getUid(String account) {
        WorkDao dao = new WorkDao();
        return  dao.getUid(account);
    }

    public String[] getCakeNames(int uid) {
        WorkDao dao = new WorkDao();
        return dao.writeCartCake(uid);
    }

    public String[] getCakeNum(int uid) {
        WorkDao dao = new WorkDao();
        return dao.writeCartNum(uid);
    }

    public String[] getCakePrice(int uid) {
        WorkDao dao = new WorkDao();
        return dao.writeCartPrice(uid);
    }

    public void updateCart(String updateNameSql, String updateNumSql, String updatePriceSql, String account) {
        WorkDao dao = new WorkDao();
        int uid = dao.getUid(account);
        dao.updateCartByUid(uid, updateNameSql, updateNumSql, updatePriceSql);
    }

    public void changeUserInfo(String account, String newAccount, String newPwd) {
        WorkDao dao = new WorkDao();
        int uid = dao.getUid(account);
        dao.updateInfo(uid, newAccount, newPwd);
    }
}
