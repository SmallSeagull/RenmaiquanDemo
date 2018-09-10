package com.dolphin.renmaicircle.utils;

/**
 * Created by Dolphin on 2018/7/31.
 */

public class Constant {

    public static final int code_200 = 200;//成功
    public static final int code_201 = 201;//失败
    public static final int code_202 = 202;//参数错误（为空，缺少,格式不正确等）
    public static final int code_203 = 203;//手机已注册
    public static final int code_204 = 204;//验证码错误
    public static final int code_205 = 205;//两次输入的密码不一致
    public static final int code_206 = 206;//账户不存在
    public static final int code_207 = 207;//密码错误
    public static final int code_208 = 208;//登录过期
    public static final int code_209 = 209;//验证码过期
    public static final int code_210 = 210;//身份验证失败，请重新登录
    public static final int code_500 = 500;//系统错误

    public static final String SYSTEM_ERROR = "系统错误";
    public static final String REQUEST_FAILED = "请求失败";

    public static final String FIRST_LOAD = "first_load";
    public static final String MORE_LOAD = "more_load";

    public static final String ISLOGIN = "isLogin";
    public static final String ISFIRST = "isFirst";
    public static final String PASSWORD = "password";
    public static final String NUMBER = "number";
    public static final String NICKNAME = "nickname";
    public static final String INTEGRAL = "integral";
    public static final String IDENTITY_STATUS = "identityStatus";
    public static final String AVATAR = "avatar";
    public static final String TOKEN = "token";

    public static final String UPDATE_AVATAR_NAME = "update_avatar_name";
    public static final String UPDATE_BANKCARD = "update_bankcard";
}
