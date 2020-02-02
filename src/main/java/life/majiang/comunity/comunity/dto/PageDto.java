package life.majiang.comunity.comunity.dto;

import lombok.Data;

@Data
public class PageDto {
    private Integer count;
    private Boolean hasNext;
    private Integer currentPage;
}
