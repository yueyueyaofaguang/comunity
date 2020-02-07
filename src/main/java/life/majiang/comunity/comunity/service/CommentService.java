package life.majiang.comunity.comunity.service;

import life.majiang.comunity.comunity.exception.CustomizeErrorCode;
import life.majiang.comunity.comunity.exception.CustomizeException;
import life.majiang.comunity.comunity.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public void insert(Comment comment){
        if(comment.getParentId()==null || comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
    }
}
