package com.xiaobai.gossip.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class timetest {
    public static void main(String[] args) {
        String strNow = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()).toString();
        System.out.println(strNow);
    }
}
