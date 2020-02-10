package life.majiang.comunity.comunity.dto;

import life.majiang.comunity.comunity.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {
        private Long id;
        private Long parentId;
        private Integer type;
        private Long commentator;
        private Long gmtCreate;
        private Long gmtModified;
        private Long likeCount;
        private String content;
        private User user;
        private List<CommentDTO> subComments;
}
