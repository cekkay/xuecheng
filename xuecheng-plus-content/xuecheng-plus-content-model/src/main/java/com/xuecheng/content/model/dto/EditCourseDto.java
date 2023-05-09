package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/9-14:38
 */

@Data
public class EditCourseDto extends AddCourseDto{

    @ApiModelProperty(value = "课程id", required = true)
    private Long id;

}
