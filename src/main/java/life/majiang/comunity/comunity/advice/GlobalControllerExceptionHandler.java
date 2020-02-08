package life.majiang.comunity.comunity.advice;
import life.majiang.comunity.comunity.dto.ResultDTO;
import life.majiang.comunity.comunity.exception.GetJsonException;
import life.majiang.comunity.comunity.exception.GetPageException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(GetJsonException.class)
    @ResponseBody
    public ResultDTO customerExceptionHandler(HttpServletRequest rep, GetJsonException e){
        return ResultDTO.errorOf(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(GetPageException.class)
    public ModelAndView exceptionHandler(Model model,Throwable e){
        model.addAttribute("message",e.getMessage());
        return new ModelAndView("error");
    }
}
