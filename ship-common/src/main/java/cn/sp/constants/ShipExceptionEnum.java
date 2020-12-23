package cn.sp.constants;

/**
 * Created by 2YSP on 2020/12/23
 */
public enum ShipExceptionEnum {

    PARAM_ERROR(1000,"参数错误");

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
