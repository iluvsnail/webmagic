package com.chenyl.book.yinyangmishi;

import com.chenyl.util.TxtUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{//http://www.du1du.net
    public static final String BASE_URL = "http://www.du1du.net";
    public static void main( String[] args ) throws IOException {
        //http://www.du1du.net/xs207207/
        Document doc= Jsoup.connect("http://www.du1du.net/xs207207/").ignoreContentType(true).get();
        doc.select("div.chapter").stream().forEachOrdered((element)->{
            Document doc1= null;
            try {
                analysChapter(element);
            } catch (Exception e) {
                System.out.println(BASE_URL+element.select("a").attr("href"));
            }
        });
    }

    private static void analysChapter(Element element){
       try{
           Document doc1;
           doc1 = Jsoup.connect(BASE_URL+element.select("a").attr("href")).timeout(1000).header("cookie","ras=207207; UM_distinctid=1601a18828b20e-0b000ad0e431ec-3977065e-12b178-1601a18828c655; cids_AC11=207207; _vcc=7; CNZZDATA1259684034=397304525-1512261191-https%253A%252F%252Fwww.baidu.com%252F%7C1512266624").header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").ignoreContentType(true).get();
           Element element1=doc1.getElementById("content");
           element1.select("div").remove();
           System.out.println(element.select("a").text());
           TxtUtil.writeTxtFile("\n"+element.select("a").text()+"\n"+element1.text(), new File("/home/iluvsnail/yinyangmishi.txt"));
           Thread.currentThread().sleep(100);
       }catch (Exception e){
           System.out.println(BASE_URL+element.select("a").attr("href"));
           analysChapter(element);
       }
    }

}
