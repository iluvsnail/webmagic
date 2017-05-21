package com.chenyl.zeju;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZejuExcutor {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
    public static void excute(int start){
        Runnable run = new ZejuThread(start);
        fixedThreadPool.execute(run);
    }
}