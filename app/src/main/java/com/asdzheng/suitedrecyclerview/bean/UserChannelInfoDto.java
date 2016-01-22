package com.asdzheng.suitedrecyclerview.bean;

import java.util.List;

/**
 * Created by asdzheng on 2015/12/10.
 */
public class UserChannelInfoDto extends BaseDto
{private static final long serialVersionUID = 6590658010445846280L;
    private int count;
    private int infos;
    private String next;
    private String prev;
    private List<NewChannelInfoDetailDto> results;

    public int getCount() {
        return this.count;
    }

    public int getInfos() {
        return this.infos;
    }

    public String getNext() {
        return this.next;
    }

    public String getPrev() {
        return this.prev;
    }

    public List<NewChannelInfoDetailDto> getResults() {
        return this.results;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public void setInfos(final int infos) {
        this.infos = infos;
    }

    public void setNext(final String next) {
        this.next = next;
    }

    public void setPrev(final String prev) {
        this.prev = prev;
    }

    public void setResults(final List<NewChannelInfoDetailDto> results) {
        this.results = results;
    }
}