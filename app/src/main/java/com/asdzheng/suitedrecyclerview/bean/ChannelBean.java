package com.asdzheng.suitedrecyclerview.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-1-18.
 */
public class ChannelBean implements Serializable{

    private String channelName;
    private String channelPhoto;
    private String channelUrl;

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelPhoto() {
        return channelPhoto;
    }

    public void setChannelPhoto(String channelPhoto) {
        this.channelPhoto = channelPhoto;
    }
}
