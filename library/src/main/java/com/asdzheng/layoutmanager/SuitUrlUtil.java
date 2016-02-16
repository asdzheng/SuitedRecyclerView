package com.asdzheng.layoutmanager;

/**
 * Created by asdzheng on 2015/12/28.
 */
public class SuitUrlUtil {
    /**
     * 字符串为null和""都返回true
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(CharSequence text) {
        boolean empty = false;
        if (text == null || text.toString().trim().length() == 0) {
            empty = true;
        }
        return empty;
    }

    public static boolean isNotEmpty(CharSequence text) {
        return !isEmpty(text);
    }

    public static double getAspectRadioFromUrl(String url) {
        if (isNotEmpty(url) && url.contains("_w") && url.contains("_h")) {
            double width = 0;
            double height = 0;

            String widthAndHeight = url.substring(url.indexOf("_w"), url.lastIndexOf("."));//得到的应该是_w123_h123这样的格式

            String widthStr = widthAndHeight.substring(2, widthAndHeight.lastIndexOf("_"));

            String heightStr = widthAndHeight.substring(widthAndHeight.lastIndexOf("_") + 2, widthAndHeight.length());

            width = Integer.parseInt(widthStr);
            height = Integer.parseInt(heightStr);

            return width / height;
        } else {
            return 1;
        }
    }

}
