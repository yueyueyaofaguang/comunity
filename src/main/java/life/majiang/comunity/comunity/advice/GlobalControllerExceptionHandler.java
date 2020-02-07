package life.majiang.comunity.comunity.advice;

import life.majiang.comunity.comunity.dto.ResultDTO;
import life.majiang.comunity.comunity.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    void handle(Throwable e, Model model,HttpServletRequest req){
        String contentType = req.getContentType();
        if("application/json".equals(contentType)){
        }else{
            //错误页面跳转
//            if(e instanceof CustomizeException){
//                model.addAttribute("message",e.getMessage());
//            }else{
//                model.addAttribute("message","服务器冒烟了,要不然你稍后再试");
//            }
//            return new ModelAndView("error");
        }
    }

}
