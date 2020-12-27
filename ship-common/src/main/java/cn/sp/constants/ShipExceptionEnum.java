package cn.sp.constants;

/**
 * Created by 2YSP on 2020/12/23
 */
public enum ShipExceptionEnum {
    /**
     * param error
     */
    PARAM_ERROR(1000,"param error"),
    /**
     * service not find
     */
    SERVICE_NOT_FIND(1001,"service not find,maybe not register");

    private Integer code;

    private String msg;

    ShipExceptionEnum(Integer code,String msg){
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
