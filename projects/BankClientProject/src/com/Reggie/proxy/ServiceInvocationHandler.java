package com.Reggie.proxy;

import com.Reggie.bean.Call;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类,同远程服务建立连接并发送和接受call对象,在RemoteService中调用
 */
public class ServiceInvocationHandler implements InvocationHandler {
    private Class<?> classType;
    private String host;
    private Integer port;

    public Class<?> getClassType() {
        return classType;
    }

    public ServiceInvocationHandler(Class<?> classType, String host, Integer port) {
        this.classType = classType;
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //封装请求信息
        Call call=new Call(classType.getName(),method.getName(),method.getParameterTypes(),args);
        //创建连接
        Connector connector=new Connector();
        connector.connect(host,port);
        //发送请求
        connector.sendCall(call);
        //获取封装远程方法调用结果的对象
        call=connector.receive();
        connector.close();
        return call.getResult();
    }
}
