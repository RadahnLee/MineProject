package com.projectpra.cakesell.servlet;

import java.io.*;

import com.projectpra.cakesell.service.ServeltService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account = req.getParameter("account");
        String pwd = req.getParameter("password");
        //获取数据库连接，从数据库中找到对应的记录
        ServeltService service = new ServeltService();
        boolean result = service.register(account,pwd);

        /*PrintWriter out = resp.getWriter();
        out.print(result);
        out.close();*/
        //System.out.println(result);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(),"utf-8"));
        if(result) {
            writer.flush();
            writer.write("success");
            writer.flush();
            writer.close();
        }
        writer.flush();
        writer.write("fail");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}