package cn.sp.constants;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2020/12/25
 */
public enum MatchMethodEnum {
    /**
     * =
     */
    EQUAL(1, "="),
    /**
     * regex
     */
    REGEX(2, "regex"),
    /**
     * like
     */
    LIKE(3, "like");


    private Integer code;

    private String desc;

    MatchMethodEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
