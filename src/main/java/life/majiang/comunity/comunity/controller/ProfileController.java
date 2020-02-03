package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.dto.PageDto;
import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.User;
import life.majiang.comunity.comunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@CookieValue(value = "token", required = false) String token,
                          @PathVariable(name = "action")String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "2") Integer size,
                          HttpServletRequest request,
                          Model model){
        if (token != null) {
            User user = userMapper.findByToken(token);
            if (user != null) request.getSession().setAttribute("user", user);
            if("questions".equals(action)){
                model.addAttribute("section","questions");
                model.addAttribute("sectionName","我的提问");
            }else if("replies".equals(action)){
                model.addAttribute("section","replies");
                model.addAttribute("sectionName","我的回复");
            }
            PageDto pageDto = questionService.list(user.getId(), page, size);
            model.addAttribute("pageInfo",pageDto);
            return "profile";
        }

        return "redirect:";
    }
}
