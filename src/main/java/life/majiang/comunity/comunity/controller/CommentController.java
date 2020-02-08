package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.dto.CommentDTO;
import life.majiang.comunity.comunity.dto.ResultDTO;
import life.majiang.comunity.comunity.mapper.CommentMapper;
import life.majiang.comunity.comunity.model.Comment;
import life.majiang.comunity.comunity.model.User;
import life.majiang.comunity.comunity.service.CommentService;
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
    public ResultDTO post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(2002,"未登录不能进行评论，请先登录");
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.success();
    }
}
