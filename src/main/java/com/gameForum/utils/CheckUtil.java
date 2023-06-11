package com.gameForum.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

    public static Boolean mailCheck(String mail){
        if (mail == null || mail.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }
    public static Boolean usernameCheck(String username){
        if (username == null || username.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("^([0-9]|[a-z]|[A-Z]){5,11}$");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static Boolean passwordCheck(String password){
        if (password == null || password.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("^([0-9]|[a-z]|[A-Z]|[~!@#$%^&*_.]){6,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public static boolean phoneCheck(String phone){
        if (phone == null || phone.trim().equals("")) {
            return false;
        }
        if (phone.length() != 11) {
            return false;
        }
        Pattern pattern = Pattern.compile("^1[3-9]\\d{9}$");
        Matcher matcher = pattern.matcher(phone);

        return matcher.matches();
    }
}
