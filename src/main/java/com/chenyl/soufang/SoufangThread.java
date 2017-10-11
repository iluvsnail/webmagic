package com.chenyl.soufang;

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

public class SoufangThread implements Runnable{
    private int start;
    public SoufangThread(int start) {
        this.start = start;
    }
    @Override
    public void run(){
        List<House> houseList = new ArrayList<>();
            for (int start=1;start<=18;start++){
                System.out.println(start);
                houseList.addAll(queryHouse("http://newhouse.km.fang.com/house/s/b9"+start+"/?ctm=1.km.xf_search.page.1")) ;
            }
        final String[] FILE_HEADER = {"Name","Addr","Money"};
        final String FILE_NAME = "d://house/house.csv";
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();

        // 这是写入CSV的代码
        try(Writer out = new FileWriter(FILE_NAME);
            CSVPrinter printer = new CSVPrinter(out, format)) {
            houseList.forEach(e-> {
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
    private List<House> queryHouse(String url) {
        List<House> houseList = new ArrayList<>();
        try {
            Document doc= Jsoup.connect(url).get();
            doc.getElementsByClass("nl_con").get(0).getElementsByTag("li").forEach(e->{
                if(e.getElementsByClass("nlc_details").size()!=0){
                    Element detail = e.getElementsByClass("nlc_details").get(0);
                    House house = new House();
                    house.setName(detail.getElementsByClass("nlcd_name").get(0).text());
                    try{

                        house.setMoney(detail.getElementsByClass("nhouse_price").get(0).text());
                    }catch (Exception e1){

                    }

                    try{
                        house.setAddr(detail.getElementsByClass("address").get(0).text());
                    }catch (Exception e1){

                    }
                    houseList.add(house);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
        return houseList;
    }
}