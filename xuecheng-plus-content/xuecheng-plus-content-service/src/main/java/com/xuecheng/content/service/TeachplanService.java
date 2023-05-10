package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
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

    /**
     * 插入或修改课程查询计划
     * @param saveteachplanDto
     */
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto);

    /**
     * 根据课程教学计划id删除课程教学计划
     * @param teachplanId
     */
    public void deleteTeachplan(Long teachplanId);

    /**
     * 实现课程计划的上移/下移动
     * @param moveType
     * @param teachplanId
     */
    public void moveTeachplan(String moveType, Long teachplanId);
}
