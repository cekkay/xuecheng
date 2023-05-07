package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/7-17:18
 */

@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    // 子节点
    List<CourseCategoryTreeDto> childrenTreeNodes;

}
