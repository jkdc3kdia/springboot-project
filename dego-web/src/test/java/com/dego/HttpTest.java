package com.dego;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description
 * @Author janus
 * @Date 2022/4/3 9:08
 * @Version 1.0
 */
public class HttpTest {

    public static void main(String[] args) {

        //hutool http 请求
        //https://blog.csdn.net/qq_41903017/article/details/116529206

        //账号api
        //https://docs.bscscan.com/api-endpoints/accounts

        JSONObject json = new JSONObject();
        json.put("username", "1332788xxxxxx");
        json.put("password", "123456.");

//        String result = HttpRequest.get("https://api.bscscan.com/api" +
//                "?module=account" +
//                "&action=balance" +
//                "&address=0x29155B492CED8B2A2C1bB3399d06aE3CD94125ed" +
//                "&apikey=DJUESBJ9JUTKH59KJQARUHW1YZXY15EIUA")
////                .header("Content-Type","application/json")
////                .header("Content-Type", "application/json")//头信息，多个头信息多次调用此方法即可
////                .header("X-Bmob-Application-Id","2f0419a31f9casdfdsf431f6cd297fdd3e28fds4af")
////                .header("X-Bmob-REST-API-Key","1e03efdas82178723afdsafsda4be0f305def6708cc6")
////                .body(json.toJSONString())
//                .execute().body();



//        String result = HttpRequest.get("https://api.bscscan.com/api" +
//                "?module=account" +
//                "&action=txlistinternal" +
//                "&startblock=16010097" +
//                "&endblock=16610097" +
//                "&page=1" +
//                "&offset=100" +
//                "&sort=asc" +
//                "&apikey=DJUESBJ9JUTKH59KJQARUHW1YZXY15EIUA")
//                .execute().body();




        String result = HttpRequest.get("https://api.bscscan.com/api" +
                "?module=account" +
                "&action=txlistinternal" +
                "&startblock=16010097" +
                "&endblock=16610097" +
                "&page=1" +
                "&offset=100" +
                "&sort=asc" +
                "&apikey=DJUESBJ9JUTKH59KJQARUHW1YZXY15EIUA")
                .execute().body();


                System.out.println(result);
    }
}
