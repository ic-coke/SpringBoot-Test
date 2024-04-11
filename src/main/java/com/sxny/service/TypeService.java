package com.sxny.service;

import com.sxny.pojo.PortalVo;
import com.sxny.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxny.utils.Result;

/**
* @author coke
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-04-09 17:48:37
*/
public interface TypeService extends IService<Type> {
    Result findAllTypes();
}
