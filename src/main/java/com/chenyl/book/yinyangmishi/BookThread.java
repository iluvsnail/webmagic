package com.chenyl.book.yinyangmishi;

import com.chenyl.util.TxtUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

public class BookThread implements Runnable{
    private String url;
    private String title;
    public BookThread(String title ,String url) {
        this.url = url;
        this.title = title;
    }
    @Override
    public void run(){
        try {
            Document doc= Jsoup.connect(url).ignoreContentType(true).get();
            Element element=doc.getElementById("content");
            element.select("div").remove();
            TxtUtil.writeTxtFile("\n"+title+"\n"+element.text(), new File("/home/iluvsnail/yinyangmishi.txt"));
            Thread.currentThread().sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}