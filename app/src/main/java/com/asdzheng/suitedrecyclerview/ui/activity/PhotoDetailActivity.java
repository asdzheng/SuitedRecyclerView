package com.asdzheng.suitedrecyclerview.ui.activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.asdzheng.suitedrecyclerview.R;
import com.asdzheng.suitedrecyclerview.ui.view.MaterialProgressBar;
import com.asdzheng.suitedrecyclerview.utils.LogUtil;
import com.asdzheng.suitedrecyclerview.utils.transition.ActivityTransitionExitHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * Created by Administrator on 2016-1-4.
 */
public class PhotoDetailActivity extends BaseActivity {

    private final String TAG = this.getClass().getSimpleName();

    private uk.co.senab.photoview.PhotoView imageView;

    private ActivityTransitionExitHelper transitionExitHelper;

    private boolean isFinishing = false;

    private MaterialProgressBar progressBar;

    @Override
    protected int setLayout() {
        return R.layout.activity_channel_photo_detail;
    }

    @Override
    protected void initViews() {
        imageView = (uk.co.senab.photoview.PhotoView) findViewById(R.id.iv_channel_photo_detail);
        progressBar = (MaterialProgressBar) findViewById(R.id.mp_photo_detail);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String photo = getIntent().getStringExtra("photo");
        Picasso.with(this).load(photo).tag(this).into(imageView, new Callback() {

                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                        LogUtil.i("PhotoDetailActivity", "imageView.getWidth " + imageView.getDisplayRect().width()
                                + " | imageView.getHeight()" + imageView.getDisplayRect().height());
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);

                    }

                }
        );

        transitionExitHelper = ActivityTransitionExitHelper.with(getIntent())
                .toView(imageView).background(findViewById(R.id.rl_photo_detail)).start(savedInstanceState);


        imageView.setMediumScale(2.0f);
        imageView.setMaximumScale(2.00001f);
        //默认能过缩放两次，现在设置为一次
        imageView.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                animExitActivity();
                LogUtil.w(TAG, "onSingleTapConfirmed");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                try {
                    float scale = imageView.getScale();
                    float x = e.getX();
                    float y = e.getY();

                    if (scale < imageView.getMediumScale()) {
                        imageView.setScale(imageView.getMediumScale(), x, y, true);
                    } else {
                        imageView.setScale(imageView.getMinimumScale(), x, y, true);
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    // Can sometimes happen hen getX() and getY() is called
                }
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                LogUtil.w(TAG, "onDoubleTapEvent");
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (progressBar.getVisibility() == View.GONE) {
            animExitActivity();
        } else {
            finish();
        }
    }

    private void animExitActivity() {
        if (!transitionExitHelper.isStarting && !isFinishing) {
            isFinishing = true;
            transitionExitHelper.runExitAnimation(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}
