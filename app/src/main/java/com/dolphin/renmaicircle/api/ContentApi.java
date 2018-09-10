package com.dolphin.renmaicircle.api;

import android.content.Context;
import android.text.TextUtils;

import com.dolphin.renmaicircle.utils.AppSharedPreferences;
import com.dolphin.renmaicircle.utils.Constant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Dolphin on 2018/8/6.
 */

public class ContentApi {
        public static final String HOST = "https://120.78.184.35:8943/";
//    public static final String HOST = "http://192.168.0.130:8080/";
//    public static final String HOST = "http://192.168.0.119:8080/";


    private static HttpHeaders headers;
    private static HttpParams params;


    //注册
    public static void register(Context context, String phoneNumber, String smsCode, String password, String invitationCode, StringCallback c) {
        params = new HttpParams();
        params.put("phoneNumber", phoneNumber);
        params.put("smsCode", smsCode);
        params.put("password", password);
        if (!TextUtils.isEmpty(invitationCode)) {
            params.put("invitationCode", invitationCode);
        }
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/user/register")
                .tag(context)
                .params(params)
                .execute(c);
    }

    //获取短信验证码
    public static void getSMScode(Context context, String phoneNumber, StringCallback c) {
        params = new HttpParams();
        params.put("phoneNumber", phoneNumber);
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/common/getSMSCode")
                .tag(context)
                .params(params)
                .execute(c);
    }

    //登录
    public static void login(Context context, String phoneNumber, String password, StringCallback c) {
        params = new HttpParams();
        params.put("phoneNumber", phoneNumber);
        params.put("password", password);
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/user/login")
                .tag(context)
                .params(params)
                .execute(c);
    }

    //找回密码
    public static void findPassword(Context context, String phoneNumber, String smsCode, String password, StringCallback c) {
        params = new HttpParams();
        params.put("phoneNumber", phoneNumber);
        params.put("smsCode", smsCode);
        params.put("password", password);
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/user/retrieveLoginPwd")
                .tag(context)
                .params(params)
                .execute(c);
    }

    //我的收藏
    public static void getFavorite(Context context, int pageNumber, StringCallback c) {
        params = new HttpParams();
        params.put("pageNumber", pageNumber);
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/enshrine/getEnshrineList")
                .tag(context)
                .params(params)
                .headers(headers)
                .execute(c);
    }

    //修改密码
    public static void changePassword(Context context, String newPassword, String smsCode, String phoneNumber, StringCallback c) {
        params = new HttpParams();
        params.put("newPassword", newPassword);
        params.put("smsCode", smsCode);
        params.put("phoneNumber", phoneNumber);
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/user/updateLoginPwd")
                .tag(context)
                .params(params)
                .headers(headers)
                .execute(c);
    }

    //提交意见反馈
    public static void publishFeedback(Context context, String feedbackContent, StringCallback c) {
        params = new HttpParams();
        params.put("feedbackContent", feedbackContent);
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/feedback/submitFeedback")
                .tag(context)
                .params(params)
                .headers(headers)
                .execute(c);
    }

    //获取所有标签列表
    public static void getAllLebel(Context context, StringCallback c) {
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/userInformation/getLabelList")
                .tag(context)
                .headers(headers)
                .execute(c);
    }

    //获取用户标签列表
    public static void getUserLebel(Context context, StringCallback c) {
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/userInformation/getUserLabelList")
                .tag(context)
                .headers(headers)
                .execute(c);
    }

    //我的标签确认操作
    public static void labelConfirm(Context context, String labels, StringCallback c) {
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        params = new HttpParams();
        params.put("labels", labels);
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/userInformation/confirmUserLabel")
                .tag(context)
                .headers(headers)
                .params(params)
                .execute(c);
    }

    //查看个人资料 type 0-查看自己信息   1-查看他人信息    user_id-被查看人的userId
    public static void viewInformation(Context context, int type, int user_id, StringCallback c) {
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        params = new HttpParams();
        params.put("type", type);
        if (user_id != -1) {
            params.put("user_id", user_id);
        }
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/userInformation/getUserInformation")
                .tag(context)
                .headers(headers)
                .params(params)
                .execute(c);
    }

    //修改个人资料
    /*
    * headPortraitImg 头像
    * nickName 昵称
    * sex 性别 1-男  2-女
    * email 邮箱
    * brithday 生日
    * region 地区
    * address 详细地址
    * company 公司
    * position 职位
    * school 学校
    * education 学历
    * */
    public static void updateInformation(Context context, File headPortraitImg, String nickName, int sex,
                                         String email, String brithday, String region, String address, String company,
                                         String position, String school, String education, StringCallback c) {
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        params = new HttpParams();
        if (headPortraitImg != null) params.put("headPortraitImg", headPortraitImg);
        if (nickName != null) params.put("nickName", nickName);
        if (sex > 0) params.put("sex", sex);
        if (email != null) params.put("email", email);
        if (brithday != null) params.put("brithday", brithday);
        if (region != null) params.put("region", region);
        if (address != null) params.put("address", address);
        if (company != null) params.put("company", company);
        if (position != null) params.put("position", position);
        if (school != null) params.put("school", school);
        if (education != null) params.put("education", education);
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/userInformation/updateUserInformation")
                .tag(context)
                .headers(headers)
                .params(params)
                .execute(c);
    }

    //查询银行卡列表
    public static void getBankCardList(Context context, StringCallback c) {
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/userInformation/getBankCardList")
                .tag(context)
                .headers(headers)
                .execute(c);
    }


    //删除银行卡
    public static void deleteBankCard(Context context, int id,StringCallback c) {
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        params = new HttpParams();
        params.put("id",id);
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/userInformation/deleteBankCard")
                .tag(context)
                .headers(headers)
                .params(params)
                .execute(c);
    }

    //获取银行卡开户行
    public static void getOpeningBank(Context context,String bcNumberCard,StringCallback c){
        params = new HttpParams();
        params.put("bcNumberCard",bcNumberCard);
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/bind/judge")
                .tag(context)
                .params(params)
                .execute(c);
    }

    //添加银行卡
    public static void addBankCard(Context context,String bankCardNumber,String bank,StringCallback c){
        headers = new HttpHeaders();
        headers.put(Constant.TOKEN, AppSharedPreferences.getInstance(context).getToken());
        params = new HttpParams();
        params.put("bankCardNumber",bankCardNumber);
        params.put("bank",bank);
        OkGo.getInstance()
                .setCacheMode(CacheMode.NO_CACHE)
                .<String>post(HOST + "CircleOfPeople/userInformation/addBankCard")
                .tag(context)
                .headers(headers)
                .params(params)
                .execute(c);
    }
}
