package life.majiang.comunity.comunity.service;

import life.majiang.comunity.comunity.mapper.UserMapper;
import life.majiang.comunity.comunity.model.User;
import life.majiang.comunity.comunity.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    public void createOrUpdate(User user){
//        User dbUser = userMapper.findByAccountId(user.getAccountId());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insertSelective(user);
        }else{
            User user1 = new User();
            user1.setBio(user.getBio());
            user1.setGmtModified(System.currentTimeMillis());
            user1.setAvatarUrl(user.getAvatarUrl());
            user1.setName(user.getName());
            user1.setToken(user.getToken());
            userMapper.updateByExampleSelective(user1,userExample);
        }
    }
}
