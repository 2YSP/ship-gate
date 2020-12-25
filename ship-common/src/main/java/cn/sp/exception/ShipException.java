package cn.sp.exception;

import cn.sp.constants.ShipExceptionEnum;

/**
 * Created by 2YSP on 2020/12/23
 */
public final class ShipException extends RuntimeException {

    private Integer code;

    private String errMsg;

    public ShipException(ShipExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.errMsg = exceptionEnum.getMsg();
    }

    public ShipException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public ShipException(Integer code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }


    public Integer getCode() {
        return code;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
