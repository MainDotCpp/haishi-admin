package com.haishi.admin.common.exception;

import com.haishi.admin.common.dto.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public HttpResult<Object> handleBizException(BizException e) {
        log.error(e.getMsg());
        return HttpResult.error(e);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public HttpResult<Object> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage());
        return HttpResult.error(new BizException(BizExceptionEnum.PERMISSION_DENIED));
    }


    @ExceptionHandler(value = NoResourceFoundException.class)
    public Object handleNoResourceException(NoResourceFoundException e) {
        log.error(e.getMessage());
        return "404";
    }




    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public HttpResult<Object> handleException(Exception e) {
        e.printStackTrace();
        return HttpResult.error(new BizException());
    }


}
