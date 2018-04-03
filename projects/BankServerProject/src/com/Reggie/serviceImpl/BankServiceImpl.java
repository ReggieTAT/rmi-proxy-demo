package com.Reggie.serviceImpl;

import com.Reggie.bean.Result;
import com.Reggie.bean.XMLObject;
import com.Reggie.service.BankService;

public class BankServiceImpl implements BankService{
    private XMLObject xmlObj=new XMLObject();

    @Override
    public Result register(String id,String name,String password) {
        Result result=new Result();
        try {
            xmlObj.addAccount(id,name,password);
            result.setBooleanFlag(true);
            result.setMessage("注册成功");
        }catch (Exception e){
            result.setBooleanFlag(false);
            result.setMessage("注册失败");
        }
        return result;
    }

    @Override
    public Result login(String id, String password) {
        Result result=new Result();
        if (xmlObj.isAccountExist(id)){
            String correctPassword=xmlObj.readPassword(id);
            if (correctPassword.equals(password)){
                result.setBooleanFlag(true);
                result.setMessage("登录成功");
            }else {
                result.setBooleanFlag(false);
                result.setMessage("密码错误");
            }
        }else{
            result.setBooleanFlag(false);
            result.setMessage("该用户不存在");
        }
        return result;
    }

    @Override
    public Result saveMoney(String id,int money) {
        Result result=new Result();
        int deposit=Integer.parseInt(xmlObj.readDeposit(id));
        deposit+=money;
        try {
            xmlObj.writeDeposit(id,String.valueOf(deposit));
            result.setBooleanFlag(true);
            result.setMessage("存款成功");
        }catch (Exception e){
            result.setBooleanFlag(false);
            result.setMessage("存款失败");
        }
        return result;
    }

    @Override
    public Result query(String id) {
        Result result=new Result();
        int deposit=Integer.parseInt(xmlObj.readDeposit(id));
        result.setIntFlag(deposit);
        return result;
    }

    @Override
    public Result withdraw(String id, int money) {
        Result result=new Result();
        int deposit=Integer.parseInt(xmlObj.readDeposit(id));
        if (money>deposit){
            result.setBooleanFlag(false);
            result.setMessage("余额不足");
        }else {
            deposit-=money;
            try {
                xmlObj.writeDeposit(id,String.valueOf(deposit));
                result.setBooleanFlag(true);
                result.setMessage("取款成功");
            }catch (Exception e){
                result.setBooleanFlag(false);
                result.setMessage("取款失败");
            }
        }
        return result;
    }

    @Override
    public Result transfer(String requestId, String targetId, int money) {
        Result result=new Result();
        Result requestResult=withdraw(requestId,money);
        if (requestResult.getBooleanFlag()){
            if (xmlObj.isAccountExist(targetId)){
                Result targetResult=saveMoney(targetId,money);
                if (targetResult.getBooleanFlag()){
                    result.setBooleanFlag(true);
                    result.setMessage("转账成功");
                }else {
                    result.setBooleanFlag(false);
                    result.setMessage("向目标账户中存款失败");
                    saveMoney(requestId,money);//将钱款返还转账请求账户
                }
            }else {
                result.setBooleanFlag(false);
                result.setMessage("目标账户不存在");
                saveMoney(requestId,money);//将钱款返还转账请求账户
            }
        }else {
            result.setBooleanFlag(false);
            result.setMessage("从账户中取款失败");
        }
        return result;
    }
}















