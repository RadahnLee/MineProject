package com.projectpra.cakesell.servlet;

import com.projectpra.cakesell.entity.Order;
import com.projectpra.cakesell.service.ServeltService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account = req.getParameter("account");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(),"utf-8"));
        /*PrintWriter writer = resp.getWriter();*/
        ServeltService service = new ServeltService();
        List<Order> orderList = service.searchOrderByAccount(account);
        for(Order o: orderList){
            String id = String.valueOf(o.getId());
            String goodsName = o.getGoods_name();
            String goodsNum = o.getGoods_num();
            String date = o.getDate().toString();
            String totalPrice = String.valueOf(o.getTotal_price());
            String order = id +';' + goodsName+';' + goodsNum +';'+ date+';' + totalPrice + ";";
            writer.flush();
            writer.write(order);
            writer.newLine();
        }
        writer.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
