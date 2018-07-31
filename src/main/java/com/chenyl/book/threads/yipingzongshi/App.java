package com.chenyl.book.threads.yipingzongshi;

import com.chenyl.util.TxtUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hello world!
 *
 */
public class App 
{//http://www.du1du.net
    public static final String BASE_URL = "http://www.biqukan.com";
    public static void main( String[] args ) throws IOException {
        List<Element> list = new ArrayList<>();
        //http://www.du1du.net/xs207207/
        Document doc= Jsoup.connect(BASE_URL+"/36_36832/").ignoreContentType(true).get();

        AtomicBoolean flag = new AtomicBoolean(false);
        doc.select(".listmain a").stream().forEachOrdered((element)->{
            Document doc1= null;
            try {
                if(element.text().contains("第一章"))
                    flag.set(true);
                if(flag.get())
                    list.add(element);
            } catch (Exception e) {
                System.out.println(BASE_URL+element.attr("href"));
            }
        });

        BookExcutor.setAll(list.size());

        //所有列表
        for(int cursor=0;cursor<list.size();cursor+=100){
            BookExcutor.excute(cursor,list);
        }
    }

}
