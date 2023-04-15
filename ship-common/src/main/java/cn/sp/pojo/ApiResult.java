package cn.sp.pojo;

/**
 * @Author: Ship
 * @Description:
 * @Date: Created in 2023/4/15
 */
public class ApiResult {

    private Integer code;

    private String message;

    public ApiResult() {
    }

    public ApiResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
