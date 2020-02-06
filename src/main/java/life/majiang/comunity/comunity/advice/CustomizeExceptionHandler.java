package life.majiang.comunity.comunity.advice;

import life.majiang.comunity.comunity.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    ModelAndView handleControllerException(HttpServletRequest request, Throwable e, Model model){
        HttpStatus status = getStatus(request);
        String message = "";
        if(status.is4xxClientError())
            message = "你这个请求错了吧，要不换个姿势";
        if(status.is5xxServerError())
            message = "服务器冒烟了，要不然你稍后试试";
        if (e instanceof CustomizeException)
            message = e.getMessage();
        model.addAttribute("message",message);
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
