package com.docswebapps.appsupportdashboard.domain;

public class AppStatus {

    private String sysMessage;
    private String sysMessageColor;
    private String msgDetail;
    private String msgColor;
    private String appStatus;
    private String probCount;

    public String getSysMessageColor() {
        return sysMessageColor;
    }

    public void setSysMessageColor(String sysMessageColor) {
        this.sysMessageColor = sysMessageColor;
    }

    public String getSysMessage() {
        return sysMessage;
    }

    public void setSysMessage(String sysMessage) {
        this.sysMessage = sysMessage;
    }

    public String getMsgDetail() {
        return msgDetail;
    }

    public void setMsgDetail(String msgDetail) {
        this.msgDetail = msgDetail;
    }

    public String getMsgColor() {
        return msgColor;
    }

    public void setMsgColor(String msgColor) {
        this.msgColor = msgColor;
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getProbCount() {
        return probCount;
    }

    public void setProbCount(String probCount) {
        this.probCount = probCount;
    }

}
