package com.chenyl.vvic;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class VvicThread implements Runnable{
    private int start;
    public VvicThread(int start) {
        this.start = start;
    }
    @Override
    public void run(){
        List<Vvic> zejuList = new ArrayList<>();
        zejuList.addAll(queryShops("http://www.vvic.com/gz/shops/"+start)) ;
        final String[] FILE_HEADER = {"Name","Addr","Money"};
        final String FILE_NAME = "/home/iluvsnail/shops.csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();

        // 这是写入CSV的代码
        try(Writer out = new FileWriter(FILE_NAME);
            CSVPrinter printer = new CSVPrinter(out, format)) {
            zejuList.forEach(e-> {
                List<String> records = new ArrayList<>();
                records.add(e.getName());
                records.add(e.getAddr());
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
    private List<Vvic> queryShops(String url) {
        List<Vvic> vvicList = new ArrayList<>();
        try {
            Document doc= Jsoup.connect(url).ignoreContentType(true).get();
            doc.getElementsByClass("items").forEach(shop->{
                String shopUrl = "http://www.vvic.com"+shop.attr("href");
                vvicList.add(queryShop(shopUrl));
            });


        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return vvicList;
    }
    private Vvic queryShop(String url) {
        Vvic vvic = new Vvic();
        try {
            Document doc= Jsoup.connect(url).ignoreContentType(true).get();
            doc.select("h2.shop-name > span").forEach(shop->{
                System.out.println(shop.text());
            });
            doc.select("ul.mt10 > li").forEach(shop->{
                System.out.println(shop.text());
            });

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return vvic;
    }
}