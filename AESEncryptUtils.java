package com.chain.goodluck.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AESEncryptUtils {

    /**
     */
    private static final String AES_PKCS5P = "AES/ECB/PKCS5Padding";

    /**
     *
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String key) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key is null");
        }
        try {
            if (str == null) {
                return null;
            }
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     *
     * @param str
     * @param key
     * @return
     */
    public static String decrypt(String str, String key) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key is null");
        }
        try {
            if (str == null) {
                return null;
            }
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted = Base64.decodeBase64(str);
            try {
                byte[] original = cipher.doFinal(encrypted);
                String originalString = new String(original, StandardCharsets.UTF_8);
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
