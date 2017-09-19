package com.baipengx.coin.config.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baipengx.coin.controller.BaseController;
import com.baipengx.coin.vo.ResponseVo;

@ControllerAdvice("com.baipengx.coin.controller")
@Scope("prototype")
public class ApiGlobalExceptionHandler extends BaseController{
    /**
     * 系统异常处理，比如：404,500
     * @param req
     * @param resp
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVo defaultErrorHandler(HttpServletRequest req, Exception e, HttpServletResponse response) throws Exception {
        log.error(e.getMessage(), e);
        vo.setErrmsg(e.getMessage());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
             vo.setErrcode(404);
             response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
             vo.setErrcode(500);
//             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        vo.setData(null);
        return vo;
    }
}
