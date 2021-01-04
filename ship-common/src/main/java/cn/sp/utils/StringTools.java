package cn.sp.utils;

import cn.sp.constants.MatchMethodEnum;
import cn.sp.exception.ShipException;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public class StringTools {

    public static final String CHARSET_UTF8 = "UTF-8";
    /**
     * key: regex
     */
    private static final Map<String, Pattern> PATTERN_MAP = new HashMap<>();

    /**
     * @param value
     * @param matchMethod {@link MatchMethodEnum}
     * @param matchRule
     * @return
     */
    public static boolean match(String value, Byte matchMethod, String matchRule) {
        if (MatchMethodEnum.EQUAL.getCode().equals(matchMethod)) {
            return value.equals(matchRule);
        } else if (MatchMethodEnum.REGEX.getCode().equals(matchMethod)) {
            // 将 Pattern 缓存下来，避免反复编译Pattern
            Pattern p = PATTERN_MAP.computeIfAbsent(matchRule, k -> Pattern.compile(k));
            Matcher m = p.matcher(value);
            return m.matches();
        } else if (MatchMethodEnum.LIKE.getCode().equals(matchMethod)) {
            return value.indexOf(matchRule) != -1;
        } else {
            throw new ShipException("invalid matchMethod");
        }
    }

    public static String byteToStr(byte[] data) {
        try {
            return new String(data, CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, CHARSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5Digest(String value, String salt) {
        String plainText = value + salt;
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

}
