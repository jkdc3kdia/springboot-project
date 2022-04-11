package com.dego;

import cn.hutool.http.HttpRequest;

import java.util.HashMap;

/**
 * @Description
 * @Author janus
 * @Date 2022/4/3 10:07
 * @Version 1.0
 */
public class AoyaTest {

    //多租户 SASS系统

    //可用性较强 https://segmentfault.com/a/1190000038389543
    //https://juejin.cn/post/6847902217475719176
    //https://blog.csdn.net/johntsu2006/article/details/100623090

    //官方网站
    //https://docs.bscscan.com/api-endpoints/accounts

    //领导人地址
    private static final String LEADER_ADDRESS = "0x9de342797AE8A269c18C5c9a2C5dAAA6045163EB";
    //大户钱包
    private static final String MAIN_ADDRESS = "0x97d48f4297f4b1b8c9902a325b50703544ad2b7d";
    private static final String API_KEY = "DJUESBJ9JUTKH59KJQARUHW1YZXY15EIUA";


    public static void main(String[] args) {
//        getTransactionsByAddress(LEADER_ADDRESS);

        getTxlistinternalByAddress("0xFDf4fc8b6B73718Dfc90Dc2Fa9E27D2a1B580CCa");


    }


    private static void getTxlistinternalByAddress(String address){
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.bscscan.com/api")
                .append("?module=account")
                .append("&action=txlist&address=").append(address)
                .append("&startblock=0").append("&endblock=99999999")
                .append("&page=1").append("&offset=10")
                .append("&sort=asc").append("&apikey=")
                .append(API_KEY);
        String url = sb.toString();
        System.out.println(url);
        HashMap<String, String> headers = new HashMap<>();//存放请求头，可以存放多个请求头
        headers.put("cookie", "__stripe_mid=6655794b-e300-464f-8304-d15420e12d3c4888c6; _gid=GA1.2.623558959.1649234234; amp_fef1e8=105666a4-3739-4fbd-8516-affa28c2d2b3R...1g03tg8ge.1g03tnt0s.u.3.11; __cf_bm=5raFhiMOZAA6Cdxdl93VvsLc3kTf4B5_UFDLLaMVpzs-1649401249-0-AU15XSp2j22qNxAhU+M5rRQXQq4Y7ONAP9mcTjlRlIbU9Ock8avqPS6QO05UsMlym+Nw865P5ne/7RpqI+gKco7Yz9tgbJKJ5BHisT3fNPhT2ny1Czwi58QCkwc8WNh6zQ==; _ga=GA1.2.146656865.1621049736; _ga_PQY6J2Q8EP=GS1.1.1649399043.14.1.1649401075.0");
        String body = HttpRequest.get(url).execute().body();
        System.out.println(body);

    }


    private static void getTransactionsByAddress(String address){


    }
}
