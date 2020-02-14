package life.majiang.comunity.comunity.dto;

import lombok.Data;

@Data
public class QuestionQuery {
    private String search;
    private Integer offset;
    private Integer size;
}
