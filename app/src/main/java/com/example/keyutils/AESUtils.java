package com.example.keyutils;

import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    /**
     * AES加密
     *
     * @param seed      密钥
     * @param cleartext 明文
     * @return 密文
     */
    private static final String AES = "AES";
    private static final String SHA1PRNG = "SHA1PRNG";
    private static final String TAG = "XY";

    public static String encrypt(String seed, String cleartext) {
        //对密钥进行加密
        byte[] rawkey = getRawKey(seed.getBytes());
        //加密数据
        byte[] result = encrypt(rawkey, cleartext.getBytes());
        String enresult = Base64.encodeToString(result, Base64.DEFAULT);
        return enresult;
    }

    /**
     * AES解密
     *
     * @param seed      密钥
     * @param encrypted 密文
     * @return 明文
     */
    public static String decrypt(String seed, String encrypted) {
        byte[] rawkay = getRawKey(seed.getBytes());
        byte[] enContent = Base64.decode(encrypted, Base64.DEFAULT);
        byte[] result = decrypt(rawkay, enContent);
        Log.e(TAG, "decrypt: result" + result);
        return new String(result);
    }

    private static byte[] getRawKey(byte[] seed) {
        try {
            //获取密钥生成器
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom sr = null;
            if (Build.VERSION.SDK_INT > 23) {
                sr = SecureRandom.getInstance(SHA1PRNG, new CryptoProvider());
            } else {
                sr = SecureRandom.getInstance(SHA1PRNG);
            }
            sr.setSeed(seed);
            //生成位的AES密码生成器
            kgen.init(128, sr);
            //生成密码
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            return raw;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        try {
            Cipher cipher = Cipher.getInstance(AES);
            //使用ENCRYPT_MODE模式，用skeySpec密码组，生成AES加密方法
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            //得到加密数据
            byte[] encrypted = cipher.doFinal(clear);
            return encrypted;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) {
        //生成一系列扩展密钥，并放入一个数组中
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);

        try {
            Cipher cipher = Cipher.getInstance(AES);
            //使用DECRYPT_MODE模式，用skeySpec密码组，生成AES解密方法
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //得到解密数据
            byte[] decrypted = cipher.doFinal(encrypted);
            return decrypted;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 增加  CryptoProvider  类

    public static class CryptoProvider extends Provider {
        /**
         * Creates a Provider and puts parameters
         */
        public CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}
