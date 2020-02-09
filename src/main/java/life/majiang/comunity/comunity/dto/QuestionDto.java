package life.majiang.comunity.comunity.dto;

import life.majiang.comunity.comunity.model.User;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private String title;
    private String description ;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
    private Integer commentCount;
    private Integer viewCount;
    private String tag;
}
