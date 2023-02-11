package com.xuecheng.base.execption;

/**
 * ClassName: CommonError
 * Package: com.xuecheng.base.execption
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/8 - 23:45
 * @Version: v1.0

 */

/**
 * 通用异常信息
 */
public enum CommonError {
    UNKOWN_ERROR("执行过程异常，请重试。"),
    PARAMS_ERROR("非法参数"),
    OBJECT_NULL("对象为空"),
    QUERY_NULL("查询结果为空"),
    REQUEST_NULL("请求参数为空");

    private String errMessage;

    public String getErrMessage() {
        return errMessage;
    }

    private CommonError( String errMessage) {
        this.errMessage = errMessage;
    }
}
