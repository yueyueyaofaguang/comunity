package life.majiang.comunity.comunity.service;

import life.majiang.comunity.comunity.dto.CommentDTO;
import life.majiang.comunity.comunity.enums.CommentTypeEnum;
import life.majiang.comunity.comunity.enums.NotificationStatusEnum;
import life.majiang.comunity.comunity.enums.NotificationTypeEnum;
import life.majiang.comunity.comunity.exception.CustomizeResCode;
import life.majiang.comunity.comunity.exception.GetJsonException;
import life.majiang.comunity.comunity.mapper.*;
import life.majiang.comunity.comunity.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired(required = false)
    CommentMapper commentMapper;
    @Autowired(required = false)
    QuestionMapper questionMapper;
    @Autowired(required = false)
    QuestionExMapper questionExMapper;
    @Autowired(required = false)
    UserMapper userMapper;
    @Autowired(required = false)
    NotificationMapper notificationMapper;

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
            createNotify(comment.getCommentator(),dbComment.getCommentator(),comment.getId(),
                    NotificationTypeEnum.COMMENT_NOTIFICATION,dbComment.getParentId());
        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null)
                throw new GetJsonException(CustomizeResCode.QUESTION_NOT_FOUND);
            commentMapper.insert(comment);
            createNotify(comment.getCommentator(),question.getCreator(),question.getId(),
                    NotificationTypeEnum.QUESTION_NOTIFICATION,question.getId());
            question.setCommentCount(1);
            questionExMapper.incCommentCount(question);
        }
    }
    
    public void createNotify(Long notifier, Long receiver, Long outerid, NotificationTypeEnum notificationTypeEnum, Long relatedQuestion) {
        Notification notification = new Notification();
        notification.setNotifier(notifier);
        notification.setReceiver(receiver);
        notification.setOuterid(outerid);
        notification.setType(notificationTypeEnum.getType());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setRelatedQuestion(relatedQuestion);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listById(Long id,CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("GMT_CREATE DESC");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转换成Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment 为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment->{
            CommentDTO commentDTO = new CommentDTO();
            List<CommentDTO> subComment = listById(comment.getId(),CommentTypeEnum.COMMENT);
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            commentDTO.setSubComments(subComment);
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
