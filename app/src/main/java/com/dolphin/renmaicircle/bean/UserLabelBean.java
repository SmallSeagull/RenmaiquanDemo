package com.dolphin.renmaicircle.bean;

import java.util.List;

/**
 * Created by Dolphin on 2018/8/8.
 */

public class UserLabelBean {

    /**
     * msg : 查询成功
     * code : 200
     * data : [{"userLabelId":1,"userLabelName":"IT"},{"userLabelId":2,"userLabelName":"策划"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userLabelId : 1
         * userLabelName : IT
         */

        private int userLabelId;
        private int labelId;
        private String userLabelName;

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

        public String getUserLabelName() {
            return userLabelName;
        }

        public void setUserLabelName(String userLabelName) {
            this.userLabelName = userLabelName;
        }
    }
}
