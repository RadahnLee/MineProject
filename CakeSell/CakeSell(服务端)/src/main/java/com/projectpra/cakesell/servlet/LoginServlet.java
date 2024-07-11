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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account = req.getParameter("account");
        String pwd = req.getParameter("password");
        //获取数据库连接，从数据库中找到对应的记录
        ServeltService service = new ServeltService();
        boolean result = service.login(account,pwd);
       /* PrintWriter writer = resp.getWriter();
        writer.print(result);
        writer.close();*/
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(),"utf-8"));
        if(result) {
            writer.flush();
            writer.write("success");
            writer.flush();
            writer.close();
        }
        writer.flush();
        writer.write("fail");
        writer.close();
    }
}
