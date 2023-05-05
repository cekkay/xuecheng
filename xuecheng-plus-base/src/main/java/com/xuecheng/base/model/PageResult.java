package com.xuecheng.base.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/5-15:28
 */

@Data
@ToString
public class PageResult<T> implements Serializable {

    // 数据列表
    private List<T> items;

    // 总记录数
    private long counts;

    // 当前页码
    private long page;

    // 每页记录数
    private long pageSize;

    public PageResult() {
    }

    public PageResult(List<T> items, long counts, long page, long pageSize) {
        this.items = items;
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
    }
}
