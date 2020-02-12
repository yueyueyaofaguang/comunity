package life.majiang.comunity.comunity.service;

import life.majiang.comunity.comunity.dto.NotificationDTO;
import life.majiang.comunity.comunity.dto.PageDto;
import life.majiang.comunity.comunity.enums.NotificationTypeEnum;
import life.majiang.comunity.comunity.mapper.CommentMapper;
import life.majiang.comunity.comunity.mapper.NotificationMapper;
import life.majiang.comunity.comunity.mapper.QuestionMapper;
import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired(required = false)
    private NotificationMapper notificationMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    QuestionMapper questionMapper;
    @Autowired(required = false)
    CommentMapper commentMapper;

    public PageDto list(Long id, Integer page, Integer size) {
        PageDto<NotificationDTO> pageDto = new PageDto();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        pageDto.setPagination(totalCount, page, size);
        if (totalCount == 0)
            return pageDto;
        if (page < 1) {
            page = 1;
        }
        if (page > pageDto.getTotalPage()) {
            page = pageDto.getTotalPage();
        }

        Integer offset = size * (page - 1);
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        if(notificationList.size() == 0) {
            pageDto.setData(notificationDTOS);
            return pageDto;
        }

        notificationDTOS = notificationList.stream().map(notifiy -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notifiy,notificationDTO);
            User user = userMapper.selectByPrimaryKey(notifiy.getNotifier());
            notificationDTO.setNotifier(user);
            Question question = questionMapper.selectByPrimaryKey(notifiy.getRelatedQuestion());
            notificationDTO.setQuestion(question);
            return notificationDTO;
        }).collect(Collectors.toList());

        pageDto.setData(notificationDTOS);
        return pageDto;
    }
}
