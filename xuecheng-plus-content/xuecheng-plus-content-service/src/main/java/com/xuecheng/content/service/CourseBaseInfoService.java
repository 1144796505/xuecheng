package com.xuecheng.content.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * ClassName: CourseBaseInfoService
 * Package: com.xuecheng.content.service
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/7 - 23:43
 * @Version: v1.0
 */
public interface CourseBaseInfoService  {
    /**
     *
     * @param pageParams 当前页 , 每页显示数
     * @param queryCourseParamsDto 查询条件
     * @return
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams , QueryCourseParamsDto queryCourseParamsDto);
}
