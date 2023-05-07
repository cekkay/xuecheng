package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/7-21:45
 */
public interface CourseCategoryService {

    /**
     * 课程分类树形结构查询
     * @param id 类别id
     * @return
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
