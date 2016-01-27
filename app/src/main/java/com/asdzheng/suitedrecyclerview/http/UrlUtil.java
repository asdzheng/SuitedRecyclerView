package com.asdzheng.suitedrecyclerview.http;

/**
 * Created by asdzheng on 2015/12/25.
 */
public class UrlUtil {
    private static String base_url = "https://v2.same.com";

    //SexyChannel
    public static final String SEXY_CHANNEL = "/channel/1033563/senses";
    public static final String BEAUTY_CHANNEL = "/channel/1015326/senses";

    public static String getBaseUrl(String next) {
        return base_url + next;
    }

}
