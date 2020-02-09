package life.majiang.comunity.comunity.dto;

import lombok.Data;

@Data
public class QdCommentDTO {
    String avatarUrl;
    String name;
    Long LikeCount;
    String content;
    Long GmtCreate;
}
