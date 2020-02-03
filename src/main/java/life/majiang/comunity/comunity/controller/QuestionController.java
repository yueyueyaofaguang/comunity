package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.dto.QuestionDto;
import life.majiang.comunity.comunity.mapper.QuestionMapper;
import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired(required = false)
    QuestionMapper questionMapper;

    @Autowired(required = false)
    UserMapper userMapper;

    @GetMapping("/question/{action}")
    public String profile(
            @PathVariable(name = "action")String action,
            Model model
    ){
        Question question = questionMapper.getQuestionById(Integer.parseInt(action));
        QuestionDto questionDto =  new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUser(userMapper.findById(question.getCreator()));
        model.addAttribute("questionInfo",questionDto);
        return "question";
    }
}
