package com.chenyl;

import com.alibaba.fastjson.JSON;
import com.chenyl.proxy.MapError;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class SocialDataThread implements Runnable{
    private int start;
    private int rows;
    public SocialDataThread(int start,int rows) {
        this.start = start;
        this.rows = rows;
    }
    @Override
    public void run(){
        {
            Jedis jedis =RedisFactory.getRedisInstance();
            for(int i=start;i<start+rows;i++){
                if (jedis.hexists("poi",String.valueOf(i))){
                    continue;
                }
                POI poi=queryPOI("http://www.poi86.com/poi/amap/"+i+".html");
                if (poi.getName()!=null && !poi.getName().isEmpty()){
                    jedis.hset("poi",String.valueOf(i), JSON.toJSONString(poi));
                    jedis.hincrBy("proxy:count",System.getProperty("http.proxyHost")+":"+System.getProperty("http.proxyPort"),1);
                }
                if (i%100==0)
                    System.out.println("finish:"+i);
            }
            jedis.close();
        }

    }
    private POI queryPOI(String url) {
        POI poi = new POI();
        try {
            Document doc= Jsoup.connect(url).timeout(3000).get();
            poi.setName(doc.select(".panel-heading h1").text());
            doc.select("ul.list-group li").forEach(li->{
                String litxt = li.text();
                String[] litxts = litxt.split(":");
                if(litxts!=null && litxts.length==2){
                    switch (litxts[0]){
                        case "所属城市":poi.setCit(litxts[1]);break;
                        case "所属省份":poi.setPro(litxts[1]);break;
                        case "所属区县":poi.setCou(litxts[1]);break;
                        case "详细地址":poi.setAdd(litxts[1]);break;
                        case "电话号码":poi.setPho(litxts[1]);break;
                        case "所属分类":poi.setCat(litxts[1]);break;
                        case "大地坐标":poi.setDd(litxts[1]);break;
                        case "火星坐标":poi.setHx(litxts[1]);break;
                        case "百度坐标":poi.setBd(litxts[1]);break;
                    }
                }

            });
        } catch (IOException e) {
            //System.out.println(url+":"+e.getMessage());
            MapError.addCount();
            Jedis jedis = RedisFactory.getRedisInstance();
            if(e.getMessage().equals("HTTP error fetching URL")){
                jedis.hincrBy("proxy:count",System.getProperty("http.proxyHost")+":"+System.getProperty("http.proxyPort"),1);
            }
            if (MapError.getCount()%100==0){
                String ip = jedis.hget("proxy",String.valueOf(MapError.getIndex()));
                System.out.println(ip);
                MapError.addIndex();
                String[] ips = ip.split(":");
                System.getProperties().setProperty("http.proxyHost", ips[0]);
                System.getProperties().setProperty("http.proxyPort", ips[1]);
            }
            jedis.close();
        }
        return poi;
    }
}