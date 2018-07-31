package com.chenyl.book.threads.yipingzongshi;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookExcutor {
    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(15);
    private static int all=-1;
    private static int incr=0;


    public synchronized static boolean incr() {
        BookExcutor.incr++;
        return  all==incr;
    }

    public static void setAll(int all) {
        BookExcutor.all = all;
    }

    public static void excute(int cursor, List<Element> list){
        Runnable run = new BookThread(cursor,list);
        fixedThreadPool.execute(run);
    }
}