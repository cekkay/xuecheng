package com.xuecheng.content.service;

import com.xuecheng.content.model.po.CourseTeacher;

import java.util.List;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/10-16:54
 */
public interface CourseTeacherService {

    /**
     * 根据课程id查询授课教师
     * @param courseId 课程id
     * @return
     */
    List<CourseTeacher> queryCourseTeacherList(Long courseId);

    /**
     * 添加或修改课程教师
     * @param courseTeacher
     * @return
     */
    CourseTeacher addCourseTeacher(CourseTeacher courseTeacher);

    /**
     * 根据课程id和教师id删除课程教师
     * @param courseId 课程id
     * @param id 教师id
     */
    void removeCourseTeacher(Long courseId, Long id);
}
