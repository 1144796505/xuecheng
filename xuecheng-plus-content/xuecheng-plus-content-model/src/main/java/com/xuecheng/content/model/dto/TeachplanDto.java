package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * ClassName: TeachplanDto
 * Package: com.xuecheng.content.model.dto
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/9 - 14:15
 * @Version: v1.0
 *  课程结构树形计划dto
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {
    //课程计划关联的媒资信息
    TeachplanMedia teachplanMedia;

    //子结点
    List<TeachplanDto> teachPlanTreeNodes;
}
