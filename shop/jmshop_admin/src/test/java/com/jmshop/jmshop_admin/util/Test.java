package com.jmshop.jmshop_admin.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class Test {
    public static void main(String[] args) throws IOException {
        String src = new String(new FileInputStream("/Users/junmokang/whatap/study/dev.txt").readAllBytes());
        String[] parsed = src.split("data")[1].split("projects")[1].split("pcode");
        List<Integer> pcodeList = new ArrayList<>();

        for (int i = 1; i < parsed.length; i++) {
            pcodeList.add(Integer.parseInt(parsed[i].split("\"\":")[0].split(",")[0].split(":")[1]));
        }
        // src.split("data")[1].split("projects")[1].split("pcode")[3].split("\"\":")[0].split(",")[0].split(":")[1]

        System.out.println(src);
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