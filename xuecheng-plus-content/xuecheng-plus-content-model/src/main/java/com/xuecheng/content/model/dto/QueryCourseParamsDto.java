package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * ClassName: QueryCourseParamsDto
 * Package: com.xuecheng.content.model.dto
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/7 - 20:57
 * @Version: v1.0
 */
@Data
@ToString
public class QueryCourseParamsDto {

    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;

}
