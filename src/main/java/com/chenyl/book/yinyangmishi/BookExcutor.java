package com.chenyl.book.yinyangmishi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookExcutor {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(12);
    public static void excute(String title,String url,int cursor){
        Runnable run = new BookThread(title,url);
        fixedThreadPool.execute(run);
    }
}