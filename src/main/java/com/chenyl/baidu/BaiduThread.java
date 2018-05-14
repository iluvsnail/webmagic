package com.chenyl.baidu;

import org.jsoup.Jsoup;

import java.io.IOException;

public class BaiduThread implements Runnable{
    private int start;
    public BaiduThread(int start) {
        this.start = start;
    }
    @Override
    public void run(){
        try {
            for (int i=0;i<10000;i++){
                System.out.println(start+":"+i);
                Jsoup.connect("http://www.poi86.com/poi/category/51/15531.html").get().body().text();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}