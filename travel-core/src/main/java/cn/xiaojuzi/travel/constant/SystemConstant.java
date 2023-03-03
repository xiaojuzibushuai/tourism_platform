package cn.xiaojuzi.travel.constant;

/**
 * @program: tourism_platform
 * @description: 系统常量
 * @author: xiaojuzi
 * @create: 2022-02-07 10:13
 **/
public class SystemConstant {

    //验证码有效时间
    public static final int VERIFY_CODE_VAI_TIME = 5;  //单位分

    //token有效时间
    public static final int USER_INFO_TOKEN_VAI_TIME = 30;  //单位分

    public static final String VERIFY_CODE = "verify_code";

    // 关于性比常量
    public static final int GENDER_SECRET = 0; //保密
    public static final int GENDER_MALE = 1;   //男
    public static final int GENDER_FEMALE = 2;  //女

    // 关于用户状态常量
    public static final int STATE_NORMAL = 0;  //正常
    public static final int STATE_DISABLE = 1;  //冻结

    // 系统状态码
    public static final int CODE_SUCCESS = 200;
    public static final String MSG_SUCCESS = "操作成功";

    public static final int CODE_NOLOGIN = 401;
    public static final String MSG_NOLOGIN = "请先登录";

    public static final int CODE_PHONE_REGISTER = 402;
    public static final String PHONE_REGISTER = "该手机已经注册过了";

    public static final int CODE_SEND_PHONE_MESSAGE = 403;
    public static final String MSG_SEND_PHONE_MESSAGE = "发送短信失败";
    public static final int CODE_ERROR = 500;
    public static final String MSG_ERROR = "系统异常，请联系管理员";

    public static final int CODE_ERROR_PARAM = 501;  //参数异常
    public static final int CODE_ERROR_PARAM_IS_NULL = 502;
    public static final String MSG_ERROR_PARAM_IS_NULL = "参数为空";


}
