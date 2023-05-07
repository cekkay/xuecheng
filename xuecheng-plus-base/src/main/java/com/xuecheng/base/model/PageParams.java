package com.xuecheng.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author shi
 * @Description 分页通用查询参数模型类
 * @create 2023-05-2023/5/5-15:18
 */

@Data
@ToString
public class PageParams {

    // 当前页码
    @ApiModelProperty("页码")
    private Long pageNo = 1L;
    // 每页显示记录数
    @ApiModelProperty("每页记录数")
    private Long pageSize = 30L;

    public PageParams() {
    }

    public PageParams(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
