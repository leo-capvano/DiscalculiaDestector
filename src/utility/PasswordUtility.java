/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 * 
 * An utility class used to encrypt password and generate salt
 */
public class PasswordUtility {
    
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    
    private static final SecureRandom RAND = new SecureRandom();
    
    public static Optional<String> generateSalt(final int length) {
        
        if(length < 0)
            throw new IllegalArgumentException("Error in generateSalt: length must be > 0");
        
        byte[] salt = new byte[length];
        
        RAND.nextBytes(salt);
        
        return Optional.of(Base64.getEncoder().encodeToString(salt));
    }
    
    public static Optional<String> hashPassword(String password, String salt) {
        
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        
        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, KEY_LENGTH);
        
        //Clear array content for security reason
        Arrays.fill(passwordChars, Character.MIN_VALUE);
        
        try {
            //Opaque representation of the cryptographic key (contains only the hashed password and no other identifying information)
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            //Getting hashed password as byte array
            byte[] securePassword = fac.generateSecret(spec).getEncoded();
            //Encrypt the secure password in base 64 and then return it as a String
            return Optional.of(Base64.getEncoder().encodeToString(securePassword));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.err.println("Exception encountered in hashPassword()");
            return Optional.empty();
        } finally {
            spec.clearPassword();
        }
    }
    
    public static boolean verifyPassword (String password, String encryptedPassword, String salt) {
        Optional<String> optEncrypted = hashPassword(password, salt);
        
        if (!optEncrypted.isPresent()) 
            return false;
        
        return optEncrypted.get().equals(encryptedPassword);
    }
}
