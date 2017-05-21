package com.chenyl.zeju;

import com.alibaba.fastjson.JSON;
import com.chenyl.POI;
import com.chenyl.RedisFactory;
import com.chenyl.proxy.MapError;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import redis.clients.jedis.Jedis;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ZejuThread implements Runnable{
    private int start;
    public ZejuThread(int start) {
        this.start = start;
    }
    @Override
    public void run(){
        List<Zeju> zejuList = new ArrayList<>();
            for (int start=0;start<6;start++){
                zejuList.addAll(queryHouse("http://km.zeju.com/loupan/guandu/n"+start)) ;
            }
        final String[] FILE_HEADER = {"Name","Addr","Money"};
        final String FILE_NAME = "d://house/house.csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();

        // 这是写入CSV的代码
        try(Writer out = new FileWriter(FILE_NAME);
            CSVPrinter printer = new CSVPrinter(out, format)) {
            zejuList.forEach(e-> {
                List<String> records = new ArrayList<>();
                records.add(e.getName());
                records.add(e.getAddr());
                records.add(e.getMoney());
                try {
                    printer.printRecord(records);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private List<Zeju> queryHouse(String url) {
        List<Zeju> zejuList = new ArrayList<>();
        try {
            Document doc= Jsoup.connect(url).get();
            doc.getElementsByClass("_item_li").forEach(e->{
                Zeju zeju = new Zeju();
                Element ctn=e.getElementsByClass("s-lp-txt-center").get(0);
                zeju.setName(ctn.getElementsByClass("lp-t-title").get(0).getElementsByTag("a").get(0).text());
                zeju.setAddr(ctn.getElementsByTag("p").get(1).text());
                /////s-lp-txt-right
                Element rht=e.getElementsByClass("s-lp-txt-right").get(0);
                zeju.setMoney(rht.getElementsByTag("strong").get(0).text());
                //户型
                List<HouseType> houseTypeList = new ArrayList<>();
                e.getElementsByTag("li").forEach(li->{
                    HouseType houseType=new HouseType();
                    houseType.setName(li.getElementsByClass("s-hx-title").get(0).text());
                    houseType.setSize(li.getElementsByClass("s-hx-area").get(0).text());
                    houseType.setMoney(li.getElementsByClass("s-hx-price").get(0).text());
                    houseTypeList.add(houseType);
                });
                zeju.setTypes(houseTypeList);
                zejuList.add(zeju);
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
        return zejuList;
    }
}