package be.kdg.backend.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA 12.
 * User: Jonas Liekens
 * Date: 7/02/13
 * Time: 15:41
 * Copyright @ Soulware.be
 */
public class Utilities {

    public static String getEncryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();

        for(byte b : digest){
            sb.append(Integer.toHexString(b & 0xff));
        }

        return sb.toString();
    }

    public static Date makeDate(String date){
        try {
            //TODO: Use regex to support multiple input strings: dd-mm-yyyy or dd/mm/yyyy or dd.mm.yyyy
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String newPass(int length)
    {
        String characters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
