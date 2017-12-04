package com.chenyl.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class TxtUtil {
    public static boolean writeTxtFile(String content,File fileName)throws Exception{
        RandomAccessFile mm=null;
        boolean flag=false;
        FileOutputStream o=null;
        try {
            o = new FileOutputStream(fileName,true);
            content=content.replaceAll(" {4}","    \n");
            //System.out.println(content);
            o.write(content.getBytes("UTF8"));
            o.close();
            flag=true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            if(mm!=null){
                mm.close();
            }
        }
        return flag;
    }
}
