package life.majiang.comunity.comunity.controller;
import life.majiang.comunity.comunity.dto.CommentDTO;
import life.majiang.comunity.comunity.dto.QuestionDto;
import life.majiang.comunity.comunity.enums.CommentTypeEnum;
import life.majiang.comunity.comunity.model.Question;
import life.majiang.comunity.comunity.service.CommentService;
import life.majiang.comunity.comunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired(required = false)
    QuestionService questionService;

    @Autowired(required = false)
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String profile(
            @PathVariable(name = "id") Long id,
            Model model
    ) {
        QuestionDto questionDto = questionService.getById(id);
        List<CommentDTO> comments = commentService.listById(id, CommentTypeEnum.QUESTION);
        List<Question> relaventQuestions = questionService.selectRelaventById(id);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relaventQuestions",relaventQuestions);
        return "question";
    }
}
