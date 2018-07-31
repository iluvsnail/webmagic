package com.chenyl.book.threads.yipingzongshi;

import com.chenyl.util.TxtUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.List;

public class BookThread implements Runnable {
    private int cursor;
    private List<Element> list;

    String bookname = App.class.getPackage().getName()+"test";
    String path = "/home/iluvsnail/"+bookname.substring(bookname.lastIndexOf(".")+1)+".txt";
    public BookThread(int cursor, List<Element> list) {
        this.list = list;
        this.cursor = cursor;
    }

    @Override
    public void run() {
        for (int pos=cursor;pos<cursor+100 && pos<list.size();pos++){
            analysChapter(list.get(pos),pos);
        }

    }

    private void analysChapter(Element element,int pos){
        try{
            Document doc1;
            doc1 = Jsoup.connect(App.BASE_URL+element.select("a").attr("href")).timeout(1000).header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36").ignoreContentType(true).get();
            Element element1=doc1.getElementById("content");
            System.out.println(element.text());
            element1.prependText(element.text() + "\n");
            list.set(pos, element1);
            if(BookExcutor.incr()){
               list.forEach(e->{
                   try {
                       TxtUtil.writeTxtFile(e.text()+"\n\n", new File(path));
                   } catch (Exception e1) {
                       e1.printStackTrace();
                   }
               });

            }
        }catch (Exception e){
            System.out.println(App.BASE_URL+element.select("a").attr("href"));
            analysChapter(element,pos);
        }
    }
}