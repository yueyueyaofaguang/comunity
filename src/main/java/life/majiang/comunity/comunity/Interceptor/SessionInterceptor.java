package life.majiang.comunity.comunity.Interceptor;

import life.majiang.comunity.comunity.enums.NotificationStatusEnum;
import life.majiang.comunity.comunity.mapper.NotificationMapper;
import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.Notification;
import life.majiang.comunity.comunity.model.NotificationExample;
import life.majiang.comunity.comunity.model.User;
import life.majiang.comunity.comunity.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor{
    @Autowired(required = false)
    UserMapper userMapper;
    @Autowired(required = false)
    NotificationMapper notificationMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0)
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    UserExample useExample = new UserExample();
                    useExample.createCriteria()
                                .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(useExample);
                    if(users.size()!=0) user = users.get(0);
                }
            }
        request.getSession().setAttribute("user",user);

        NotificationExample notificationExample = new NotificationExample();
            notificationExample.createCriteria().andReceiverEqualTo(user.getId()).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        List<Notification> notificationList = notificationMapper.selectByExample(notificationExample);
        request.getSession().setAttribute("notifiyCount",notificationList.size());
        return true;
    }
}
