package com.jikexueyuan.httpreview;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class JsonAndXML extends AppCompatActivity {

    private ByteArrayOutputStream dist;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//1、JSON对象与其文本文件的创建---------------------------------------------------------------------------------
        JSONObject root = new JSONObject();
        try {
            root.put("cat", "it");//
            JSONObject lan1 = new JSONObject();
            lan1.put("id", 1);
            lan1.put("ide", "Eclipse");
            lan1.put("name", "java");
            JSONObject lan2 = new JSONObject();
            lan2.put("id", 2);
            lan2.put("ide", "XCode");
            lan2.put("name", "Swift");
            JSONObject lan3 = new JSONObject();
            lan3.put("id", 3);
            lan3.put("ide", "Visual Studio");
            lan3.put("name", "C#");
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(lan1);
            jsonArray.put(lan2);
            jsonArray.put(lan3);
            root.put("languages", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        File extDir = Environment.getExternalStorageDirectory();
        File file = new File(extDir, "jsonData.txt");
        System.out.println("文件绝对路径：" + file.getAbsolutePath());
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileChannel channel = fileOutputStream.getChannel();
            channel.write(ByteBuffer.wrap((root.toString()).getBytes("utf-8")));

            channel.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("JSON对象的创建：" + root);
        //2、JSON文件读取---------------------------------------------------------------------------------


        if (file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(extDir + "/jsonData.txt");


                FileChannel channel = fileInputStream.getChannel();
                dist = new ByteArrayOutputStream();
                ByteBuffer buffer = ByteBuffer.allocate(1024);//allocateDirect方法直接分配系统内存，较快，容易内存泄漏，
                // 而前者是java虚拟机内存，较慢，较安全。
                int result = 0;
                while ((result = channel.read(buffer)) != -1) {
                    buffer.flip(); //flip后，就可以用buffer.remaining()来获取buffer的长度了
                    dist.write(buffer.array(), 0, buffer.remaining());
                    buffer.clear();
                }
                System.out.println("JSON文本文件读取：" + new String(dist.toByteArray(), "utf-8"));//只要使用ByteArrayOutputStream能将文件读取为字节数组，
                // 那么就可用其相关api读取任何文件了；!!!控制台单行sout输出的长度貌似有限，比如，如果在创建Data.txt文件的时候，50个JSON对象都没有换行，
                //读取的时候，只会sout到第28个对象的字符串！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！

                // 事实上，就算不用ByteArrayOutputStream，将文件读取为ByteBuffer，也足够用了。
                channel.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //3、JSON文本文件的解析：
        try {
            JSONObject rootResolve = new JSONObject(new String(dist.toByteArray(), "utf-8"));
            System.out.println("cat=" + rootResolve.getString("cat"));
            JSONArray languages = rootResolve.getJSONArray("languages");
            for (int i = 0; i < languages.length(); i++) {
//                System.out.println(languages.getString(i));//可直接得到数组的字符串并输出
                JSONObject lan = languages.getJSONObject(i);
                System.out.println("id=" + lan.get("id"));
                System.out.println("id=" + lan.get("ide"));
                System.out.println("id=" + lan.get("name"));
            }
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//---------------------------------------------------------------------------------------------
        useXml(extDir);
    }

    private void useXml(File extDir) {
        //1、XML文件的生成：
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docuBulder = builderFactory.newDocumentBuilder();
            Document newxml = docuBulder.newDocument();
            Element languages = newxml.createElement("Languages");
            languages.setAttribute("cat", "it");

            Element lan1 = newxml.createElement("lan");
            lan1.setAttribute("id", "1");
            Element name1 = newxml.createElement("name");
            name1.setTextContent("Java");
            Element ide1 = newxml.createElement("ide");
            ide1.setTextContent("Eclipse");
            lan1.appendChild(name1);
            lan1.appendChild(ide1);
            languages.appendChild(lan1);

            Element lan2 = newxml.createElement("lan");
            lan2.setAttribute("id", "2");
            Element name2 = newxml.createElement("name");
            name2.setTextContent("Swift");
            Element ide2 = newxml.createElement("ide");
            ide2.setTextContent("XCode");
            lan2.appendChild(name2);
            lan2.appendChild(ide2);
            languages.appendChild(lan2);

            Element lan3 = newxml.createElement("lan");
            lan3.setAttribute("id", "3");
            Element name3 = newxml.createElement("name");
            name3.setTextContent("C#");
            Element ide3 = newxml.createElement("ide");
            ide3.setTextContent("Visual Studio");
            lan3.appendChild(name3);
            lan3.appendChild(ide3);
            languages.appendChild(lan3);

            newxml.appendChild(languages);
//2、打印出XML文件-----------------------------------------------------------------------------------
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(newxml), new StreamResult(sw));
            tv = (TextView) findViewById(R.id.tv);
            tv.setText(sw.toString()+"-----------------------------------------\n");
            System.out.println("XML文件的输出：" + sw.toString());
//3、保存XML文件
            File fileXML = new File(extDir, "xmlData.xml");
            FileOutputStream fileOutputStream = new FileOutputStream(fileXML);
            FileChannel channel = fileOutputStream.getChannel();
            channel.write(ByteBuffer.wrap(sw.toString().getBytes("utf-8")));

            channel.close();
            fileOutputStream.close();
        } catch (ParserConfigurationException | TransformerException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//4、XML文件的解析-----------------------------------------------------------------------------------
        System.out.println();
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder bulder = builderFactory.newDocumentBuilder();
            Document document = bulder.parse(new FileInputStream(extDir + "/xmlData.xml"));
            Element element=document.getDocumentElement();
            NodeList list=element.getElementsByTagName("lan");
            for (int i = 0; i < list.getLength(); i++) {
                Element lan= (Element) list.item(i);
                tv.append(lan.getAttribute("id")+"\n");
                tv.append(lan.getElementsByTagName("name").item(0).getTextContent()+"\n");
                tv.append(lan.getElementsByTagName("ide").item(0).getTextContent()+"\n");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
