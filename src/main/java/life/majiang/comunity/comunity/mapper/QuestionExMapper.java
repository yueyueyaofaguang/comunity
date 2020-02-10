package life.majiang.comunity.comunity.mapper;

import life.majiang.comunity.comunity.model.Question;

import java.util.List;

public interface QuestionExMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelavent(String keywork);
}