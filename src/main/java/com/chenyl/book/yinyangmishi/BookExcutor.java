package com.chenyl.book.yinyangmishi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookExcutor {
    public static final String BASE_URL = "http://www.du1du.net";
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    public static void excute(String title,String url){
        Runnable run = new BookThread(title,url);
        fixedThreadPool.execute(run);
    }
}