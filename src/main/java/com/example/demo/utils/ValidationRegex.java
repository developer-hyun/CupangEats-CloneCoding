package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    //핸드폰 넘버 정규식
    public static boolean isRegexPhone(String target) {
        String regex = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    //String regex = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";

    //비밀번호 정규식
    public static boolean isRegexPassowrd1(String target) {
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$";   //영문 숫자
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isRegexPassowrd2(String target) {
        String regex = "^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,20}$";  //영문 특수문자
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isRegexPassowrd3(String target) {
        String regex = "^(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,20}$";  //특수문자 숫자
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isRegexPassword4(String target) {
        String regex = "^(?=.*[^a-zA-Z0-9])(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}$";  // 특수문자 숫자 영문
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    //같은 문자 3개
    public static boolean isRegexPassword5(String target) {
        String regex = "/(.)\\1\\1\\1/";
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    } //(\w)\1\1\1
    //

}

