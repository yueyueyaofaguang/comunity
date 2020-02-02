package life.majiang.comunity.comunity.service;

import life.majiang.comunity.comunity.dto.PageDto;
import life.majiang.comunity.comunity.dto.QuestionDto;
import life.majiang.comunity.comunity.mapper.QuestionMapper;
import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.Question;
import life.majiang.comunity.comunity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private PageDto pageDto = new PageDto();

    public List<QuestionDto> list(Integer page, Integer size) {
        List<Question> questions = questionMapper.list(size,(page-1)*size);
        List<Question> questionList = questionMapper.listAll();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        pageDto.setCount(Math.round(questionList.size()/size));
        pageDto.setCurrentPage(page);
        pageDto.setHasNext(page<pageDto.getCount());
        for (Question q : questions) {
            User user = userMapper.findById(q.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }

    public PageDto getPageDto() {
        return pageDto;
    }

}
