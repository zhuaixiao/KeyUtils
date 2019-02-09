package com.example.keyutils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String getMD5Code(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("utf-8"));
            byte[] encryption = md5.digest();
            String result = Base64.encodeToString(encryption, Base64.DEFAULT);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();


        }
        return "";

    }

    public static String md5ForFile(File file) {
        int buffersize = 1024;
        FileInputStream fis = null;
        DigestInputStream dis = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            dis = new DigestInputStream(fis, messageDigest);
            byte[] buffer = new byte[buffersize];
            while (dis.read(buffer) > 0) ;
            messageDigest = dis.getMessageDigest();
            byte[] array = messageDigest.digest();
            String result = Base64.encodeToString(array, Base64.DEFAULT);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
        return null;
    }
}
