package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired(required = false)
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(@CookieValue("token") String token, HttpServletRequest request){
        //1.判断cookies中是否含有token
        //2.利用token去查询数据库中是否有相同的用户
        //3.选择性展示信息
        if(token!=null){
            User user = userMapper.findByToken(token);
            if(user!=null) request.getSession().setAttribute("user", user);
        }

        return "index";
    }

}
