package com.asdzheng.suitedrecyclerview.bean;

import java.util.List;

/**
 * Created by asdzheng on 2015/12/24.
 */
public class NewChannelInfoDto extends BaseResponse {

    private ChannelInfo data;

    public ChannelInfo getData() {
        return data;
    }

    public void setData(ChannelInfo data) {
        this.data = data;
    }

    public static class ChannelInfo {
        private int count;
        private String next;
        private String prev;
        private List<NewChannelInfoDetailDto> results;

        public int getCount() {
            return this.count;
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
}
