package com.dolphin.renmaicircle.bean;

/**
 * Created by Dolphin on 2018/8/8.
 */

public class LoginBean {

    /**
     * msg : 登录成功
     * code : 200
     * data : {"phoneNumber":"18979796100","nickName":"18979796100","integral":0,"identityStatus":0,"headPortrait":"www.baidu.com"}
     * token : alkVMZcYOvqDgYyNRG8rZ/Yzrd9r2Q41BWMOOyXYUVYyFEaGv6ZqGrocxsXB3A7UyksQfQDfTmVRL5lV7g+KAIM8Ne2WM+nIipy8VUZRZ4U04QgmxawnYBLn2cw5GnT6U8RwrRmQ6itFJ1bWiX/8PQ==
     */

    private String msg;
    private int code;
    private DataBean data;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBean {
        /**
         * phoneNumber : 18979796100
         * nickName : 18979796100
         * integral : 0
         * identityStatus : 0
         * headPortrait : www.baidu.com
         */

        private String phoneNumber;
        private String nickName;
        private int integral;
        private int identityStatus;
        private String headPortrait;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getIdentityStatus() {
            return identityStatus;
        }

        public void setIdentityStatus(int identityStatus) {
            this.identityStatus = identityStatus;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }
    }
}
