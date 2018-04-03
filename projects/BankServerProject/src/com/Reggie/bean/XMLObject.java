package com.Reggie.bean;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 封装了对accounts.xml文件(存放账户信息)的一些读写操作,用到了处理xml文件的第三方包dom4j
 */
public class XMLObject {
    private final String path="src/com/Reggie/localData/accounts.xml";
    private Document doc;

    public XMLObject(){
        try {
            SAXReader reader=new SAXReader();
            this.doc=reader.read(new File(path));
        } catch (DocumentException e) {
            System.out.println("accounts.xml文件读取失败");
            e.printStackTrace();
        }
    }

    public void addAccount(String id,String name,String password)throws Exception{
        Element root=doc.getRootElement();
        Element actEle=root.addElement("account");
        actEle.addAttribute("id",id);
        actEle.addElement("name").setText(name);
        actEle.addElement("password").setText(password);
        actEle.addElement("deposit").setText(String.valueOf(0));
        XMLWriter writer=new XMLWriter(new FileOutputStream(path));
        writer.write(doc);
    }

    public boolean isAccountExist(String id){
        boolean result=false;
        Element root=doc.getRootElement();
        List<Element> list=root.elements("account");
        for (Element actEle:list
             ) {
            if (actEle.attributeValue("id").equals(id)){
                result=true;
                break;
            }
        }
        return result;
    }

    public String readPassword(String id){
        Element actEle=(Element)doc.selectSingleNode("//account[@id="+id+"]");
        return actEle.element("password").getText();
    }

    public String readDeposit(String id){
        Element actEle=(Element)doc.selectSingleNode("//account[@id="+id+"]");
        return actEle.element("deposit").getText();
    }

    public void writeDeposit(String id,String deposit)throws Exception{
        Element actEle=(Element)doc.selectSingleNode("//account[@id="+id+"]");
        actEle.element("deposit").setText(deposit);
        XMLWriter writer=new XMLWriter(new FileOutputStream(path));
        writer.write(doc);
    }

}
