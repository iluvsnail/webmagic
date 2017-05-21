package com.chenyl.proxy;


import com.chenyl.RedisFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class Proxy {
    public static void main(String[] args) {
        final int[] i = {0};
        for(int j=1;j<500;j++){
            try {
                System.out.println(j);
                Jedis jedis = RedisFactory.getRedisInstance();
                Document doc= Jsoup.connect("http://www.kuaidaili.com/free/intr/"+j+"/").followRedirects(true).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36")
                        .method(Connection.Method.POST).get();
                doc.select("tbody tr").forEach(d->{
                    i[0]++;
                    String[] ips = d.text().split(" ");
                    jedis.hset("proxy",String.valueOf(i[0]),ips[0] +":"+ips[1]);

                });
                jedis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.currentThread().sleep(990);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}