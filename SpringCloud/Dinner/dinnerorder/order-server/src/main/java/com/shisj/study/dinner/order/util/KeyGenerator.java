package com.shisj.study.dinner.order.util;

import java.util.UUID;

public class KeyGenerator {

    public static String gen(){
        String ret = UUID.randomUUID().toString().replace("-","");
        return  ret.substring(0,10);
    }

    public static String gen(int size){
        String ret = UUID.randomUUID().toString().replace("-","");
        return  ret.substring(0,size);
    }
}
