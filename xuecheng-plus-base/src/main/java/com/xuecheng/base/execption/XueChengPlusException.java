package com.xuecheng.base.execption;

/**
 * ClassName: XueChengPlusException
 * Package: com.xuecheng.base.execption
 * Description:
 *
 * @Author: 侯文柯
 * @Create: 2023/2/8 - 23:44
 * @Version: v1.0
 */
public class XueChengPlusException extends RuntimeException{
    private static final long serialVersionUID = 5565760508056698922L;

    private String errMessage;

    public XueChengPlusException() {
        super();
    }
    public XueChengPlusException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }
    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(CommonError commonError){
        throw new XueChengPlusException(commonError.getErrMessage());
    }
    public static void cast(String errMessage){
        throw new XueChengPlusException(errMessage);
    }
}
