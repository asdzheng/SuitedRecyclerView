package com.asdzheng.suitedrecyclerview.imageloaders;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.util.ArrayMap;
import android.widget.ImageView;

import com.asdzheng.suitedrecyclerview.MyApplication;

/**
 * Created by asdzheng on 2015/12/26.
 */
public class ShowImageLoader {

    private static volatile ShowImageLoader sInstance;
//    private Context mContext;
    private ImageLoader mImageLoader;

    private ShowImageLoader(Context mContext, ImageLoader mImageLoader) {
//        this.mContext = mContext;
        (this.mImageLoader = mImageLoader).configure(mContext);
    }

    public static ShowImageLoader getSharedInstance() {
        synchronized (ShowImageLoader.class) {
            if (ShowImageLoader.sInstance == null) {
                ShowImageLoader.sInstance = new ShowImageLoader(MyApplication.context, new PicassoImageLoader());
            }
            return ShowImageLoader.sInstance;
        }
    }

    public void load(Context context, String s, ImageView imageView) {
        this.mImageLoader.load(context, s, imageView);
    }

    public void load(Context context, String s, ImageView imageView, ImageCallback callback) {
        this.mImageLoader.load(context, s, imageView, callback);
    }

    public void load(Context context, String s, ImageView imageView, @DrawableRes int n) {
        this.mImageLoader.load(context, s, imageView, n);
    }



//    public void fetchImageForSize(ShowImageLoader.AspectRatios ratios, String... imageUrls) {
//        this.mImageLoader.fetchImageForSize(mContext, ratios, imageUrls);
//    }

//    public void loadImageForPhotoAtSize(Photo photo, int n, ImageView imageView) {
//        this.load(photo.getImageUrlForSize(n), imageView);
//    }
//
//    public void loadImageForPhotoAtSize(Photo photo, int n, ImageView imageView, @DrawableRes int n2) {
//        this.load(photo.getImageUrlForSize(n), imageView, n2);
//    }

//    public void prefetch(List<String> list) {
//        this.mImageLoader.prefetch(this.mContext, (String[])list.toArray(new String[list.size()]));
//    }
//
//    public void prefetch(String... array) {
//        this.mImageLoader.prefetch(this.mContext, array);
//    }

//    public void prefetchImageForPhotosAtSize(int n, List<Photo> list) {
//        String[] array = new String[list.size()];
//        for (int i = 0; i < list.size(); ++i) {
//            array[i] = list.get(i).getImageUrlForSize(n);
//        }
//        this.mImageLoader.prefetch(this.mContext, array);
//    }
//
//    public void prefetchImageForPhotosAtSize(int n, Photo... array) {
//        String[] array2 = new String[array.length];
//        for (int i = 0; i < array.length; ++i) {
//            array2[i] = array[i].getImageUrlForSize(n);
//        }
//        this.mImageLoader.prefetch(this.mContext, array2);
//    }

    public void setImageLoader(ImageLoader mImageLoader) {
        this.mImageLoader = mImageLoader;
    }

    public interface AspectRatios {
        void getAspectRatios(ArrayMap<String, Double> ratios);
    }

    public void pauseTag(Context context) {
        mImageLoader.pause(context);
    }

    public void resumeTag(Context context) {
        mImageLoader.resume(context);
    }

    public void cancelRequest(Context context) {
        mImageLoader.cancelRequest(context);
    }
}
