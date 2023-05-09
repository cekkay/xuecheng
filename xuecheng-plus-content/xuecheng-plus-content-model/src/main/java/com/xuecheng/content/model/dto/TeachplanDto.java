package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/9-17:46
 */

@Data
@ToString
public class TeachplanDto extends Teachplan {

    // 课程计划关联的媒资信息
    TeachplanMedia teachplanMedia;

    // 子节点
    List<TeachplanDto> teachPlanTreeNodes;

}
