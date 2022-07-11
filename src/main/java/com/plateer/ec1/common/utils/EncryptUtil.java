package com.plateer.ec1.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Slf4j
public class EncryptUtil {

    public static String encryptSha521(String input){
        String encryptedStr = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes(StandardCharsets.UTF_8));
            encryptedStr = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return encryptedStr;
    }
}
