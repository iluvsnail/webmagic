package com.chenyl.book.dianfengwudi;

import com.chenyl.util.TxtUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{//http://www.du1du.net
    public static final String BASE_URL = "http://www.jingcaiyuedu.com";
    public static void main( String[] args ) throws IOException {
        //http://www.du1du.net/xs207207/
        Document doc= Jsoup.connect("http://www.jingcaiyuedu.com/book/125670.html").ignoreContentType(true).get();
        doc.select("#list a").stream().forEachOrdered((element)->{
            Document doc1= null;
            try {
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
           Element element1=doc1.getElementsByClass("panel-body content-body content-ext").first();
           System.out.println(element.text());
           TxtUtil.writeTxtFile("\n"+element1.text(), new File("E://dianfengwudi.txt"));
           Thread.currentThread().sleep(10);
       }catch (Exception e){
           System.out.println(BASE_URL+element.select("a").attr("href"));
           analysChapter(element);
       }
    }

}
