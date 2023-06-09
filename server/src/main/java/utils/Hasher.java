package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    String alg;

    public Hasher(String alg){
        this.alg = alg;
    }
    public String encode(String str){
        try {
            MessageDigest digest = MessageDigest.getInstance(alg);
            byte[] hash = digest.digest(str.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b: hash){
                result.append(String.format("%02x",b));
            }
            return result.toString();
        }catch (NoSuchAlgorithmException n){
            System.out.println(n.getMessage());
        }
        return null;
    }
}
