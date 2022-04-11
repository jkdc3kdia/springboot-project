package com.dego.util.crypto;

import com.dego.exception.BusinessException;
import org.apache.commons.codec.binary.Hex;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * MD5加密对象
 *
 * @author wangjinsi
 */
public abstract class MD5 {

    private final static String DM5 = "MD5";

    /**
     * MD5加密
     *
     * @param password 密码串（未加密）
     * @return 密码串（已加密）
     */
    public static String encrypt(String password) {
        try {
            MessageDigest alg = MessageDigest.getInstance(DM5);
            alg.update(password.getBytes());
            byte[] digesta = alg.digest();
            return byte2hex(digesta);
        } catch (NoSuchAlgorithmException NsEx) {
            return null;
        }
    }

    /**
     * MD5加密
     * @param data
     * @return
     */
    public static String encrypt(byte[] data) {
        try {
            MessageDigest alg = MessageDigest.getInstance(DM5);
            alg.update(data);
            byte[] digesta = alg.digest();
            return byte2hex(digesta);
        } catch (NoSuchAlgorithmException NsEx) {
            return null;
        }
    }

    private static String byte2hex(byte[] bstr) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < bstr.length; n++) {
            stmp = (Integer.toHexString(bstr[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0");
                hs.append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }

    /**
     * 获取大文件MD5值
     *
     * @param file
     * @return : String
     * @throws IOException
     * @Description :
     * @Method_Name : getFileMD5String
     * @Creation Date  : 2014-5-15 下午1:31:00
     * @Author :  <a href="mailto:zuiwoxing@gmail.com">刘德建</a>
     */
    public static String getFileMd5(File file) throws IOException {
        FileInputStream in = null;
        FileChannel ch = null;
        try {
            MessageDigest alg = MessageDigest.getInstance(DM5);
            if (file == null || !file.exists()) {
                throw new BusinessException("file does't exist!");
            }
            in = new FileInputStream(file);
            ch = in.getChannel();
            MappedByteBuffer inputBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            alg.update(inputBuffer);

            byte[] digesta = alg.digest();
            return byte2hex(digesta);
        } catch (NoSuchAlgorithmException NsEx) {
            return null;
        } finally {
            if (in != null) {
                in.close();
            }
            if (ch != null) {
                ch.close();
            }
        }
    }

    /**
     * 获取大文件MD5值
     *
     * @param file
     * @return
     * @throws IOException
     * @author : <a href="mailto:dejianliu@ebnew.com">liudejian</a>  2014-6-16 上午8:58:51
     */
    public static String getBigFileMd5(File file) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream buf = null;
        try {
            MessageDigest alg = MessageDigest.getInstance(DM5);
            fis = new FileInputStream(file);
            buf = new BufferedInputStream(fis);

            byte[] buffer = new byte[1024 * 256];
            int length;

            while ((length = buf.read(buffer)) != -1) {
                alg.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(alg.digest()));
        } catch (Exception NsEx) {
            return null;
        } finally {
            if (buf != null) {
                buf.close();
            }
            if (fis != null) {
                fis.close();
            }

        }
    }

}