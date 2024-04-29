package com.haishi.admin.common.exception;

import com.haishi.admin.common.dto.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = BizException.class)
    public HttpResult handleBizException(BizException e) {
        log.error(e.getMsg());
        return HttpResult.error(e);
    }

    @ExceptionHandler(value = Exception.class)
    public HttpResult handleException(Exception e) {
        e.printStackTrace();
        return HttpResult.error(new BizException());
    }


}
