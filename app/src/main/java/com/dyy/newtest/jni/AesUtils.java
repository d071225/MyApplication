package com.dyy.newtest.jni;

import android.util.Base64;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.utils.CryptoProvider;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by DY on 2018/4/2.
 */

public class AesUtils {
    // /** 算法/模式/填充 **/
    private static final String CipherMode = "AES/ECB/PKCS5Padding";
    // private static final String CipherMode = "AES";
    static {
        System.loadLibrary("native-lib");
    }
    public native static String getAesEncContent(String content);
    public native static String getAesDecContent(String keyContent);
    /**
     * 生成一个AES密钥对象
     * @return
     */
    public static SecretKeySpec generateKey(String seed){
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom sha1PRNG =null;
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                LogUtils.e("---------------大于4.2版本---------------");
    //            sr = SecureRandom.getInstance(SHA1PRNG, "Crypto");
                sha1PRNG = SecureRandom.getInstance("SHA1PRNG", new CryptoProvider());
            } else {
                LogUtils.e("---------------小于4.2版本---------------");
                sha1PRNG = SecureRandom.getInstance("SHA1PRNG");
            }
//            SecureRandom sha1PRNG = SecureRandom.getInstance("SHA1PRNG");
//            kgen.init(128, new SecureRandom());
            sha1PRNG.setSeed(seed.getBytes());
            kgen.init(128, sha1PRNG);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成一个AES密钥字符串
     * @return
     */
    public static String generateKeyString(String seed){
        return byte2hex(generateKey(seed).getEncoded());
    }

    /**
     * 加密字节数据
     * @param content
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] content,byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过byte[]类型的密钥加密String
     * @param content
     * @param key
     * @return 16进制密文字符串
     */
    public static String encrypt(String content,byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] data = cipher.doFinal(content.getBytes("UTF-8"));
            LogUtils.e(Base64.encodeToString(data,Base64.DEFAULT));
            String result = byte2hex(data);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过String类型的密钥加密String
     * @param content
     * @param key
     * @return 16进制密文字符串
     */
    public static String encrypt(String content,String key) {
        byte[] data = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = encrypt(data,new SecretKeySpec(hex2byte(key), "AES").getEncoded());
        String result = byte2hex(data);
        return result;
    }

    /**
     * 通过byte[]类型的密钥解密byte[]
     * @param content
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] content,byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过String类型的密钥 解密String类型的密文
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        byte[] data = null;
        try {
            data = hex2byte(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt(data, hex2byte(key));
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过byte[]类型的密钥 解密String类型的密文
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content,byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key, "AES"));
            byte[] data = cipher.doFinal(hex2byte(content));
            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转成16进制字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) { // 一个字节的数，
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp = "";
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            tmp = (Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 将hex字符串转换成字节数组
     * @param inputString
     * @return
     */
    public static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

    /*
     * 解密
     */
    public static String decrypt6(byte[] encrypted, String key) {
        SecretKeySpec skeySpec = new SecretKeySpec(hex2byte(key), "AES");
        Cipher cipher = null;
        byte[] decrypted=null;
        try {
            cipher = Cipher.getInstance(CipherMode);
//        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decrypted = cipher.doFinal(encrypted);
            return new String(decrypted,"utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
