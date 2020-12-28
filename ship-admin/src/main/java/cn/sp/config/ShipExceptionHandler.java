package cn.sp.config;

import cn.sp.exception.ShipException;
import cn.sp.pojo.vo.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Created by 2YSP on 2019/9/3.
 */
@RestControllerAdvice
public class ShipExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> handlerBusinessException(Exception exception) {
        return Result.error(transferToShipException(exception));
    }

    private ShipException transferToShipException(Exception exception) {
        ShipException shipException;
        if (exception instanceof ShipException) {
            shipException = (ShipException) exception;

        } else if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            BindingResult bindingResult = bindException.getBindingResult();
            shipException = new ShipException(getErrorMsg(bindingResult));

        } else if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) exception;
            BindingResult bindingResult = validException.getBindingResult();
            shipException = new ShipException(getErrorMsg(bindingResult));

        } else {
            shipException = new ShipException(exception.getMessage());
        }
        return shipException;
    }

    private String getErrorMsg(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        fieldErrors.forEach(fieldError -> {
            sb.append(fieldError.getDefaultMessage());
            sb.append("-");
        });
        return sb.toString();
    }


}
