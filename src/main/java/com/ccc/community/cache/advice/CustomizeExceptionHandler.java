package com.ccc.community.cache.advice;

import com.alibaba.fastjson.JSON;
import com.ccc.community.dto.ResultDTO;
import com.ccc.community.exception.CustomizeException;
import com.ccc.community.exception.impl.CustomizeErrorCodeImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program:
 * @description:
 * @author: RuYi-Chen
 * @create: 2019 07 08 10:27
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleControllerException(Throwable ex, Model model , HttpServletRequest request , HttpServletResponse response) {
        String contentType = request.getContentType();
        ResultDTO resultDTO;
        if (contentType.equals("application/json")) {
            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorof((CustomizeException) ex);
            } else {
                resultDTO = ResultDTO.errorof(CustomizeErrorCodeImpl.SYS_ERROR);
            }
            try {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(200);
                response.getWriter().write(JSON.toJSONString(resultDTO));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCodeImpl.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
