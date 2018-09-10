package com.dolphin.renmaicircle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dolphin on 2018/8/9.
 */

public class InformationBean {

    /**
     * msg : 查询成功
     * code : 200
     * data : {"id":null,"userId":null,"nickName":"卿无恙","headPortrait":"kjsahfw22222.jpg","phone":"16602100174","phoneStatus":1,"email":"16602100174@163.com","emailStatus":0,"sex":0,"age":null,"brithday":"1982-01-01","company":"XXXX公司","position":"搬运工","school":"村口幼儿园","education":"无","provinceCode":null,"cityCode":null,"address":null,"createTime":null,"updateTime":null,"region":null,"labels":[{"userLabelName":"电影","labelId":5,"userLabelId":15},{"userLabelName":"游戏","labelId":4,"userLabelId":16},{"userLabelName":"IT","labelId":1,"userLabelId":17}],"wallrtMoney":null,"integral":0,"drill":null,"payStatus":0,"pocketStatus":0,"identityStatus":0}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public static class DataBean implements Serializable{
        /**
         * id : null
         * userId : null
         * nickName : 卿无恙
         * headPortrait : kjsahfw22222.jpg
         * phone : 16602100174
         * phoneStatus : 1
         * email : 16602100174@163.com
         * emailStatus : 0
         * sex : 0
         * age : null
         * brithday : 1982-01-01
         * company : XXXX公司
         * position : 搬运工
         * school : 村口幼儿园
         * education : 无
         * provinceCode : null
         * cityCode : null
         * address : null
         * createTime : null
         * updateTime : null
         * region : null
         * labels : [{"userLabelName":"电影","labelId":5,"userLabelId":15},{"userLabelName":"游戏","labelId":4,"userLabelId":16},{"userLabelName":"IT","labelId":1,"userLabelId":17}]
         * wallrtMoney : null
         * integral : 0
         * drill : null
         * payStatus : 0
         * pocketStatus : 0
         * identityStatus : 0
         */

        private int id;
        private int userId;
        private String nickName;
        private String headPortrait;
        private String phone;
        private int phoneStatus;
        private String email;
        private int emailStatus;
        private int sex;
        private int age;
        private String brithday;
        private String company;
        private String position;
        private String school;
        private String education;
        private Object provinceCode;
        private Object cityCode;
        private String address;
        private long createTime;
        private long updateTime;
        private String region;
        private Object wallrtMoney;
        private int integral;
        private double drill;
        private int payStatus;
        private int pocketStatus;
        private int identityStatus;
        private List<LabelsBean> labels;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getPhoneStatus() {
            return phoneStatus;
        }

        public void setPhoneStatus(int phoneStatus) {
            this.phoneStatus = phoneStatus;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getEmailStatus() {
            return emailStatus;
        }

        public void setEmailStatus(int emailStatus) {
            this.emailStatus = emailStatus;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getBrithday() {
            return brithday;
        }

        public void setBrithday(String brithday) {
            this.brithday = brithday;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public Object getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(Object provinceCode) {
            this.provinceCode = provinceCode;
        }

        public Object getCityCode() {
            return cityCode;
        }

        public void setCityCode(Object cityCode) {
            this.cityCode = cityCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public Object getWallrtMoney() {
            return wallrtMoney;
        }

        public void setWallrtMoney(Object wallrtMoney) {
            this.wallrtMoney = wallrtMoney;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public double getDrill() {
            return drill;
        }

        public void setDrill(double drill) {
            this.drill = drill;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public int getPocketStatus() {
            return pocketStatus;
        }

        public void setPocketStatus(int pocketStatus) {
            this.pocketStatus = pocketStatus;
        }

        public int getIdentityStatus() {
            return identityStatus;
        }

        public void setIdentityStatus(int identityStatus) {
            this.identityStatus = identityStatus;
        }

        public List<LabelsBean> getLabels() {
            return labels;
        }

        public void setLabels(List<LabelsBean> labels) {
            this.labels = labels;
        }

        public static class LabelsBean implements Serializable{
            /**
             * userLabelName : 电影
             * labelId : 5
             * userLabelId : 15
             */

            private String userLabelName;
            private int labelId;
            private int userLabelId;

            public String getUserLabelName() {
                return userLabelName;
            }

            public void setUserLabelName(String userLabelName) {
                this.userLabelName = userLabelName;
            }

            public int getLabelId() {
                return labelId;
            }

            public void setLabelId(int labelId) {
                this.labelId = labelId;
            }

            public int getUserLabelId() {
                return userLabelId;
            }

            public void setUserLabelId(int userLabelId) {
                this.userLabelId = userLabelId;
            }
        }
    }
}
