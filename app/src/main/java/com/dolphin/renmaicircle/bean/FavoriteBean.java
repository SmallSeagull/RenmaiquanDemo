package com.dolphin.renmaicircle.bean;

import java.util.List;

/**
 * Created by Dolphin on 2018/7/31.
 */

public class FavoriteBean {

    /**
     * msg : 查询成功
     * total : 4
     * code : 200
     * data : [{"userNickName":"你猜我猜不猜","dynamicId":7,"dynamicImg":"asdhregegsd.png","time":1530674901000,"userHeadPortrait":"bwiabkjsdba.png","labelName":"电影","headLine":"kjwbadkjawdb.png"},{"userNickName":"你猜呀","dynamicId":5,"dynamicImg":"hregegsd.png","time":1532575570000,"userHeadPortrait":"laksjdow.png","labelName":"游戏","headLine":"dfgdtg.jpg"}]
     * totalPageCount : 2
     */

    private String msg;
    private int total;
    private int code;
    private int totalPageCount;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userNickName : 你猜我猜不猜
         * dynamicId : 7
         * dynamicImg : asdhregegsd.png
         * time : 1530674901000
         * userHeadPortrait : bwiabkjsdba.png
         * labelName : 电影
         * headLine : kjwbadkjawdb.png
         */

        private String userNickName;
        private int dynamicId;
        private String dynamicImg;
        private long time;
        private String userHeadPortrait;
        private String labelName;
        private String headLine;
        private int iv_height;

        public int getIv_height() {
            return iv_height;
        }

        public void setIv_height(int iv_height) {
            this.iv_height = iv_height;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public int getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(int dynamicId) {
            this.dynamicId = dynamicId;
        }

        public String getDynamicImg() {
            return dynamicImg;
        }

        public void setDynamicImg(String dynamicImg) {
            this.dynamicImg = dynamicImg;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getUserHeadPortrait() {
            return userHeadPortrait;
        }

        public void setUserHeadPortrait(String userHeadPortrait) {
            this.userHeadPortrait = userHeadPortrait;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public String getHeadLine() {
            return headLine;
        }

        public void setHeadLine(String headLine) {
            this.headLine = headLine;
        }
    }
}
