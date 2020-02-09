package life.majiang.comunity.comunity.service;

import life.majiang.comunity.comunity.enums.CommentTypeEnum;
import life.majiang.comunity.comunity.exception.CustomizeResCode;
import life.majiang.comunity.comunity.exception.GetJsonException;
import life.majiang.comunity.comunity.mapper.CommentMapper;
import life.majiang.comunity.comunity.mapper.QuestionExMapper;
import life.majiang.comunity.comunity.mapper.QuestionMapper;
import life.majiang.comunity.comunity.model.Comment;
import life.majiang.comunity.comunity.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired(required = false)
    CommentMapper commentMapper;
    @Autowired(required = false)
    QuestionMapper questionMapper;
    @Autowired(required = false)
    QuestionExMapper questionExMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0)
            throw new GetJsonException(CustomizeResCode.TARGET_PARAM_NOT_FOUND);
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType()))
            throw new GetJsonException(CustomizeResCode.TYPE_PARAM_WRONG);
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null)
                throw new GetJsonException(CustomizeResCode.COMMENT_NOT_FOUND);
            commentMapper.insert(comment);
        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null)
                throw new GetJsonException(CustomizeResCode.QUESTION_NOT_FOUND);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExMapper.incCommentCount(question);
        }
    }
}
