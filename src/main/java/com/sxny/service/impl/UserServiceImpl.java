package com.sxny.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxny.pojo.User;
import com.sxny.service.UserService;
import com.sxny.mapper.UserMapper;
import com.sxny.utils.JwtHelper;
import com.sxny.utils.MD5Util;
import com.sxny.utils.Result;
import com.sxny.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author coke
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-04-09 17:48:37
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 登录业务实现
     *
     * @param user
     * @return result封装
     */
    @Override
    public Result login(User user) {

        //根据账号查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(queryWrapper);

        //账号判断
        if (loginUser == null) {
            //账号错误
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //判断密码
        if (!StringUtils.isEmpty(user.getUserPwd())
                && loginUser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd()))) {
            //账号密码正确
            //根据用户唯一标识生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));

            Map data = new HashMap();
            data.put("token", token);

            return Result.ok(data);
        }

        //密码错误
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 查询用户数据
     *  1、判断是否在有效期内
     *  2、获取对应的token数据
     *  3、根据主键查询数据
     *  4、将数据封装到map中
     * @param token
     * @return result封装
     */
    @Override
    public Result getUserInfo(String token) {
        //1、判断是否在有效期
        if (jwtHelper.isExpiration(token))
            //true - 过期 直接返回未登录
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        //2、获取token对应数据
        int userId = jwtHelper.getUserId(token).intValue();
        //3、查询数据
        User user = userMapper.selectById(userId);
        if (user != null){
            user.setUserPwd("");
            Map map = new HashMap();
            map.put("loginUser",user);
            return Result.ok(map);
        }
        return Result.build(null,ResultCodeEnum.NOTLOGIN);
    }

    /**
     * 注册用户名检测
     *  1、获取账号数据
     *  2、根据账号进行数据库查询
     *  3、结果封装
     * @param name
     * @return result 封装
     */
    @Override
    public Result checkUserName(String name) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,name);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (user != null)
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        return Result.ok(null);
    }

    /**
     * 注册功能实现
     *  1、将数据加密
     *  2、将数据插入
     *  3、判断结果，成功返回 200，失败返回 505
     * @param user
     * @return result 封装
     */
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        Long count = userMapper.selectCount(lambdaQueryWrapper);
        if (count > 0)
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        int insert = userMapper.insert(user);
        System.out.println(insert);
        return Result.ok(null);
    }
}