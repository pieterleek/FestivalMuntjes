package io.qman.festivalcoins.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * This class is used for generating secure hashes.
 * It uses the SHA-512 algorithm for hashing, with a fallback to SHA-256.
 */
public class SecureHasher {

    /**
     * The secret salt used for hashing.
     * In a real-world scenario, this would be loaded from a configuration file.
     */
    private static final String SECRET_SALT = "abc123!@#";

    /**
     * The MessageDigest used for hashing.
     * It is initialized with the SHA-512 algorithm and the secret salt.
     */
    private static final MessageDigest SALT_MESSAGE_DIGEST = getMessageDigest("SHA-512");

    /**
     * This method returns a MessageDigest instance for the given algorithm.
     * If the algorithm is not supported, it falls back to SHA-256.
     * @param algorithm the hashing algorithm
     * @return a MessageDigest instance
     */
    private static MessageDigest getMessageDigest(String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(String.format("%-16s", SECRET_SALT).getBytes(StandardCharsets.UTF_8));
            md.reset();
            return md;
        } catch (Exception ex) {
            if (!algorithm.equals("SHA-256"))
                return getMessageDigest("SHA-256");
        }
        return null;
    }

    /**
     * This method generates a secure hash for the given source string.
     * It uses the MessageDigest initialized with the secret salt.
     * @param source the source string
     * @return the secure hash, or null if the source is null or the MessageDigest could not be initialized
     */
    public static String secureHash(String source) {
        if (SALT_MESSAGE_DIGEST == null || source == null) return null;
        byte[] hashedResult = SALT_MESSAGE_DIGEST.digest(source.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedResult);
    }
}