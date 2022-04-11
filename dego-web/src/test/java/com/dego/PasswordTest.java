package com.dego;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Description
 * @Author janus
 * @Date 2021/7/31 21:46
 * @Version 1.0
 */
public class PasswordTest {

    public static void main(String[] args) {
        String md5OldPwd = new Md5Hash("12345", "dego", 2).toHex();
        System.out.println(md5OldPwd);

    }
}
