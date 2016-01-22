package com.asdzheng.suitedrecyclerview.ui.drawable;

/**
 * Created by asdzheng on 2015/12/31.
 */

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.asdzheng.suitedrecyclerview.R;

public class Drawables {

    public static void init(final Resources resources) {
        if (sPlaceholderDrawable == null) {
            sPlaceholderDrawable = resources.getDrawable(R.color.placeholder);
        }
        if (sErrorDrawable == null) {
            sErrorDrawable = resources.getDrawable(R.color.error);
        }
    }

    public static Drawable sPlaceholderDrawable;
    public static Drawable sErrorDrawable;

    private Drawables() {
    }
}