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
public class ChannelImageView extends ImageView {

    private String mPhoto;

//    private Size size ;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.i("imageView", "onLayout");

//        //只有在重新第一次或者宽高改变时才需重新请求图片
//        if(size == null) {
//            size = new Size(getWidth(), getHeight());
//            Picasso.with(getContext()).load(mPhoto).tag(getContext()).resize(getWidth(),getHeight()).into(this);
//        } else {
//            if(size.getWidth() != getWidth() && size.getHeight() != getHeight()) {
//                size.setWidth(getWidth());
//                size.setHeight(getHeight());
//                Picasso.with(getContext()).load(mPhoto).tag(getContext()).resize(getWidth(),getHeight()).into(this);
//            }
//        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.i("imageView", "onSizeChanged");
//        if(w != 0 && h != 0) {
//            Picasso.with(getContext()).load(mPhoto).tag(getContext()).resize(getWidth(),getHeight()).into(this);
//        }
    }

    public ChannelImageView(final Context context) {
        super(context);
        this.init(null, 0);
    }

    public ChannelImageView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init(set, 0);
    }

    public ChannelImageView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.init(set, n);
    }

    private void init(final AttributeSet set, final int n) {
        this.setScaleType(ScaleType.FIT_XY);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(getResources().getColor(R.color.default_image_bg));
    }

    public void bind(final String s) {
//        LogUtil.i("imageView", "bind ===== width : " + getWidth() + " | height = " + getHeight());

        mPhoto = s;

//        if(getWidth() != 0 && getHeight() != 0) {
            Picasso.with(getContext()).load(s).tag(getContext()).into(this);
//            ShowImageLoader.getSharedInstance().load(getContext(), mPhoto, this);
//        }
    }

    public String toString() {
        return this.mPhoto;
    }

//    public Size getSize() {
//        return size;
//    }
//
//    public void setSize(Size size) {
//        this.size = size;
//    }

}
