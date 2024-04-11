package com.sxny.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sxny.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxny.pojo.PortalVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author coke
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-04-09 17:48:37
* @Entity com.sxny.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
    //自定义分页查询方法
    IPage<Map> findNewsPage(IPage<Headline> page,
                            @Param("portalVo") PortalVo portalVo);
    Map selectDetail(Integer hid);
}