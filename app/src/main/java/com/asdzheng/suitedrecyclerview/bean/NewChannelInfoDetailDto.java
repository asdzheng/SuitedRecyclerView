package com.asdzheng.suitedrecyclerview.bean;

/**
 * Created by asdzheng on 2015/12/10.
 */
public class NewChannelInfoDetailDto {
        public static final int TYPE_ELLIPSIS = 1;
        public static final int TYPE_EXPENDED = 0;
        public NewChannelInfoDetailChannelDto channel;
        public transient int collects;
        public String created_at;
        public int destroyed;
        public String id;
        public transient int isCollect;
        public transient Boolean isExpanded;
        public int is_liked;
        public int likes;
        public String photo;
        public transient int rankListExpendedType;
        public String txt;
        public NewChannelInfoDetailUserDto user;
        public int views;

        public NewChannelInfoDetailDto() {
            this.isExpanded = false;
            this.isCollect = 0;
            this.rankListExpendedType = 1;
         }
}


