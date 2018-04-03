package com.Reggie.service;

import com.Reggie.bean.Result;

public interface BankService {
    public Result register(String id, String name, String password);//注册
    public Result login(String id, String password);//登录
    public Result saveMoney(String id, int money);//存款
    public Result query(String id);//查询余额
    public Result withdraw(String id, int money);//取款
    public Result transfer(String requestId, String targetId, int money);//转账
}
