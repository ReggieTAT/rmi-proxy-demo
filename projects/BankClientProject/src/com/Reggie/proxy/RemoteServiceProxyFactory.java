package com.Reggie.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 代理工厂,产生代理实例,被RemoteService调用
 */

public class RemoteServiceProxyFactory {
    public static Object getRemoteServiceProxy(InvocationHandler handler){
        Class<?> classType=((ServiceInvocationHandler)handler).getClassType();
        //获取动态代理
        Object proxy= Proxy.newProxyInstance(classType.getClassLoader(),new Class[]{classType},handler);
        return proxy;
    }
}
