package com.graduation.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {


    public static String MD5Hex(String password){
        return DigestUtils.md5Hex(password);
    }

    public static void main(String[] args) {
        byte q = 'a';
        byte w = 'a';
        System.out.println(q|w);

        String a= DigestUtils.md5Hex("970108");
        System.out.println(a);
    }
}
