package com.sxny.service;

import com.sxny.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxny.utils.Result;

/**
* @author coke
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-04-09 17:48:37
*/
public interface UserService extends IService<User> {

    Result login(User user);

    Result getUserInfo(String token);

    Result checkUserName(String name);

    Result regist(User user);
}
