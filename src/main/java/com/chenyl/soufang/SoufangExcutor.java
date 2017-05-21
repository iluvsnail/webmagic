package com.chenyl.soufang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoufangExcutor {
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
    public static void excute(int start){
        Runnable run = new SoufangThread(start);
        fixedThreadPool.execute(run);
    }
}