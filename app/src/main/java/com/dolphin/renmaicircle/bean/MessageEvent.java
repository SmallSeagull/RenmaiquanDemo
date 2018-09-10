package com.dolphin.renmaicircle.bean;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MessageEvent {
    private String message;
    private int value;

    public MessageEvent(String message, int value) {
        this.message = message;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public MessageEvent(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
