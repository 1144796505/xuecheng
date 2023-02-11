package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: EditCourseDto
 * Package: com.xuecheng.content.model.dto
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/9 - 11:16
 * @Version: v1.0
 */
@Data
@ApiModel(value="EditCourseDto", description="修改课程基本信息")
public class EditCourseDto extends AddCourseDto{
    @ApiModelProperty(value = "课程名称", required = true)
    private Long id;
}
