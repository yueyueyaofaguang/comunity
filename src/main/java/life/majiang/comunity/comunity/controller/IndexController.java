package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.dto.PageDto;
import life.majiang.comunity.comunity.dto.QuestionDto;
import life.majiang.comunity.comunity.mapper.QuestionMapper;
import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.Question;
import life.majiang.comunity.comunity.model.User;
import life.majiang.comunity.comunity.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "2") Integer size,
                        @RequestParam(name = "search",required = false)String search,
                        HttpServletRequest request,
                        Model model
    ) {
        //1.判断cookies中是否含有token
        //2.利用token去查询数据库中是否有相同的用户
        //3.选择性展示信息
        PageDto pageDto =   questionService.list(search,page,size);
        model.addAttribute("pageInfo",pageDto);
        if(!StringUtils.isBlank("search")){
            model.addAttribute("search",search);
        }
        return "index";
    }

}
