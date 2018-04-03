package com.Reggie.proxy;

import java.io.File;
import java.lang.reflect.InvocationHandler;

/**
 * 封装了产生服务代理实例的一些过程,在实例化的时候会产生一个BankService的代理实例存储在serviceProxy属性里
 * 是servlet里调用服务的直接对象,请移步LoginServlet查看说明
 */
public class RemoteService {
    private final String host="localhost";
    private final Integer port=8001;
    private final String path="com.Reggie.service.BankService";
    private Object serviceProxy;

    public RemoteService() {
        try {
            Class<?> classType=Class.forName(path);
            InvocationHandler h = new ServiceInvocationHandler(classType, host, port);
            this.serviceProxy=RemoteServiceProxyFactory.getRemoteServiceProxy(h);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Object getServiceProxy() {
        return serviceProxy;
    }
}
