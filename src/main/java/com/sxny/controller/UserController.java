package com.sxny.controller;

import com.alibaba.druid.util.StringUtils;
import com.sxny.pojo.User;
import com.sxny.service.UserService;
import com.sxny.utils.JwtHelper;
import com.sxny.utils.Result;
import com.sxny.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: SpringBoot-Test-0409
 * @description:
 * @author: coke
 * @create: 2024-04-09 18:00
 **/
@RequestMapping("user")
@CrossOrigin //跨域
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;

    //登录校验
    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        return result;
    }

    //根据token获取用户数据
    @GetMapping("getUserInfo")
    public Result userInfo(@RequestHeader String token){ //默认为param形式，这里需要修改为header
        Result result = userService.getUserInfo(token);
        return result;
    }

    //注册用户名检测
    @PostMapping("checkUserName")
    public Result checkUserName(String name){
        Result result = userService.checkUserName(name);
        return result;
    }

    //用户注册功能
    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return result;
    }

    //登录验证和保护
    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token))
            return Result.build(ResultCodeEnum.NOTLOGIN);
        return Result.ok(null);
    }
}
