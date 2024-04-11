package com.sxny.pojo;

import lombok.Data;

/**
 * @program: SpringBoot-Test-0409
 * @description: 分页查询
 * @author: coke
 * @create: 2024-04-10 19:37
 **/
@Data
public class PortalVo {
    private String keyWords;
    private Integer type;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
