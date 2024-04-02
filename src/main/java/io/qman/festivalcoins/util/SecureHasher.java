package io.qman.festivalcoins.util;

import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class SecureHasher {

    @Value("${secretSalt}")
    private static String secretSalt;
    private static MessageDigest saltedMessageDigest = getMessageDigest("SHA-512");

    private static MessageDigest getMessageDigest(String algorithm) {
        //System.out.println("GetMD-" + algorithm);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
            // apply a secret salt to the hasher
            md.update(String.format("%-16s", secretSalt).getBytes(StandardCharsets.UTF_8));
            // for more security, add more salt, maybe different salt...

            // reset the md before its first use
            md.reset();
            return md;
        } catch (Exception ex) {
            // try to fallback on SHA-256
            if (!algorithm.equals("SHA-256"))
                return getMessageDigest("SHA-256");
        }
        // nothing found
        return null;
    }

    /**
     * Calculate a secure hash from a soource for the purpose of password hashing.
     *
     * @param source
     * @return
     */
    public static String secureHash(String source) {
        if (saltedMessageDigest == null || source == null) return null;
        byte[] hashedResult = saltedMessageDigest.digest(source.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedResult);
    }
}
