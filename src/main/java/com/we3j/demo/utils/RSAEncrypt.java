package com.we3j.demo.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 * RSA加密类
 */
public class RSAEncrypt {

    public static void main(String[] args) {
        try {
            RSAEncrypt encrypt = new RSAEncrypt();
            String encryptText = "123456mPU5amu6Zr";
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();

            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 公钥
            System.out.println("¥¥¥¥¥¥¥¥"+publicKey);
            System.out.println("¥¥¥¥¥¥¥¥"+publicKey);
            byte[] e = encrypt.encrypt(publicKey, encryptText.getBytes());
            byte[] de = encrypt.decrypt(privateKey, e);
            System.out.println("¥¥¥¥¥¥¥¥  "+encrypt.bytesToString(e));

            System.out.println("¥¥¥¥¥¥¥¥  "+encrypt.bytesToString(de));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * byte数组转为string
     *
     * @return
     */
    protected String bytesToString(byte[] bytes) {
        String result = "";
        result = Base64.getEncoder().encodeToString(bytes);
        return result;
    }

    /**
     * 加密方法
     *
     * @param publicKey
     * @param obj
     * @return
     */
    protected byte[] encrypt(RSAPublicKey publicKey, byte[] obj) {
        if (publicKey != null) {
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                return cipher.doFinal(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解密方法
     *
     * @param privateKey
     * @param obj
     * @return
     */
    protected byte[] decrypt(RSAPrivateKey privateKey, byte[] obj) {
        if (privateKey != null) {
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return cipher.doFinal(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}