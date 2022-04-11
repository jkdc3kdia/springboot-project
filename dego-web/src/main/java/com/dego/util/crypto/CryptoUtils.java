package com.dego.util.crypto;

import com.dego.exception.BusinessException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.commons.CommonUtils;
import org.jasypt.util.binary.BasicBinaryEncryptor;
import org.jasypt.util.binary.StrongBinaryEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;


/**
 * @author <a href="mailto:zuiwoxing@gmail.com">刘德建</a>
 * @description: 对称加密(为了使并发加，解密安全，所以去掉静态方法.)
 * @Date 2012 2012-10-9 上午11:01:04
 * @since Ver 1.0
 */
public class CryptoUtils {

    /**
     * MD5
     */
    private static final String defaultPwd = "6864F6FE8A2A46CD64DF5284B1F40A14";

    /**
     * 对文件分段加密，分段大小10(M)
     */
    private static final int CACHE_SIZE = 10;

    /**
     * @param message 待加密信息
     * @param key     密码 default defaultPwd
     * @return
     * @description 加密
     */
    public String basicEncrypt(String message, String key) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                : defaultPwd);
        return textEncryptor.encrypt(message);
    }

    /**
     * @param message 待解密信息
     * @param key     密文
     * @return
     * @description 解密
     */
    public String basicDecrypt(String message, String key) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                : defaultPwd);
        return textEncryptor.decrypt(message);
    }

    /**
     * @param message 待加密信息
     * @param key     密码 default defaultPwd
     * @return
     * @description 强加密
     */
    public String strongEncrypt(String message, String key) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                : defaultPwd);
        return textEncryptor.encrypt(message);
    }

    /**
     * @param message 待解密信息
     * @param key     密码 default defaultPwd
     * @return
     * @description 解密
     */
    public String strongDecrypt(String message, String key) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                : defaultPwd);
        return textEncryptor.decrypt(message);
    }

    /**
     * @param byteBuf 待加密字节数组
     * @param key      密码 default defaultPwd
     * @return
     * @description 对文件流加密
     */
    public byte[] basicBinaryEncrypt(byte[] byteBuf, String key) {
        BasicBinaryEncryptor binaryEncryptor = new BasicBinaryEncryptor();
        binaryEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                : defaultPwd);
        return binaryEncryptor.encrypt(byteBuf);
    }

    /**
     * @param byteBuf 待解密字节数组
     * @param key     key 密码 default defaultPwd
     * @return
     * @description 对文件解密
     */
    public byte[] basicBinaryDecrypt(byte[] byteBuf, String key) {
        BasicBinaryEncryptor binaryEncryptor = new BasicBinaryEncryptor();
        binaryEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                : defaultPwd);
        return binaryEncryptor.decrypt(byteBuf);
    }

    /**
     * @param byteBuf 待加密字节数组
     * @param key      密码 default defaultPwd
     * @return
     * @description 对文件流强加密
     */
    public byte[] strongBinaryEncrypt(byte[] byteBuf, String key) {
        StrongBinaryEncryptor binaryEncryptor = new StrongBinaryEncryptor();
        binaryEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                : defaultPwd);
        return binaryEncryptor.encrypt(byteBuf);
    }

    /**
     * @param byteBuf 待解密字节数组
     * @param key     key 密码 default defaultPwd
     * @return
     * @description 对文件解密
     */
    public byte[] strongBinaryDecrypt(byte[] byteBuf, String key) {
        StrongBinaryEncryptor binaryEncryptor = new StrongBinaryEncryptor();
        binaryEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                : defaultPwd);
        return binaryEncryptor.decrypt(byteBuf);
    }

    /**
     * 该方法可应用于大小文件加密(大文件则是cacheSize大小加密一次)
     *
     * @param plaintextFile    待加密文件(明文)
     * @param cipherFile 加密后的文件(密文)
     * @param key        密钥文件
     * @throws Exception
     */
    public void encryptFilePBE(File plaintextFile, File cipherFile, String key) throws Exception {
        InputStream datain = null;
        OutputStream dataOut = null;

        byte[] old = null;
        byte[] buf = null;
        try {
            if (plaintextFile == null || !plaintextFile.exists() || !plaintextFile.isFile()) {
                throw new BusinessException("待加密文件不存在！");
            }
            if (cipherFile == null) {
                throw new BusinessException("请指定密文文件！");
            }
            StrongBinaryEncryptor binaryEncryptor = new StrongBinaryEncryptor();
            binaryEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                    : defaultPwd);

            datain = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(plaintextFile)));
            dataOut = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(cipherFile)));
            old = new byte[datain.available()];
            datain.read(old);
            buf = binaryEncryptor.encrypt(old);
            dataOut.write(buf, 0, buf.length);
            dataOut.flush();

        } finally {
            if (datain != null) {
                datain.close();
            }
            if (dataOut != null) {
                dataOut.close();
            }
        }
    }

    /**
     * 该方法可应用于大小文件加密(大文件则是cacheSize大小加密一次)
     *
     * @param cipherFile    加密后的文件(密文)
     * @param plaintextFile 解密后文件(明文)
     * @param key           密钥文件
     * @throws Exception
     */
    public void decryptFilePBE(File cipherFile, File plaintextFile, String key) throws Exception {
        InputStream datain = null;
        OutputStream dataOut = null;

        byte[] old = null;
        byte[] buf = null;
        try {
            if (cipherFile == null || !cipherFile.exists() || !cipherFile.isFile()) {
                throw new BusinessException("待解密文件不存在！");
            }
            if (plaintextFile == null) {
                throw new BusinessException("请指定明文文件！");
            }
            StrongBinaryEncryptor binaryEncryptor = new StrongBinaryEncryptor();
            binaryEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                    : defaultPwd);

            datain = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(cipherFile)));
            dataOut = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(plaintextFile)));
            old = new byte[datain.available()];
            datain.read(old);
            buf = binaryEncryptor.decrypt(old);
            dataOut.write(buf, 0, buf.length);
            dataOut.flush();

        } finally {
            if (datain != null) {
                datain.close();
            }
            if (dataOut != null) {
                dataOut.close();
            }
        }
    }

    /**
     * 该方法可应用于大小文件加密(大文件则是cacheSize大小加密一次)
     *
     * @param plaintextFile    待加密文件(明文)
     * @param cipherFile 加密后的文件(密文)
     * @param cacheSize  缓存大小(MB)
     * @param key        密钥文件
     * @throws Exception
     */
    @Deprecated
    public void encryptFilePBE(File plaintextFile, File cipherFile,
                               Integer cacheSize, String key) throws Exception {
        InputStream datain = null;
        OutputStream dataOut = null;
        int size = 0;
        byte[] old = null;
        byte[] buf = null;
        try {
            if (plaintextFile == null || !plaintextFile.exists() || !plaintextFile.isFile()) {
                throw new BusinessException("待加密文件不存在！");
            }
            if (cipherFile == null) {
                throw new BusinessException("请指定密文文件！");
            }
            StrongBinaryEncryptor binaryEncryptor = new StrongBinaryEncryptor();
            binaryEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                    : defaultPwd);
            if (cacheSize == null || cacheSize.intValue() <= 0) {
                size = CACHE_SIZE * 1024;
            } else {
                size = 1024 * cacheSize.intValue();
            }

            datain = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(plaintextFile)));
            dataOut = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(cipherFile)));
            old = new byte[size];
            int len = 0;
            while (true) {
                len = datain.read(old);
                if (len < size) {
                    buf = binaryEncryptor.encrypt(old);
                    // 注意这里必需是buf.length 而不能是len 因为加密后长度会增加16位
                    dataOut.write(buf, 0, buf.length);
                    dataOut.flush();
                    break;
                } else {
                    buf = binaryEncryptor.encrypt(old);
                    dataOut.write(buf, 0, buf.length);
                    dataOut.flush();
                }
            }
        } finally {
            if (datain != null) {
                datain.close();
            }
            if (dataOut != null) {
                dataOut.close();
            }
        }
    }


    /**
     * 该方法可应用于大小文件解密(大文件则是cacheSize大小加密一次)
     *
     * @param cipherFile        (密文)
     *                          加密后的文件
     * @param plaintextFile(明文) 待解密后文件
     * @param cacheSize         缓存大小(MB)
     * @param key               密钥文件
     * @throws Exception
     */
    @Deprecated
    public void decryptFilePBE(File cipherFile, File plaintextFile,
                               Integer cacheSize, String key) throws Exception {
        InputStream datain = null;
        OutputStream dataOut = null;
        int size = 0;
        byte[] old = null;
        byte[] buf = null;
        try {
            if (cipherFile == null || !cipherFile.exists() || !cipherFile.isFile()) {
                throw new BusinessException("待解密文件不存在！");
            }
            if (plaintextFile == null) {
                throw new BusinessException("请指定明文文件！");
            }
            StrongBinaryEncryptor binaryEncryptor = new StrongBinaryEncryptor();
            binaryEncryptor.setPassword(CommonUtils.isNotEmpty(key) ? key
                    : defaultPwd);
            if (cacheSize == null || cacheSize.intValue() <= 0) {
                size = CACHE_SIZE * 1024 + 16;
            } else {
                size = 1024 * cacheSize.intValue() + 16;
            }

            datain = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(cipherFile)));
            dataOut = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(plaintextFile)));
            old = new byte[size];
            int len = 0;
            while (true) {
                len = datain.read(old);
                if (len < size) {
                    buf = binaryEncryptor.decrypt(old);
                    // 注意这里必需是buf.length 而不能是len 因为加密后长度会增加16位
                    dataOut.write(buf, 0, buf.length);
                    dataOut.flush();
                    break;
                } else {
                    buf = binaryEncryptor.decrypt(old);
                    dataOut.write(buf);
                    dataOut.flush();
                }
            }
        } finally {
            if (datain != null) {
                datain.close();
            }
            if (dataOut != null) {
                dataOut.close();
            }
        }
    }


    /**
     * 创建密钥
     *
     * @return 密钥字符串
     * @throws Exception
     */
    public String createAESCipher() throws Exception {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(256);
        SecureRandom rom = new SecureRandom();
        gen.init(rom);
        SecretKey key = gen.generateKey();
        byte[] buf = key.getEncoded();
        return base64EncodeString(buf);
    }


    /**
     * @param plaintextFile 明文文件
     * @param cipherFile    密钥文件
     * @param cacheSize     段大小(M)
     * @param key           密钥
     * @throws Exception
     * @description AES 文件加密
     */
    public void encryptFileAES(File plaintextFile, File cipherFile,
                               Integer cacheSize, String key) throws Exception {
        InputStream datain = null;
        OutputStream dataOut = null;
        int size = 0;
        byte[] old = null;
        byte[] buf = null;
        SecretKey keyAes = null;
        Cipher cipher = null;
        String KEY_ALGORITHM = "AES";
        String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
        try {
            if (plaintextFile == null || !plaintextFile.exists() || !plaintextFile.isFile()) {
                throw new BusinessException("待加密文件不存在！");
            }
            if (cipherFile == null) {
                throw new BusinessException("请指定密文文件！");
            }
            if (StringUtils.isEmpty(key)) {
                throw new BusinessException("密钥不能为空！");
            }
            int mode = Cipher.ENCRYPT_MODE; // 加密模式常量
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            keyAes = new SecretKeySpec(base64Decode(key), KEY_ALGORITHM);
            if (cacheSize == null || cacheSize.intValue() <= 0) {
                size = CACHE_SIZE;
            } else {
                size = 1024 * cacheSize.intValue();
            }
            datain = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(plaintextFile)));
            dataOut = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(cipherFile)));
            old = new byte[size];
            int len = 0;
            while (true) {
                len = datain.read(old);
                if (len < size) {

                    cipher.init(mode, keyAes);
                    // 注意当len<size时就必需加上0到len,表示对0到len字节加密
                    buf = cipher.doFinal(old, 0, len);
                    // 注意这里必需是buf.length 而不能是len 因为加密后长度会增加16位
                    dataOut.write(buf, 0, buf.length);
                    dataOut.flush();
                    break;
                } else {
                    cipher.init(mode, keyAes);
                    // 这里默认是对0到old.length字节长度进行加密
                    buf = cipher.doFinal(old);
                    dataOut.write(buf, 0, buf.length);
                    dataOut.flush();
                }
            }
        } finally {
            if (datain != null) {
                datain.close();
            }
            if (dataOut != null) {
                dataOut.close();
            }
        }
    }



    /**
     * @param cipherFile    密钥文件
     * @param plaintextFile 明文文件
     * @param cacheSize     段大小(M)
     * @param key           密钥
     * @throws Exception
     * @description AES文件解密
     */
    public void decryptFileAES(File cipherFile, File plaintextFile,
                               Integer cacheSize, String key) throws Exception {
        InputStream datain = null;
        OutputStream dataOut = null;
        int size = 0;
        byte[] old = null;
        byte[] buf = null;
        SecretKey keyAes = null;
        Cipher cipher = null;
        String KEY_ALGORITHM = "AES";
        String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
        try {
            if (cipherFile == null || !cipherFile.exists() || !cipherFile.isFile()) {
                throw new BusinessException("待解密文件不存在！");
            }
            if (plaintextFile == null) {
                throw new BusinessException("请指定明文文件！");
            }
            if (StringUtils.isEmpty(key)) {
                throw new BusinessException("密钥不能为空！");
            }

            int mode = Cipher.DECRYPT_MODE; // 解密模式常量
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            keyAes = new SecretKeySpec(base64Decode(key), KEY_ALGORITHM);

            if (cacheSize == null || cacheSize.intValue() <= 0) {
                size = CACHE_SIZE + 16;
            } else {
                size = 1024 * cacheSize.intValue() + 16;
            }

            datain = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(cipherFile)));
            dataOut = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(plaintextFile)));
            old = new byte[size];
            int len = 0;
            while (true) {
                len = datain.read(old);
                if (len < size) {
                    cipher.init(mode, keyAes);
                    buf = cipher.doFinal(old, 0, len);
                    dataOut.write(buf, 0, buf.length);
                    dataOut.flush();
                    break;
                } else {
                    cipher.init(mode, keyAes);
                    buf = cipher.doFinal(old);
                    dataOut.write(buf, 0, buf.length);
                    dataOut.flush();
                }
            }
        } finally {
            if (datain != null) {
                datain.close();
            }
            if (dataOut != null) {
                dataOut.close();
            }
        }
    }


    public String base64EncodeString(byte[] text) {
        return Base64.encodeBase64String(text);
    }

    public byte[] base64Encode(byte[] text) {
        return Base64.encodeBase64(text);
    }

    public byte[] base64Decode(String text) {
        return Base64.decodeBase64(text);
    }

    public byte[] base64Decode(byte[] text) {
        return Base64.decodeBase64(text);
    }

    public static void main(String[] args) throws Exception {
        CryptoUtils crytoUtils = new CryptoUtils();

        System.out.println(crytoUtils.createAESCipher());
        System.out.println(crytoUtils.createAESCipher());
 		String key = crytoUtils.createAESCipher();

 /*       String xxx = crytoUtils.decryptAESFromBase64(res,key);
        System.out.println(xxx);*/

//		String message = "必联sssssssssssssssssssssssaaaaaaaaaaaaaaaaaaaaaawwwwwwwwwwwwwwwwwwwwwwwwwwsssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
//		// String res = new String(Base64.encodeBase64(strongEncrypt(message,
//		// key).getBytes(), false));
//
//		String res = strongEncrypt(message, key);
//		System.out.println("强加密后:" + res);
//		System.out.println("密文长度:" + res.length());
//		res = strongDecrypt(res, key);
//		System.out.println("强解密后:" + res);
//
//		System.out.println("=================================");
//		String tres = basicEncrypt(message, key);
//		System.out.println("加密后:" + tres);
//		System.out.println("密文长度:" + tres.length());
//		tres = basicDecrypt(tres, key);
//		System.out.println("解密后:" + tres);

//		encryptFilePBE(new File("G:/activiti-5.10.zip"), new File("G:/activiti.zip"),10, key);
//		decryptFilePBE(new File("G:/activiti.zip"), new File("G:/activiti-解密后.zip"), 10, key);
//		encryptFile(new File("G:/1.doc"), new File("G:/2.doc"),null,key);
//		decryptFile(new File("G:/2.doc"), new File("G:/2_1.doc"),null,key);

//		String keys = createAESCipher();
//		System.out.println("key:"+keys);

//        long startTime = System.currentTimeMillis();
//        
//		String key = "OnmqJgA0wM0jU5HvSCtSJ06SNc02A7FLDQygqlS77S8=";
////		System.out.println(key.length());
//		File file = new File("G:/mytest.rar");
//		/**
//		 * 文件大小:858 M
//         * 耗时:31 秒
//		 */
////		encryptFileAES(file, new File("G:/mytest_AES.zip"),10, key);
//		/**
//		 * 文件大小:859 M
//         * 耗时:17 秒
//		 */
//		crytoUtils.decryptFileAES(new File("G:/mytest_AES.zip"), new File("G:/mytest_AES_01.zip"), 10, key);
//		long endTime = System.currentTimeMillis();
//		System.out.println("文件大小:"+file.length()/1024/1024+" M");
//		System.out.println("耗时:"+((endTime - startTime)/1000)+" 秒");


    }

}
