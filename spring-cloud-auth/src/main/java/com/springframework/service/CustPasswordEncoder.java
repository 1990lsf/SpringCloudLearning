package com.springframework.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 〈一句话描述〉
 * 〈功能详细描述〉
 *
 * @author edz
 * @version 2017/2/28
 * @see CustPasswordEncoder
 * @since 1.0
 */
public class CustPasswordEncoder implements PasswordEncoder {

    /*
     * (non-Javadoc)
     * @see org.springframework.security.crypto.password.PasswordEncoder#encode(java.lang.CharSequence)
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String str = new String(Base64.decodeBase64(rawPassword.toString().getBytes()));
        ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
        sha.setEncodeHashAsBase64(false);
        return sha.encodePassword(str, null);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.crypto.password.PasswordEncoder#matches(java.lang.CharSequence, java.lang.String)
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

//        String str = new String(Base64.decodeBase64(rawPassword.toString().getBytes()));
        String str= rawPassword.toString();
        ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
        sha.setEncodeHashAsBase64(false);
        String pwd= sha.encodePassword(str, null);
        return matches(pwd.getBytes(), encodedPassword.getBytes());
    }

    private boolean matches(byte[] expected, byte[] actual) {
        if (expected.length != actual.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < expected.length; i++) {
            result |= expected[i] ^ actual[i];
        }
        return result == 0;
    }

}