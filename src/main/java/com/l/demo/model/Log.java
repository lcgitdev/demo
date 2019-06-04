package com.l.demo.model;

public class Log {
    private Integer id;

    private String userName;

    private String remark;

    private String startTime;

    private String endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public Log() {
    }

    public Log(String userName, String remark, String startTime, String endTime) {
        this.userName = userName;
        this.remark = remark;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}