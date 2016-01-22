package com.asdzheng.suitedrecyclerview.http;

/**
 * Created by asdzheng on 2015/12/25.
 */
public class UrlUtil {
    private static String base_url = "https://v2.same.com";

    public static final String CHANNEL_BOOK_STATE = "/channel/%s/booked/state.json";
    public static final String CHANNEL_DETAIL = "/channel/%s/detail";
    public static final String CHANNEL_IGNORE_REPORTED_SENSE = "/sense/%s/indict/ignore";
    public static final String CHANNEL_LIST_BY_CATE = "/channels/cate/%s";
    public static final String CHANNEL_MUSIC = "/channel/%s/rand/songs";
    public static final String CHANNEL_REPORTED_SENSES = "/channel/%s/senses/indicted?limit=10";
    public static final String CHANNEL_REPORTED_SENSE_COUNT = "/channel/%s/senses/indicted/count";
    public static final String CHANNEL_SENSE = "/channel/%s/senses";
    public static final String CHANNEL_SHUFFLE_MUSIC = ":mobile:/music/random/channel/%s";
    public static final String CHANNEL_TAGS = "/categories";
    public static final String CHANNEL_THEME_LIST = "/theme/%s";

    public static final String USER_BOOK_CHANNEL = "/channel/%s/book";
    public static final String USER_CHANNELS = "/user/%s/channels?_2";
    public static final String USER_CHANNEL_SENSE = "/user/%s/channel/%s/senses";
    public static final String USER_CREATE = "/user/create";
    public static final String USER_CREATED_CHANNELS = "/user/%s/own/channels";
    public static final String USER_LOGIN = "/user/login";
    public static final String USER_LOGOUT = "/user/logout";
    public static final String USER_LOVED_SENSES = "/user/%s/loves";
    public static final String USER_PROFILE = "/user/%s/profile";
    public static final String USER_REPORT_ILLEGAL_MUSIC = "/media/repair";
    public static final String USER_REPORT_MUSIC_PLAY = "/song/%s/play";
    public static final String USER_SENSES = "/user/%s/senses";
    public static final String USER_WRITE_CHANNELS = "/user/%s/channels/write?_2";

    //SexyChannel
    public static final String SEXY_CHANNEL = "/channel/1033563/senses";
    public static final String BEAUTY_CHANNEL = "/channel/1015326/senses";

    public static String getBaseUrl(String next) {
        return base_url + next;
    }

}
