package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/9-20:46
 */

@Service
public class teachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto) {

        // 根据课程教学计划id是否存在判断进行新增还是修改操作
        Long teachplanId = saveTeachplanDto.getId();
        if (teachplanId == null) {
            // 新增
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(saveTeachplanDto, teachplan);
            // 确定排序字段，找到它的同级节点个数，排序字段就是个数加1 select count(1) from
            // teachplan where course_id = 17 and parentid = 268;
            Long parentid = saveTeachplanDto.getParentid();
            Long courseId = saveTeachplanDto.getCourseId();
            int teachplanCount = getTeachplanCount(parentid, courseId);
            teachplan.setOrderby(teachplanCount + 1);
            teachplanMapper.insert(teachplan);
        } else {
            // 修改
            Teachplan teachplan = teachplanMapper.selectById(teachplanId);
            BeanUtils.copyProperties(saveTeachplanDto, teachplan);
            teachplanMapper.updateById(teachplan);
        }

    }



    private int getTeachplanCount(Long parentid, Long courseId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = queryWrapper.eq(Teachplan::getCourseId, courseId).eq(Teachplan::getParentid, parentid);
        return teachplanMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional
    public void deleteTeachplan(Long teachplanId) {
        // 从数据库中查询该课程计划id对应的课程计划是否有子节
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid, teachplanId);
        List<Teachplan> teachplans = teachplanMapper.selectList(queryWrapper);

        if (teachplans == null || teachplans.isEmpty()) {
            // 可以删除，即将该课程计划从teachplan和teachplan_media表中删除
            int i = teachplanMapper.deleteById(teachplanId);
            if (i <= 0) {
                XueChengPlusException.cast("课程计划删除失败");
            }
            LambdaQueryWrapper<TeachplanMedia> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(TeachplanMedia::getTeachplanId, teachplanId);
            teachplanMediaMapper.delete(queryWrapper1);
        } else {
            // 如果该课程教学计划有子节，则不能进行删除
            XueChengPlusException.cast("该课程计划还有子节，请先删除该课程计划的所有字节");
        }


    }
    @Transactional
    @Override
    public void moveTeachplan(String moveType, Long teachplanId) {
        // 计算当前课程计划对应的orderby和同一级别下课程计划的最小和最大的orderby
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        // 当前课程计划对应的grade
        Integer orderby = teachplan.getOrderby();
        // 当前课程的父课程计划
        Long parentid = teachplan.getParentid();

        if ("moveup".equals(moveType)) {
            // 上移
            // 只有当前节点不是最顶节点时才需要交换
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getParentid, parentid).lt(Teachplan::getOrderby,orderby).orderByDesc(Teachplan::getOrderby).last("limit 1");
            Teachplan teachplan1 = teachplanMapper.selectOne(queryWrapper);
            if (teachplan1 != null) {
                // 交换
                Integer exchangeOrderby = teachplan1.getOrderby();
                teachplan.setOrderby(exchangeOrderby);
                teachplan1.setOrderby(orderby);
                teachplanMapper.updateById(teachplan);
                teachplanMapper.updateById(teachplan1);
            }
        } else {
            // 下移
            // 只有当前节点不是最底节点时才需要交换
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getParentid, parentid).gt(Teachplan::getOrderby,orderby).orderByAsc(Teachplan::getOrderby).last("limit 1");
            Teachplan teachplan1 = teachplanMapper.selectOne(queryWrapper);
            if (teachplan1 != null) {
                // 交换
                Integer exchangeOrderby = teachplan1.getOrderby();
                teachplan.setOrderby(exchangeOrderby);
                teachplan1.setOrderby(orderby);
                teachplanMapper.updateById(teachplan);
                teachplanMapper.updateById(teachplan1);
            }
        }
    }


}
