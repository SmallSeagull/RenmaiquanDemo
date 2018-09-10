package com.dolphin.renmaicircle.bean;

import java.util.List;

/**
 * Created by Dolphin on 2018/8/8.
 */

public class AllLebelBean {

    /**
     * msg : 查询成功
     * code : 200
     * data : [{"id":1,"tagName":"互联网"},{"id":2,"tagName":"游戏"},{"id":3,"tagName":"自媒体"}]
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
         * id : 1
         * tagName : 互联网
         */

        private int id;
        private String tagName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
    }
}
