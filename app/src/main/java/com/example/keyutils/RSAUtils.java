package com.example.keyutils;

import android.util.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSAUtils {
    /**
     * 获取RSA公私钥匙对
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * 获取公钥
     */
    public static PublicKey getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        return publicKey;
    }

    /**
     * 获取私钥
     */
    public static PrivateKey getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        return privateKey;
    }


    /**
     * 公钥加密
     */

    public static String publicEncrypt(String content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipher.update(content.getBytes());
        byte[] bytes = cipher.doFinal();
        String result = Base64.encodeToString(bytes, Base64.DEFAULT);
        return result;
    }

    /**
     * 私钥解密
     */
    public static String privateDecrypt(String enContent, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] eContent = Base64.decode(enContent, Base64.DEFAULT);
        cipher.update(eContent);
        byte[] bytes = cipher.doFinal();
        return new String(bytes);
    }

}
