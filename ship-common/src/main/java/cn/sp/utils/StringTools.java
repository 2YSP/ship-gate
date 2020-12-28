package cn.sp.utils;

import cn.sp.constants.MatchMethodEnum;
import cn.sp.exception.ShipException;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/28
 */
public class StringTools {

    public static final String CHARSET_UTF8 = "UTF-8";

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
            // todo 将 Pattern 缓存下来，避免反复编译Pattern
            return Pattern.matches(matchRule, value);
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
