package com.springframework.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;

public class HmacEncodeStr {

    @SuppressWarnings("restriction")
    public static String hmacEncodeStr(String macKey, String macData, String account)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Mac mac = Mac.getInstance("HMACSHA256");
        byte[] secretByte = macKey.getBytes("UTF-8");
        byte[] dataBytes = macData.getBytes("UTF-8");
        SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
        BASE64Encoder be64 = new BASE64Encoder();
        mac.init(secret);
        byte[] doFinal = mac.doFinal(dataBytes);
        String signature = be64.encode(doFinal);
        return "hmac username=\"" + account
                + "\", algorithm=\"hmac-sha256\", headers=\"X-Date Content-Md5\", signature=\"" + signature + "\"";

    }

    public static String getMd5Value(String paramString) {
        if (paramString == null)
            return "";
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            StringBuffer localStringBuffer = new StringBuffer();
            byte[] arrayOfByte = localMessageDigest.digest();
            for (int i = 0;; i++) {
                if (i >= arrayOfByte.length)
                    return localStringBuffer.toString();
                int j = arrayOfByte[i];
                if (j < 0)
                    j += 256;
                if (j < 16)
                    localStringBuffer.append("0");
                localStringBuffer.append(Integer.toHexString(j));
            }
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
            localNoSuchAlgorithmException.printStackTrace();
        }
        return "";
    }

}
