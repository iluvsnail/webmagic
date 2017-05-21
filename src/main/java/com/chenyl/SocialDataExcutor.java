package com.chenyl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocialDataExcutor {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
    public static void excute(int start ,int rows){
        Runnable run = new SocialDataThread(start,rows);
        fixedThreadPool.execute(run);
    }
}