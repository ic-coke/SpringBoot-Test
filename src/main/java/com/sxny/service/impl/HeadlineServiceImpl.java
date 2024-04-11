package com.sxny.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxny.pojo.Headline;
import com.sxny.pojo.PortalVo;
import com.sxny.service.HeadlineService;
import com.sxny.mapper.HeadlineMapper;
import com.sxny.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author coke
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-04-09 17:48:37
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{
    @Autowired
    private HeadlineMapper headlineMapper;

    /**
     * 首页数据查询
     *  1、进行分页数据查询
     *  2、分页数据拼接到 result 中
     *
     * 注意：  1、查询需要自定义语句，自定义mapper方法，携带分页功能
     *        2、返回结果 list<Map>
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {

        //1.条件拼接 需要非空判断
        LambdaQueryWrapper<Headline> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(portalVo.getKeyWords()),Headline::getTitle,portalVo.getKeyWords())
                .eq(portalVo.getType()!= null,Headline::getType,portalVo.getType());

        //2.分页参数
        IPage<Headline> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());

        //3.分页查询
        //查询的结果 "pastHours":"3"   // 发布时间已过小时数 我们查询返回一个map
        //自定义方法
        headlineMapper.findNewsPage(page, portalVo);

        //4.结果封装
        //分页数据封装
        Map<String,Object> pageInfo =new HashMap<>();
        pageInfo.put("pageData",page.getRecords());
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());

        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("pageInfo",pageInfo);
        // 响应JSON
        return Result.ok(pageInfoMap);
    }

    /**
     *
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map map = headlineMapper.selectDetail(hid);
        Headline headline = new Headline();
        headline.setHid(hid);
        headline.setPageViews((Integer) map.get("pageViews" + 1)); //浏览量更新 +1
        headline.setVersion((Integer) map.get("version")); //获取版本
        headlineMapper.updateById(headline);

        Map<String,Object> page = new HashMap<>();
        page.put("headline",map);
        return Result.ok(page);
    }
}