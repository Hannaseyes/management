/*
 *
 * 2012-3-30
 * 
 */
package com.management.rd.utils;

import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * MD5加密工具类
 * @author Wang Hanqing
 */
public class MD5Util {
    /**
     * false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true
     * 表示：生成24位的Base64版
     */
    public static String getMD5(String str) {
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true
        // 表示：生成24位的Base64版
        md5.setEncodeHashAsBase64(false);
        String md5Str = md5.encodePassword(str, null);
        return md5Str;
    }

    /**
     * 哈希算法
     * 
     * @throws NoSuchAlgorithmException
     */
    public static String sha_256(String passwd) throws NoSuchAlgorithmException {
        ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
        sha.setEncodeHashAsBase64(true);
        String pwd = sha.encodePassword(passwd, null);
        return pwd;
    }

    /**
     * 哈希算法 SHA-256
     */
    public static String sha_SHA_256(String passwd) {
        ShaPasswordEncoder sha = new ShaPasswordEncoder();
        sha.setEncodeHashAsBase64(false);
        String pwd = sha.encodePassword(passwd, null);
        return pwd;
    }

    /**
     * 使用动态加密盐的只需要在注册用户的时候将第二个参数换成用户名即可
     */
    public static String md5_SystemWideSaltSource(String passwd) {
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        md5.setEncodeHashAsBase64(false);

        // 使用动态加密盐的只需要在注册用户的时候将第二个参数换成用户名即可
        String pwd = md5.encodePassword(passwd, "acegisalt");
        return pwd;
    }

}
