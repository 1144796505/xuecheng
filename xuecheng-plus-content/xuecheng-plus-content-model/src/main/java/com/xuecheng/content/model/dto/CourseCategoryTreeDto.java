package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * ClassName: CourseCategoryTreeDto
 * Package: com.xuecheng.content.model.dto
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/8 - 12:41
 * @Version: v1.0
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory {
    List childrenTreeNodes;


}
