package com.chenyl.vvic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VvicExcutor {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(28);
    public static void excute(int start){
        Runnable run = new VvicThread(start);
        fixedThreadPool.execute(run);
    }
}