package com.dego.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class AESUtils {
    // 编码方式
    private final static String ENCODE = "UTF-8";
    // 秘钥
    private final static String DEFAULT_KEY = "FNqyAH0RdASzqpxl";
    //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String ZERO_IV = "9769475569322011";

    /**
     * AES加密后再使用BASE64加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String encrypt(String content) {
        String value = "";
        try {
            if (StringUtils.isNotBlank(content)) {
                value = base64Encode(aesEncryptToBytes(content));
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return value;
    }


    /**
     * 使用BASE64解密后再用AES解密
     *
     * @param encryptStr
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr) {
        String value = "";
        try {
            value = aesDecryptByBytes(base64Decode(encryptStr));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return value;
    }

    /**
     * AES加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content) throws Exception {
        SecretKeySpec sks = new SecretKeySpec(DEFAULT_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, sks, new IvParameterSpec(ZERO_IV.getBytes()));
        return cipher.doFinal(content.getBytes(ENCODE));
    }

    /**
     * AES解密
     *
     * @param encryptBytes
     * @return
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes) throws Exception {
        SecretKeySpec sks = new SecretKeySpec(DEFAULT_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, sks, new IvParameterSpec(ZERO_IV.getBytes()));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String base64Encode(byte[] bytes) {
        return  Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decode(String base64Code) {
        return StringUtils.isBlank(base64Code) ? null :  Base64.decodeBase64(base64Code);
    }

    public static void main(String[] args) {
        String yinli = AESUtils.encrypt("{\n" +
                "    \"mobile\":15828528434,\n" +
                "    \"password\":\"123456\"\n" +
                "}");
        System.out.println(yinli);
        System.out.println(AESUtils.decrypt(yinli));
        System.out.println(AESUtils.encrypt("123456"));

    }
}
