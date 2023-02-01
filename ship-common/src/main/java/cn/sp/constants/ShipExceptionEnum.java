package cn.sp.constants;

/**
 * Created by 2YSP on 2020/12/23
 */
public enum ShipExceptionEnum {
    /**
     * param error
     */
    PARAM_ERROR(1000, "param error"),
    /**
     * service not find
     */
    SERVICE_NOT_FIND(1001, "service not find,maybe not register"),
    /**
     * invalid config
     */
    CONFIG_ERROR(1002, "invalid config"),
    /**
     * userName or password error
     */
    LOGIN_ERROR(1003, "userName or password error"),
    /**
     * not login
     */
    NOT_LOGIN(1004, "not login"),
    /**
     * token error
     */
    TOKEN_ERROR(1005, "token error"),
    /**
     * connect nacos error
     */
    CONNECT_NACOS_ERROR(1006, "connect nacos error");

    private Integer code;

    private String msg;

    ShipExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
