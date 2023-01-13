package com.example.nermaxcooking1;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SHA512 {

    public static String getHash(String password, String salt) {
        MessageDigest md;
        byte[] hashedPassword = null;

        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return hashedPassword.toString();
    }

    public static String getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt.toString();
    }

}
