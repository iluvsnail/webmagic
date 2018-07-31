package com.chenyl.book.yipingzongshi;

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
    public static final String BASE_URL = "http://www.biqukan.com";
    public static void main( String[] args ) throws IOException {
        String bookname = App.class.getPackage().getName();
        String path = "/home/iluvsnail/"+bookname.substring(bookname.lastIndexOf(".")+1)+".txt";
        //http://www.du1du.net/xs207207/
        Document doc= Jsoup.connect(BASE_URL+"/36_36832/").ignoreContentType(true).get();

        AtomicBoolean flag = new AtomicBoolean(false);
        doc.select(".listmain a").stream().forEachOrdered((element)->{
            Document doc1= null;
            try {
                if(element.text().contains("第一章"))
                    flag.set(true);
                if(flag.get())
                    analysChapter(element,path);
            } catch (Exception e) {
                System.out.println(BASE_URL+element.attr("href"));
            }
        });
    }

    private static void analysChapter(Element element,String path){
       try{
           Document doc1;
           doc1 = Jsoup.connect(BASE_URL+element.select("a").attr("href")).timeout(1000).header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").ignoreContentType(true).get();
           Element element1=doc1.getElementById("content");
           System.out.println(element.text());
           TxtUtil.writeTxtFile("\n\n"+element.text()+"\n\n"+element1.text(), new File(path));
           //Thread.currentThread().sleep(1000);
       }catch (Exception e){
           System.out.println(BASE_URL+element.select("a").attr("href"));
           analysChapter(element,path);
       }
    }

}
