package life.majiang.comunity.comunity.controller;


import life.majiang.comunity.comunity.exception.CustomizeException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomizeErrorController{
    @GetMapping("/error")
    public ModelAndView errorHandler(HttpServletRequest req, Throwable e, Model model) {
        // Get status code to determine which view should be returned
        HttpStatus status = getStatus(req);
        String message = "";
        if(status.is4xxClientError())
            message = "你这个请求错了吧，要不换个姿势";
        if(status.is5xxServerError())
            message = "服务器冒烟了，要不然你稍后试试";
        model.addAttribute("message",message);
        // In this case, status code will be shown in a view
        ModelAndView mav = new ModelAndView("error");
        return mav;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
