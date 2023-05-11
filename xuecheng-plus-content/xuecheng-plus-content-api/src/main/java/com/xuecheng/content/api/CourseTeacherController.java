package com.xuecheng.content.api;

import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shi
 * @Description 教师基本信息
 * @create 2023-05-2023/5/10-16:49
 */

@Api("教师基本信息编辑接口")
@RestController
public class CourseTeacherController {

    @Autowired
    CourseTeacherService courseTeacherService;

    @ApiOperation("查询显示所有教师")
    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> list(@PathVariable Long courseId) {
        return courseTeacherService.queryCourseTeacherList(courseId);
    }

    @ApiOperation("添加或修改教师")
    @PostMapping("/courseTeacher")
    public CourseTeacher addCourseTeacher(@RequestBody CourseTeacher courseTeacher) {
        return courseTeacherService.addCourseTeacher(courseTeacher);
    }

    @ApiOperation("根据课程id和教师id删除教师")
    @DeleteMapping("/courseTeacher/course/{courseId}/{id}")
    public void removeCourseTeacher(@PathVariable Long courseId, @PathVariable Long id) {
        courseTeacherService.removeCourseTeacher(courseId, id);
    }
}
