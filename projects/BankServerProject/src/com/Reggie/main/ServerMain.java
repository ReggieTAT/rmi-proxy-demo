package com.Reggie.main;

import com.Reggie.thread.ServerThread;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerMain {
    private Map<String,Object> registerMap;
    private final String configPath="src/com/Reggie/localData/serviceConfig.xml";

    /**
     * 从服务配置文件serviceConfig.xml文件里读取已有的服务并注册到Hashtable表里
     * 使用了用于xml文件处理的第三方包dom4j
     */
    private void register(){
        registerMap=new Hashtable<>();
        try {
            SAXReader reader=new SAXReader();
            Document doc=reader.read(new File(configPath));
            Element root=doc.getRootElement();
            List<Element> list=root.elements("service");
            for (Element svcEle:list
                 ) {
                String path=svcEle.element("path").getText();
                String implPath=svcEle.element("implPath").getText();
                Class<?> classType=Class.forName(implPath);
                registerMap.put(path,classType.newInstance());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void serviceStart(){
        System.out.println("Server launching...");
        try {
            ServerSocket serverSocket = new ServerSocket(8001);
            System.out.println("Waiting for connection");
            /*
            建立线程池
             */
            ThreadPoolExecutor executor=new ThreadPoolExecutor(2,10,300,
                    TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5),
                    new ThreadPoolExecutor.CallerRunsPolicy());
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(new ServerThread(socket,registerMap));
                //下面两行是用线程池之前使用的线程代码
                //Thread thread=new Thread(new ServerThread(socket,registerMap));
                //thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ServerMain serverMain=new ServerMain();
        serverMain.register();
        serverMain.serviceStart();
    }
}
