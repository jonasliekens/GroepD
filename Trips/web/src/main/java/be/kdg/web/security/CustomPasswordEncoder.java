package be.kdg.web.security;

import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * User: Bart Verhavert
 * Date: 14/03/13 12:47
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encodePassword(String s, Object o) {
        return s;
    }

    @Override
    public boolean isPasswordValid(String encrypted, String raw, Object o) {
        return encrypted.equals(raw);
    }
}
