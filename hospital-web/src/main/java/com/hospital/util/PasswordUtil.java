package com.hospital.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean check(String password, String hashed) {
        if (password == null || hashed == null) {
            return false;
        }
        return BCrypt.checkpw(password, hashed);
    }
}
