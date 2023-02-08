package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: CourseBaseInfoServiceImpl
 * Package: com.xuecheng.content.service.impl
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/7 - 23:45
 * @Version: v1.0
 */
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    private CourseBaseMapper courseBaseMapper;
    /**
     *
     * @param pageParams 当前页 , 每页显示数
     * @param queryCourseParamsDto 查询条件
     * @return
     */
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        //构建查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //课程名称
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
        //审核状态
        queryWrapper.eq(CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        //发布状态
        queryWrapper.eq(CourseBase::getStatus,queryCourseParamsDto.getPublishStatus());
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<CourseBase> courseBasePage = courseBaseMapper.selectPage(page, queryWrapper);
        List<CourseBase> records = courseBasePage.getRecords();
        long total = courseBasePage.getTotal();




        return new PageResult<>(records,total,pageParams.getPageNo(),pageParams.getPageSize());
    }
}
