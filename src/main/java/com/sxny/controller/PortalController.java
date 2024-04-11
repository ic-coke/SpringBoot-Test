package com.sxny.controller;

import com.sxny.pojo.PortalVo;
import com.sxny.service.HeadlineService;
import com.sxny.service.TypeService;
import com.sxny.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: SpringBoot-Test-0409
 * @description: 查询首页
 * @author: coke
 * @create: 2024-04-10 19:23
 **/
@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {
    @Autowired
    private HeadlineService headlineService;
    @Autowired
    private TypeService typeService;

    //查询首页分类
    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        Result result = typeService.findAllTypes();
        return result;
    }

    //分页查询首页头条信息
    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewsPage(portalVo);
        return result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(@Param("hid") Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }
}
