package com.chenyl.proxy;

/**
 * Created by iluvsnail on 2017/5/7.
 */
public class MapError {
    private static int count =0;
    private static int index =0;
    public synchronized static int getCount(){
        return count;
    }
    public synchronized  static void addCount(){
        count++;
    }
    public synchronized static int getIndex(){
        return index%480+1;
    }
    public synchronized  static void addIndex(){
        index++;
    }
}
