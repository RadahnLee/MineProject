package com.projectpra.cakesell.servlet;

import com.projectpra.cakesell.service.ServeltService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jboss.weld.context.http.Http;

import java.io.IOException;

@WebServlet("/user")
public class ChangeUserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String account = req.getParameter("account");
        String newAccount = req.getParameter("newAccount");
        String newPwd = req.getParameter("newPwd");
        ServeltService service = new ServeltService();
        service.changeUserInfo(account, newAccount, newPwd);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
