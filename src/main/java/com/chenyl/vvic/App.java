package com.chenyl.vvic;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<Integer> shopsList = new ArrayList<Integer>(){{
            add(10);
            add(19);
            add(12);
            add(13);
            add(14);
            add(15);
            add(18);
            add(11);
            add(37);
            add(17);
            add(34);
            add(20);
            add(16);
            add(23);
            add(25);
            add(42);
            add(35);
            add(36);
            add(26);
            add(28);
            add(41);
            add(27);
            add(46);
            add(43);
            add(39);
            add(45);
            add(44);
            add(38);
        }};
        for (int i:shopsList){
            VvicExcutor.excute(i);
        }
    }

}
