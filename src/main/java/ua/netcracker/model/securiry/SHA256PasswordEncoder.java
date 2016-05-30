package ua.netcracker.model.securiry;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;

/**
 * Created on 4:32 30.05.2016
 *
 * @author Bersik (Serhii Kisilchuk)
 */
public class SHA256PasswordEncoder implements PasswordEncoder {
    private static final Logger LOGGER = Logger.getLogger(SHA256PasswordEncoder.class);

    public String encode(CharSequence rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(rawPassword.toString().getBytes("UTF-8"));
            byte[] digest = md.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
