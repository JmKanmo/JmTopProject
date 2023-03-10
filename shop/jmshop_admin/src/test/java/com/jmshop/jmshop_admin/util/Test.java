package com.jmshop.jmshop_admin.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class Test {
    public static void main(String[] args) throws IOException {
        String src = new String(new FileInputStream("/Users/junmokang/Downloads/main.bundle.js.map.gz").readAllBytes());

        String str = encrypt(src.getBytes(), "abcdefghijklmnop".getBytes());

        String ret = decrypt(str.getBytes(), "abcdefgijklmnop".getBytes());
        System.out.println(src.equals(ret));
    }


    public static String encrypt(byte[] target, byte[] key) {
        SecretKeySpec keySpec = null;

        keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (NoSuchPaddingException e) {
            return null;
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        } catch (InvalidKeyException e) {
            return null;
        }

        try {
            Encoder encoder = Base64.getEncoder();
            return new String(encoder.encode(cipher.doFinal(target)));
        } catch (IllegalBlockSizeException e) {

        } catch (BadPaddingException e) {

        }
        return null;
    }

    public static String decrypt(byte[] target, byte[] key) {
        SecretKeySpec keySpec = null;

        keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {

            return null;
        } catch (NoSuchPaddingException e) {
            return null;
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        } catch (InvalidKeyException e) {

            return null;
        }

        try {
            Decoder encoder = Base64.getDecoder();
            return new String(cipher.doFinal(encoder.decode(target)));
        } catch (IllegalBlockSizeException e) {
            System.out.println("err");
        } catch (BadPaddingException e) {

        }
        return null;
    }
}