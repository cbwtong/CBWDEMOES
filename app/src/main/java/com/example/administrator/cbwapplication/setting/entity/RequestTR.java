package com.example.administrator.cbwapplication.setting.entity;


import com.example.administrator.cbwapplication.common.entity.BaseEntity;

/**
 * Created by Administrator on 2016/1/6.
 */
public
class RequestTR extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String phoneNum;
    private String month;


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
