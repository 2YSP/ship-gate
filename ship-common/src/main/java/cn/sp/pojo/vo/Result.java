package cn.sp.pojo.vo;

import cn.sp.exception.ShipException;

import java.io.Serializable;

/**
 * Created by 2YSP on 2019/8/28.
 */
public class Result<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    private Result() {
    }

    /**
     * return the success result
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    /**
     * return the success result without data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    /**
     * return the fail result
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> fail() {
        Result<T> result = new Result();
        result.setCode(500);
        result.setMessage("fail");
        return result;
    }


    public static <T> Result<T> error(ShipException shipException) {
        Result<T> result = new Result();
        result.setCode(shipException.getCode());
        result.setMessage(shipException.getMessage());
        return result;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
