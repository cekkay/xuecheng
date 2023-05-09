package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/6-22:28
 */

@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {

        // 拼装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        // 根据名称模糊查询，在sql中拼接course_base.name like '%值%'
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()), CourseBase::getName, queryCourseParamsDto.getCourseName());
        // 根据课程审核状态查询 course_base.audit_status = ?
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
        // 根据课程发布状态查询 course_base.status = ?
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()), CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());




        // 创建page分页参数对象，参数：当前页码，每页记录数
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        // 开始进行分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        // 数据列表
        List<CourseBase> items = pageResult.getRecords();
        // 总记录数
        long total = pageResult.getTotal();
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(items,total,pageParams.getPageNo(),pageParams.getPageSize());
        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        // 参数的合法性校验
        //if (StringUtils.isBlank(dto.getName())) {
        //    //throw new RuntimeException("课程名称为空");
        //    XueChengPlusException.cast("课程名称为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getMt())) {
        //    throw new RuntimeException("课程分类为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getSt())) {
        //    throw new RuntimeException("课程分类为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getGrade())) {
        //    throw new RuntimeException("课程等级为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getTeachmode())) {
        //    throw new RuntimeException("教育模式为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getUsers())) {
        //    throw new RuntimeException("适应人群为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getCharge())) {
        //    throw new RuntimeException("收费规则为空");
        //}

        // 向课程基本信息表course_base写入数据
        CourseBase courseBaseNew = new CourseBase();
        BeanUtils.copyProperties(dto, courseBaseNew);
        // 设置机构id，当前时间，课程审核状态(默认为未提交)，课程发布状态(为未发布)
        courseBaseNew.setCompanyId(companyId);
        courseBaseNew.setCreateDate(LocalDateTime.now());
        courseBaseNew.setAuditStatus("202002");
        courseBaseNew.setStatus("203001");

        // 插入数据库
        int insert = courseBaseMapper.insert(courseBaseNew);
        if (insert <= 0) {
            throw new RuntimeException("添加课程失败");
        }

        // 向课程营销信息表course_market写入数据
        CourseMarket courseMarketNew = new CourseMarket();
        BeanUtils.copyProperties(dto, courseMarketNew);
        Long courseId = courseBaseNew.getId();
        courseMarketNew.setId(courseId);
        saveCourseMarket(courseMarketNew);
        // 从数据库查询课程的详细信息，包括两部分
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfoByCourseId(courseId);
        return courseBaseInfo;
    }

    // 查询课程信息
    @Override
    public CourseBaseInfoDto getCourseBaseInfoByCourseId(Long courseId) {
        // 从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        // 从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        // 组装在一起
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        // 通过courseCategoryMapper查询分类信息，将分类名称放在courseBaseInfoDto对象
        CourseCategory courseCategoryMt = courseCategoryMapper.selectById(courseBase.getMt());
        String categoryNameMt = courseCategoryMt.getName();
        CourseCategory courseCategorySt = courseCategoryMapper.selectById(courseBase.getSt());
        String categoryNameSt = courseCategorySt.getName();
        courseBaseInfoDto.setMtName(categoryNameMt);
        courseBaseInfoDto.setStName(categoryNameSt);
        return courseBaseInfoDto;

    }


    // 单独写一个方法保存营销信息，逻辑：存在则更新，不存在则添加
    private int saveCourseMarket(CourseMarket courseMarketNew) {
        // 参数的合法性校验
        String charge = courseMarketNew.getCharge();
        if (StringUtils.isEmpty(charge)) {
            throw new RuntimeException("收费规则为空");
        }
        // 如果课程收费，价格没有填写也需要抛出异常
        if (charge.equals("201001")) {
            if (courseMarketNew.getPrice() == null || courseMarketNew.getPrice().floatValue() <= 0) {
                throw new RuntimeException("课程的价格不能为空并且必须大于0");
            }
        }

        // 从数据库查询营销信息，存在则更新，不存在则添加
        Long id = courseMarketNew.getId();
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        if (courseMarket == null) {
            // 插入数据库
            return courseMarketMapper.insert(courseMarketNew);
        } else {
            // 将courseMarketNew拷贝到courseMarket
            BeanUtils.copyProperties(courseMarketNew, courseMarket);
            courseMarket.setId(id);
            // 更新
            return courseMarketMapper.updateById(courseMarket);
        }
    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) {
        // 获取课程id
        Long courseId = editCourseDto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            XueChengPlusException.cast("课程不存在");
        }

        // 检验本机构只能修改本机构的课程
        if (!courseBase.getCompanyId().equals(companyId)) {
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }

        // 封装基本信息的数据
        BeanUtils.copyProperties(editCourseDto, courseBase);
        courseBase.setChangeDate(LocalDateTime.now());

        // 更新课程基本信息
        int i = courseBaseMapper.updateById(courseBase);
        if (i <= 0) {
            XueChengPlusException.cast("修改课程失败");
        }

        // 封装课程营销信息
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(editCourseDto, courseMarket);

        // 更新课程营销基本信息
        saveCourseMarket(courseMarket);

        // 查询课程信息
        CourseBaseInfoDto courseBaseInfoDto = getCourseBaseInfoByCourseId(courseId);
        return courseBaseInfoDto;

    }
}
