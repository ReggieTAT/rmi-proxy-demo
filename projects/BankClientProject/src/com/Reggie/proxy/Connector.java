package com.Reggie.proxy;

import com.Reggie.bean.Call;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 连接远程服务的类,能向远程服务器发送call对象和接受返回的call对象
 */

public class Connector {
    private Socket linkSocket;
    private InputStream in;
    private ObjectInputStream objIn;
    private OutputStream out;
    private ObjectOutputStream objOut;

    public Connector() {
    }
    /**
     * 创建连接
     */
    public void connect(String host,Integer port) throws UnknownHostException,IOException {
        linkSocket=new Socket(host,port);
        out=linkSocket.getOutputStream();
        objOut=new ObjectOutputStream(out);
        in=linkSocket.getInputStream();
        objIn=new ObjectInputStream(in);
    }
    /**
     * 发送请求call对象
     */
    public void sendCall(Call call) throws IOException{
        objOut.writeObject(call);
    }
    /**
     * 获取请求对象
     */
    public Call receive()throws ClassNotFoundException,IOException{
        return (Call)objIn.readObject();
    }
    /**
     * 简单处理关闭连接
     */
    public void close(){
        try {
            linkSocket.close();
            objIn.close();
            objOut.close();
            in.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
