package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.dto.CommentCreateDTO;
import life.majiang.comunity.comunity.dto.ResultDTO;
import life.majiang.comunity.comunity.exception.CustomizeResCode;
import life.majiang.comunity.comunity.mapper.CommentMapper;
import life.majiang.comunity.comunity.model.Comment;
import life.majiang.comunity.comunity.model.User;
import life.majiang.comunity.comunity.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired(required = false)
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public ResultDTO post(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeResCode.NO_LOGIN);
        }
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeResCode.COMMENT_NOT_FOUND);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.success();
    }
}
