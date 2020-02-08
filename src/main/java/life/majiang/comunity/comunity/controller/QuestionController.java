package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.dto.QuestionDto;
import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired(required = false)
    QuestionService questionService;

    @Autowired(required = false)
    UserMapper userMapper;

    @GetMapping("/question/{id}")
    public String profile(
            @PathVariable(name = "id")Long id,
            Model model
    ){
        QuestionDto questionDto = questionService.getById(id);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        return "question";
    }
}
