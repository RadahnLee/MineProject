package com.projectpra.cakesell.servlet;

import com.projectpra.cakesell.service.ServeltService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account = req.getParameter("account");
        String cakeId = req.getParameter("cakeId");
        ServeltService service = new ServeltService();
        if(service.search(account,cakeId)) {
            int uid = service.getUid(account);
            String[] cakeNames = service.getCakeNames(uid);
            String[] cakeNum = service.getCakeNum(uid);
            String[] cakePrice = service.getCakePrice(uid);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(),"utf-8"));
            for (int i = 0; i < cakeNames.length; i++) {
                writer.write(cakeNames[i]);
                writer.newLine();
                writer.write(cakeNum[i]);
                writer.newLine();
                writer.write(cakePrice[i]);
                writer.newLine();
                writer.flush();
            }
            resp.setStatus(205);
            writer.close();
        }else resp.setStatus(300);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
