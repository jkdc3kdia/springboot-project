package com.dego;

import cn.hutool.http.HttpRequest;

/**
 * @Description
 * @Author janus
 * @Date 2021/7/28 11:19
 * @Version 1.0
 */
public class TestCl {

    public static void main(String[] args) {
        String body = HttpRequest.get("http://121.199.78.67/web3/#/crypto").execute().body();
        System.out.println(body);

    }
}
