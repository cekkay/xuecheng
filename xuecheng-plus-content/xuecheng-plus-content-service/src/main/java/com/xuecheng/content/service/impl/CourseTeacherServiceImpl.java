package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.CourseTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/10-16:55
 */

@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<CourseTeacher> queryCourseTeacherList(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId, courseId);
        return courseTeacherMapper.selectList(queryWrapper);
    }

    @Transactional
    @Override
    public CourseTeacher addCourseTeacher(CourseTeacher courseTeacher) {
        // 根据courseTeacher中的id是否为null判断是添加教师还是修改教师
        Long id = courseTeacher.getId();
        if (id == null) {
            // 添加教师
            courseTeacher.setCreateDate(LocalDateTime.now());
            courseTeacherMapper.insert(courseTeacher);
            LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByDesc(CourseTeacher::getId).last("limit 1");
            return courseTeacherMapper.selectOne(queryWrapper);
        } else {
            // 修改教师
            CourseTeacher courseTeacher1 = courseTeacherMapper.selectById(id);
            BeanUtils.copyProperties(courseTeacher, courseTeacher1);
            courseTeacherMapper.updateById(courseTeacher1);
            return courseTeacher1;
        }

    }

    @Transactional
    @Override
    public void removeCourseTeacher(Long courseId, Long id) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId, courseId).eq(CourseTeacher::getId, id);
        courseTeacherMapper.delete(queryWrapper);
    }
}
