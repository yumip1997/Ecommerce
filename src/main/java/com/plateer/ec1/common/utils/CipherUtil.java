package com.plateer.ec1.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Slf4j
public class CipherUtil {

    private static final String ALGORITHM_SHA_512 = "SHA-512";
    private static final String ALGORITHM_AES_DBD_PKCS5 = "AES/CBC/PKCS5Padding";

    public static String encryptBySHA512(String input){
        String encryptedStr = null;

        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA_512);
            digest.reset();
            digest.update(input.getBytes(StandardCharsets.UTF_8));
            encryptedStr = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return encryptedStr;
    }

    public static String encryptByAES(String input, String key, String iv){
        String encryptedStr = null;

        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES_DBD_PKCS5);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            encryptedStr = Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return encryptedStr;
    }
}
