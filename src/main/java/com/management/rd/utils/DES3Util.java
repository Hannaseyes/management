package com.management.rd.utils;

import java.io.IOException;
import java.security.Key;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 3DES加密工具类
 */
public class DES3Util {
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";
    // 密钥
    private static String secretKey = "";
    // 向量
    private static String iv = "";

    static {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("DES3Key.properties");
            secretKey = properties.getProperty("SECRET_KEY");
            iv = properties.getProperty("IV");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 3DES加密
     * 
     * @param plainText
     *            普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64Util.encode(encryptData);
    }

    /**
     * 3DES解密
     * 
     * @param encryptText
     *            加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));

        return new String(decryptData, encoding);
    }

}