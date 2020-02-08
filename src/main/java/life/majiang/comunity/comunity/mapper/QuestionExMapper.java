package life.majiang.comunity.comunity.mapper;

import life.majiang.comunity.comunity.model.Question;

public interface QuestionExMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}