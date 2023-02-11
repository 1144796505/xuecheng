package com.xuecheng.base.execption;

import java.io.Serializable;

/**
 * ClassName: RestErrorResponse
 * Package: com.xuecheng.base.execption
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/9 - 0:11
 * @Version: v1.0
 */
public class RestErrorResponse implements Serializable {
    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
