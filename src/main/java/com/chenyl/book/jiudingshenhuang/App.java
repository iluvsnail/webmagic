package com.chenyl.book.jiudingshenhuang;

import com.chenyl.util.TxtUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Hello world!
 *
 */
public class App 
{//http://www.du1du.net
    public static final String BASE_URL = "https://www.ybdu.com/xiaoshuo/13/13859/";
    public static void main( String[] args ) throws IOException {
        //http://www.du1du.net/xs207207/
        Document doc= Jsoup.connect("https://www.ybdu.com/xiaoshuo/13/13859/").ignoreContentType(true).get();
        AtomicBoolean flag= new AtomicBoolean(false);
        doc.select(".mulu_list a").stream().forEachOrdered((element)->{
            Document doc1= null;
            try {
                if(element.text().equals("第540章 烈阳极爆")){
                    flag.set(true);
                }
                if (flag.get())
                    analysChapter(element);
            } catch (Exception e) {
                System.out.println(BASE_URL+element.attr("href"));
            }
        });
    }

    private static void analysChapter(Element element){
       try{
           Document doc1;
           doc1 = Jsoup.connect(BASE_URL+element.select("a").attr("href")).timeout(1000).header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").ignoreContentType(true).get();
           Element element1=doc1.getElementById("htmlContent");
           System.out.println(element.text());
           TxtUtil.writeTxtFile("\n\n"+element.text()+"\n\n"+element1.text(), new File("/home/iluvsnail/jiudingshenhuang.txt"));
       }catch (Exception e){
           System.out.println(BASE_URL+element.select("a").attr("href"));
           analysChapter(element);
       }
    }

}
