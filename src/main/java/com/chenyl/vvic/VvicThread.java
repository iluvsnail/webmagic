package com.chenyl.vvic;

import com.alibaba.fastjson.JSON;
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
        List<Vvic> vvicList = new ArrayList<>();
        vvicList.addAll(queryShops("http://www.vvic.com/gz/shops/"+start)) ;
        final String[] FILE_HEADER = {"店名","电话","微信","QQ","主营","地址","排行","产地","商品数"};
        final String FILE_NAME = "/home/iluvsnail/shops/shops"+start+".csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();

        // 这是写入CSV的代码
        try(Writer out = new FileWriter(FILE_NAME);
            CSVPrinter printer = new CSVPrinter(out, format)) {
            vvicList.forEach(e-> {
                List<String> records = new ArrayList<>();
                records.add(e.getName());
                records.add(e.getPhone());
                records.add(e.getWechat());
                records.add(e.getQq());
                records.add(e.getProduct());
                records.add(e.getAddr());
                records.add(e.getRank());
                records.add(e.getLocation());
                records.add(e.getCatgos());
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
            Vvic finalVvic = vvic;
            doc.select("h2.shop-name > span").forEach(shop->{
                finalVvic.setName(shop.text());
            });
            Vvic finalVvic1 = vvic;
            doc.select("ul.mt10 > li").forEach(shop->{
                String [] aa = shop.text().split("：");
                if (aa.length==2){
                    switch (aa[0]){
                        case "排行":
                            finalVvic1.setRank(aa[1].replace("第","").replace("名",""));break;
                        case "商品":
                            finalVvic1.setCatgos(aa[1].replace("件",""));break;
                        case "主营":
                            finalVvic1.setProduct(aa[1]);break;
                        case "电话":
                            finalVvic1.setPhone(aa[1]);break;
                        case "微信":
                            finalVvic1.setWechat(aa[1]);break;
                        case "QQ":
                            finalVvic1.setQq(aa[1]);break;
                        case "产地":
                            finalVvic1.setLocation(aa[1]);break;
                        case "地址":
                            finalVvic1.setAddr(aa[1]);break;
                    }
                }
            });

        } catch (IOException e) {
            //e.printStackTrace(
            System.out.println(url);
            System.out.println(e.getMessage());
        }


        try {
            Thread.currentThread().sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return vvic;
    }
}