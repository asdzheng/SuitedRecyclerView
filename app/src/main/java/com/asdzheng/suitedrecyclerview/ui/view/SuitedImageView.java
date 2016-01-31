package com.asdzheng.suitedrecyclerview.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asdzheng.suitedrecyclerview.R;
import com.asdzheng.suitedrecyclerview.utils.LogUtil;
import com.squareup.picasso.Picasso;


/**
 * Created by asdzheng on 2015/12/28.
 */
public class SuitedImageView extends ImageView {

    private String mPhoto;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.i("imageView", "onSizeChanged");
        if(w != 0 && h != 0) {
            Picasso.with(getContext()).load(mPhoto).tag(getContext()).resize(w,h).into(this);
        }
    }

    public SuitedImageView(Context context) {
        super(context);
        this.init(null, 0);
    }

    public SuitedImageView(Context context, AttributeSet set) {
        super(context, set);
    }

    public SuitedImageView(Context context, AttributeSet set, int n) {
        super(context, set, n);
        this.init(set, n);
    }

    private void init(AttributeSet set, int n) {
        this.setScaleType(ScaleType.FIT_XY);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(getResources().getColor(R.color.default_image_bg));
    }

    public void bind(String s) {
        mPhoto = s;
    }

//    public String toString() {
//        return this.mPhoto;
//    }

    public String getPhoto() {
        return mPhoto;
    }

}
