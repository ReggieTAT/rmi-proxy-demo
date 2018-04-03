package com.Reggie.thread;

import com.Reggie.bean.Call;
import com.Reggie.bean.Result;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;

/**
 * 线程类
 */
public class ServerThread implements Runnable {
    private Socket socket;
    private Map<String,Object> remoteObjects=new Hashtable<String, Object>();

    public ServerThread(Socket socket, Map<String, Object> remoteObjects) {
        this.socket = socket;
        this.remoteObjects = remoteObjects;
    }

    @Override
    public void run() {
        try {
            InputStream in=socket.getInputStream();
            ObjectInputStream objIn=new ObjectInputStream(in);
            OutputStream out = socket.getOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            // 对象输入流读取请求的call对象
            Call call = (Call) objIn.readObject();
            System.out.println("received call：" + call);
            call = getCallResult(call);
            // 发送处理的结果回客户端
            objOut.writeObject(call);
            objIn.close();
            in.close();
            objOut.close();
            out.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private Call getCallResult(Call call){
        Result result=new Result();
        try {
            String className = call.getClassName();
            String methodName = call.getMethodName();
            Object[] params = call.getParams();
            Class<?>[] paramsTypes = call.getParamTypes();
            Class<?> classType = Class.forName(className);
            // 获取所要调用的方法
            Method method = classType.getMethod(methodName, paramsTypes);
            Object remoteObject=remoteObjects.get(className);
            if (remoteObject==null){
                result.setBooleanFlag(false);
                result.setMessage(className+"的远程对象不存在!");
            }else {
                result=(Result)method.invoke(remoteObject,params);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        call.setResult(result);
        return call;
    }
}
