package life.majiang.comunity.comunity.controller;
import life.majiang.comunity.comunity.enums.NotificationStatusEnum;
import life.majiang.comunity.comunity.mapper.NotificationMapper;
import life.majiang.comunity.comunity.model.Notification;
import life.majiang.comunity.comunity.model.NotificationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class NotificationController {
    @Autowired(required = false)
    NotificationMapper notificationMapper;
    /**
     * 用来取消消息的已读状态
     * @param id(notification的id)
     */
    @GetMapping("/notification/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void notification(@PathVariable(name = "id") Long id){
        Notification record = new Notification();
        record.setStatus(NotificationStatusEnum.READ.getStatus());
        NotificationExample example = new NotificationExample();
        example.createCriteria().andIdEqualTo(id);
        notificationMapper.updateByExampleSelective(record, example);
    }
}
