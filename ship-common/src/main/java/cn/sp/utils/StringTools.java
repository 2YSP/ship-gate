package cn.sp.utils;

import cn.sp.constants.MatchMethodEnum;
import cn.sp.exception.ShipException;

import java.io.UnsupportedEncodingException;
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

}
