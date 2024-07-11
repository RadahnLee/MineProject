package com.projectpra.cakesell.servlet;

import com.projectpra.cakesell.service.ServeltService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet("/change")
public class ChangeOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String account = req.getParameter("account");
        String updateName = req.getParameter("updateName");
        String updateNum = req.getParameter("updateNum");
        String upadtePrice = req.getParameter("updatePrice");
        String updateNameSql = updateName.substring(0, updateName.length() - 1);
        String updateNumSql = "";
        String updatePriceSql = "";
        String[] split = updateNum.split(",");
        String[] split1 = upadtePrice.split(",");
        for (int i = 0; i < split.length; i++) {
            if(i == split.length - 1){
                updateNumSql += split[i];
                updatePriceSql += split1[i];
                break;
            }
            updateNumSql += split[i] + ",";
            updatePriceSql += split1[i] + ",";
        }
        ServeltService service = new ServeltService();
        service.updateCart(updateNameSql, updateNumSql, updatePriceSql, account);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
