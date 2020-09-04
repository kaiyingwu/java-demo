package com.example.demo.config;

import com.example.demo.bean.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.LinkedList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数校验异常处理
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public BaseResponse handleValidationException(HttpServletRequest req, Exception e) {
        String message = "";
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            message = methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage();
        }else if (e instanceof ConstraintViolationException){
            List<String> errors = new LinkedList<>();
            ConstraintViolationException violationException = (ConstraintViolationException) e;
            for (ConstraintViolation<?> s : violationException.getConstraintViolations()) {
                errors.add(s.getMessage());
            }
            message = errors.get(0);
        }
        return new BaseResponse(message, 10002);
    }

}
