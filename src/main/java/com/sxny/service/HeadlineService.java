package com.sxny.service;

import com.sxny.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxny.pojo.PortalVo;
import com.sxny.utils.Result;

/**
* @author coke
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-04-09 17:48:37
*/
public interface HeadlineService extends IService<Headline> {
    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);
}
