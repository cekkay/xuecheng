package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @author shi
 * @Description 课程教学计划管理接口
 * @create 2023-05-2023/5/9-20:44
 */
public interface TeachplanService {

    /**
     * 根据课程id查询课程的教学计划
     * @param courseId
     * @return
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);
}
