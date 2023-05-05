package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author shi
 * @Description 课程查询参数模型类
 * @create 2023-05-2023/5/5-15:24
 */

@Data
@ToString
public class QueryCourseParamsDto {

    // 审核状态
    private String auditStatus;
    // 课程名称
    private String courseName;
    // 发布状态
    private String publishStatus;

    public QueryCourseParamsDto() {
    }

    public QueryCourseParamsDto(String auditStatus, String courseName, String publishStatus) {
        this.auditStatus = auditStatus;
        this.courseName = courseName;
        this.publishStatus = publishStatus;
    }
}
