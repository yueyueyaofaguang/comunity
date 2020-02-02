package life.majiang.comunity.comunity.service;

import life.majiang.comunity.comunity.dto.PageDto;
import life.majiang.comunity.comunity.dto.QuestionDto;
import life.majiang.comunity.comunity.mapper.QuestionMapper;
import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.Question;
import life.majiang.comunity.comunity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//为什么要分离出service层？
@Service
public class QuestionService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    public PageDto list(Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        Integer totalCount = questionMapper.count();
        pageDto.setPagination(totalCount, page, size);
        if (page < 1) {
            page = 1;
        }
        if (page > pageDto.getTotalPage()) {
            page = pageDto.getTotalPage();
        }

        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question q : questions) {
            User user = userMapper.findById(q.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);
        return pageDto;
    }
}
