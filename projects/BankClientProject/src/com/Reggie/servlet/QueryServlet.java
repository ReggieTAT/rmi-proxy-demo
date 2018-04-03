package com.Reggie.servlet;

import com.Reggie.bean.Result;
import com.Reggie.proxy.RemoteService;
import com.Reggie.service.BankService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "QueryServlet",urlPatterns = {"/query.do"})
public class QueryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        RemoteService remoteService=new RemoteService();
        BankService bankService=(BankService)remoteService.getServiceProxy();
        String id=(String)request.getSession().getAttribute("id");
        Result result=new Result();
        try{
            result=bankService.query(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("money",result.getIntFlag());
        request.getRequestDispatcher("operation.jsp").forward(request,response);
    }
}
