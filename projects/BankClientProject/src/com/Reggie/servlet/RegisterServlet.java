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

@WebServlet(name = "RegisterServlet",urlPatterns = {"/register.do"})
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        RemoteService remoteService=new RemoteService();
        BankService bankService=(BankService)remoteService.getServiceProxy();
        String id=request.getParameter("id");
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        Result result=new Result();
        try{
            result=bankService.register(id,name,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (result.getBooleanFlag()){
            request.getSession().setAttribute("id",id);
            request.getRequestDispatcher("operation.jsp").forward(request,response);
        }else{
            request.getRequestDispatcher("register.html").forward(request,response);
        }
    }
}
