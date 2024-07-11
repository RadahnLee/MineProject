package com.projectpra.cakesell.servlet;

import com.projectpra.cakesell.dao.DBUtils;
import com.projectpra.cakesell.service.ServeltService;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet("/prepare")
public class PrepareServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServeltService service = new ServeltService();
        List<String> cakeList = service.getData();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(),"utf-8"));
        for(String x : cakeList) {
           bufferedWriter.flush();
           bufferedWriter.write(x);
           bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
