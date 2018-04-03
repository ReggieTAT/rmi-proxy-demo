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

@WebServlet(name = "LoginServlet",urlPatterns = {"/login.do"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //实例化remoteService,获取服务代理实例,并转换成BankService类型
        RemoteService remoteService=new RemoteService();
        BankService bankService=(BankService)remoteService.getServiceProxy();

        String id=request.getParameter("id");
        String password=request.getParameter("password");
        Result result=new Result();
        try{
            //bankService代理实例可直接调用BankService接口的方法
            result=bankService.login(id,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (result.getBooleanFlag()){
            request.getSession().setAttribute("id",id);
            request.getRequestDispatcher("operation.jsp").forward(request,response);
        }else{
            request.getRequestDispatcher("login.html").forward(request,response);
        }
    }
}
