package com.example.hotel.util;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/18 23:16
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptHelper {
    /*terrible, database limits password size to 11
     * when u want to change this size, remember to change database*/
    private static final int PASSWORD_LENGTH = 11;

    /**
     * @param password raw password
     * @return md5 code
     */
    public static String getMD5(String password) {
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("password shouldn't be empty");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            int i;
            StringBuilder builder = new StringBuilder();
            for (byte b : digest) {
                i = b;
                if (i < 0) i += 256;
                if (i < 16) builder.append(0);
                builder.append(Integer.toHexString(i));
            }
            return builder.toString().substring(0, PASSWORD_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static void main(String[] args) {
//        System.out.println((getMD5("123456")));
//    }
}
