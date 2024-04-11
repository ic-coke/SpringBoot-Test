package com.sxny.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxny.pojo.PortalVo;
import com.sxny.pojo.Type;
import com.sxny.service.TypeService;
import com.sxny.mapper.TypeMapper;
import com.sxny.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author coke
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-04-09 17:48:37
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {
        List<Type> list = typeMapper.selectList(null);
        return Result.ok(list);
    }
}