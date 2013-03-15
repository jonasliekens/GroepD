package be.kdg.web.security;

import be.kdg.backend.utilities.Utilities;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * User: Bart Verhavert
 * Date: 14/03/13 12:47
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    //TODO: Write the stacktrace to the logfiles with log4j and maybe improve the catches with a suited error to the user?
    @Override
    public String encodePassword(String password, Object o) {
//        try {
//            return Utilities.getEncryptPassword(password);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        return null;

        return password;
    }

    @Override
    public boolean isPasswordValid(String encryptedPassword, String password, Object o) {
//        try {
//            return encryptedPassword.equals(Utilities.getEncryptPassword(password));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        return false;

        return encryptedPassword.equals(password);
    }
}
