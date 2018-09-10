package com.dolphin.renmaicircle.bean;

import java.util.List;

/**
 * Created by Dolphin on 2018/8/3.
 */

public class BankCardBean {

    /**
     * msg : 查询成功
     * code : 200
     * data : [{"bankCardNumber":"6210984280004618036","bankName":"邮政","id":1}]
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
         * bankCardNumber : 6210984280004618036
         * bankName : 邮政
         * id : 1
         */

        private String bankCardNumber;
        private String bankName;
        private int id;

        public String getBankCardNumber() {
            return bankCardNumber;
        }

        public void setBankCardNumber(String bankCardNumber) {
            this.bankCardNumber = bankCardNumber;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
