package com.xuecheng.content;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author shi
 * @Description 相关描述
 * @create 2023-05-2023/5/7-22:24
 */

@SpringBootTest
public class CourseCategoryServiceTests {
    @Autowired
    CourseCategoryService courseCategoryService;
    @Test
    public void testqueryTreeNodes() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }
}
