package com.chenyl.baidu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaiduExcutor {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
    public static void excute(int start){
        Runnable run = new BaiduThread(start);
        fixedThreadPool.execute(run);
    }
}