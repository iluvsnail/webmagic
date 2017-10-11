package com.chenyl.dataju;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       Connection con= Jsoup.connect("http://dataju.cn/Dataju/web/springSecurityCheck");
       con.data("username=iluvsnail@gmail.com&password=xujiao0923");
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        con.followRedirects(true);
        try {
            Connection.Response response= con.execute();
            System.out.println(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
