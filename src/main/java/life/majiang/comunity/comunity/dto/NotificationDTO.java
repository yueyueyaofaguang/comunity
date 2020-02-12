package life.majiang.comunity.comunity.dto;

import life.majiang.comunity.comunity.model.Question;
import life.majiang.comunity.comunity.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer type;
    private Integer status;
    private User notifier;
    private Question question;
}
