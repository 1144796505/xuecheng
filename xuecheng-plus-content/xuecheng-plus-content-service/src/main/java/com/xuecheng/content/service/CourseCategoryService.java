package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * ClassName: CourseCategoryService
 * Package: com.xuecheng.content.service
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/8 - 14:34
 * @Version: v1.0
 */
public interface CourseCategoryService {
    /**
     * 课程分类树形结构查询
     * @param id
     * @return
     */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
