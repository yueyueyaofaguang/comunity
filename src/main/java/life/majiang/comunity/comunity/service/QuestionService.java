package life.majiang.comunity.comunity.service;

import life.majiang.comunity.comunity.dto.PageDto;
import life.majiang.comunity.comunity.dto.QuestionDto;
import life.majiang.comunity.comunity.exception.CustomizeResCode;
import life.majiang.comunity.comunity.exception.GetJsonException;
import life.majiang.comunity.comunity.exception.GetPageException;
import life.majiang.comunity.comunity.mapper.QuestionExMapper;
import life.majiang.comunity.comunity.mapper.QuestionMapper;
import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.Question;
import life.majiang.comunity.comunity.model.QuestionExample;
import life.majiang.comunity.comunity.model.User;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired(required = false)
    private QuestionExMapper questionExMapper;

    public PageDto list(Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        QuestionExample questionExample = new QuestionExample();
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        pageDto.setPagination(totalCount, page, size);
        if(totalCount == 0)
            return pageDto;
        if (page < 1) {
            page = 1;
        }
        if (page > pageDto.getTotalPage()) {
            page = pageDto.getTotalPage();
        }

        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question q : questions) {
            User user = userMapper.selectByPrimaryKey(q.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);
        return pageDto;
    }

    public PageDto list(Long id, Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                        .andIdEqualTo(id);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        pageDto.setPagination(totalCount, page, size);
        if(totalCount == 0)
            return pageDto;
        if (page < 1) {
            page = 1;
        }
        if (page > pageDto.getTotalPage()) {
            page = pageDto.getTotalPage();
        }

        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset,size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question q : questions) {
            User user = userMapper.selectByPrimaryKey(q.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);
        return pageDto;
    }

    public QuestionDto getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new GetPageException(CustomizeResCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return  questionDto;
    }

    public void createOrUpdate(Question question) {
        Question target = questionMapper.selectByPrimaryKey(question.getId());
        if(target == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insertSelective(question);
        }else{
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated!=1){
                throw new GetPageException(CustomizeResCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExMapper.incView(question);
    }
}
