package com.asdzheng.suitedrecyclerview.utils.transition;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.asdzheng.suitedrecyclerview.utils.recyclerview.Size;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Bruce Too
 * On 9/26/15.
 * At 15:13
 * ActivityTransitionExit
 * Don`t forget add transparent theme in target activity
 * <style name="Transparent">
 * <item name="android:windowNoTitle">true</item>
 * <item name="android:windowIsTranslucent">true</item>
 * <item name="android:windowBackground">@android:color/transparent</item>
 * </style>
 */
public class ActivityTransitionExitHelper {

    private final String TAG = this.getClass().getSimpleName();

    private final DecelerateInterpolator decelerator = new DecelerateInterpolator();
    private final Interpolator accelerator = new AccelerateDecelerateInterpolator();
    private static final int DEFUALT_ANIM_DURATION = 300;
    private static final int SCALE_ANIM_DURATION = 500;

    private Intent fromIntent;//intent from pre activity
    private PhotoView toView;//target view show in this activity
    private View background; //root view of this activity
    private ColorDrawable bgDrawable; //background color
    private float leftDelta;
    private float topDelta;
    private float widthDelta;
    private float heightDelta;

    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;

    private int animDuration = DEFUALT_ANIM_DURATION;

    public boolean isStarting = true;

    public ActivityTransitionExitHelper(Intent fromIntent) {
        this.fromIntent = fromIntent;
    }

    public static ActivityTransitionExitHelper with(Intent intent) {
        return new ActivityTransitionExitHelper(intent);
    }

    /**
     * add target view
     *
     * @param toView
     * @return
     */
    public ActivityTransitionExitHelper toView(View toView) {
        this.toView = (PhotoView) toView;
        return this;
    }

    /**
     * add root view of this layout
     *
     * @param background
     * @return
     */
    public ActivityTransitionExitHelper background(View background) {
        this.background = background;
        return this;
    }

    /**
     * @param savedInstanceState if savedInstanceState != null
     *                           we don`t have to perform the transition animation
     */
    public ActivityTransitionExitHelper start(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            thumbnailTop = fromIntent.getIntExtra(ActivityTransitionEnterHelper.PRE_NAME + ".top", 0);
            thumbnailLeft = fromIntent.getIntExtra(ActivityTransitionEnterHelper.PRE_NAME + ".left", 0);
            thumbnailWidth = fromIntent.getIntExtra(ActivityTransitionEnterHelper.PRE_NAME + ".width", 0);
            thumbnailHeight = fromIntent.getIntExtra(ActivityTransitionEnterHelper.PRE_NAME + ".height", 0);

            final Size size = (Size) fromIntent.getSerializableExtra("size");

            bgDrawable = new ColorDrawable(Color.BLACK);
            background.setBackground(bgDrawable);
            toView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    //remove default
                    toView.getViewTreeObserver().removeOnPreDrawListener(this);
                    int viewLocation[] = new int[2];
                    toView.getLocationOnScreen(viewLocation);
                    leftDelta = thumbnailLeft - toView.getLeft();
                    //Note: widthDelta must be float
                    widthDelta = (float) thumbnailWidth / toView.getWidth();

                    //如果drawble还没有缓存到内存中，使用传过来预估值（有误差）
                    if (toView.getDrawable() == null) {
                        if (size.getHeight() > toView.getHeight()) {
                            size.setHeight(toView.getHeight());
                        }
//                        heightDelta = (float) thumbnailHeight / size.getHeight();

                        setScaleHeight(size.getHeight());
                        setTranslationY(size.getHeight());

//                        topDelta = thumbnailTop - toView.getTop();
//                        topDelta = topDelta - ((toView.getHeight() - size.getHeight()) / 2) * heightDelta;
//                        LogUtil.i(TAG, size.getHeight() + " topDelta = " + topDelta);
                    } else {
//                        heightDelta = (float) thumbnailHeight / toView.getDisplayRect().height();
                        setScaleHeight(toView.getDisplayRect().height());
                        setTranslationY(toView.getDisplayRect().height());
//
//                        topDelta = thumbnailTop - toView.getTop();
//                        topDelta = topDelta - ((toView.getHeight() - toView.getDisplayRect().height()) / 2) * heightDelta;

//                        LogUtil.w(TAG, " topDelta = " + topDelta + "toView Height " + toView.getDisplayRect().height());
                    }

                    runEnterAnimation();
                    return true;
                }
            });
        }
        return this;
    }

    private void runEnterAnimation() {
        isStarting = true;
        toView.setPivotX(0);
        toView.setPivotY(0); //axis
        toView.setScaleX(widthDelta);
        toView.setScaleY(heightDelta);
        toView.setTranslationX(leftDelta);
        toView.setTranslationY(topDelta);

        toView.animate().translationX(0).translationY(0)
                .scaleX(1).scaleY(1).setDuration(DEFUALT_ANIM_DURATION)
                .setInterpolator(accelerator).start();

        ObjectAnimator bgAnim = ObjectAnimator.ofInt(bgDrawable, "alpha", 0, 255);
        bgAnim.setInterpolator(accelerator);
        bgAnim.setDuration(DEFUALT_ANIM_DURATION);
        bgAnim.start();

        //防止双击，在还没显示全就退出
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isStarting = false;
            }
        }, 500);

    }

    public void runExitAnimation(final Runnable exit) {

//        LogUtil.i(TAG, "toView Height " +
//                toView.getDisplayRect().height() + " | scale " + toView.getScale());

        setScaleHeight(toView.getDisplayRect().height() / toView.getScale());
        setTranslationY(toView.getDisplayRect().height() / toView.getScale());

//        heightDelta = (float) thumbnailHeight / (toView.getDisplayRect().height() / toView.getScale());
//        topDelta = thumbnailTop - toView.getTop();
//        topDelta = topDelta - ((toView.getHeight() - (toView.getDisplayRect().height() / toView.getScale())) / 2) * heightDelta;

        //由于photoView在缩小的时候被放大了，所以缩小前需要将其先恢复到正常状态
        toView.setZoomTransitionDuration(300);
        toView.setScale(1.0f, true);

        //targetApi 16
        toView.animate().translationX(leftDelta).translationY(topDelta)
                .scaleX(widthDelta).scaleY(heightDelta)
                .setInterpolator(decelerator)
                .setDuration(animDuration)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        background.setVisibility(View.GONE);
                        toView.setVisibility(View.GONE); //let background and target view invisible
                        exit.run();
                    }
                }).start();

//        //animate color drawable of background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(bgDrawable, "alpha", 0);
        bgAnim.setInterpolator(decelerator);
        bgAnim.setDuration(animDuration);
        bgAnim.start();
    }

    private void setScaleHeight(float photoNoScaleHeight) {
        heightDelta = thumbnailHeight / photoNoScaleHeight;
    }

    private void setTranslationY(float photoNoScaleHeight) {
        //因为大图有上下黑边，下面的算法就是在扩大和缩小时考虑到上下黑边所带来的影响
        topDelta = thumbnailTop - toView.getTop();
        topDelta = topDelta - ((toView.getHeight() - photoNoScaleHeight) / 2) * heightDelta;
    }


}
