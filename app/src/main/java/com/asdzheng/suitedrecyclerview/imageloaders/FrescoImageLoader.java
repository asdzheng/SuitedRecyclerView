package com.asdzheng.suitedrecyclerview.imageloaders;

/**
 * Created by asdzheng on 2015/12/31.
 */
//public class FrescoImageLoader implements ImageLoader
//{
//    @Override
//    public void configure(final Context context) {
//    }
//
//    @Override
//    public void load(final Context context, final String s, final ImageView imageView) {
//        Glide.with(context).load(s).into(imageView);
//
//        if(imageView instanceof PhotoView) {
//            PhotoView photoView = (PhotoView) imageView;
//
//            if(photoView.getSize() != null) {
//                LogUtil.w("PicassoImageLoader", photoView.getSize().toString());
//                Glide.with(context).load(s).override(photoView.getSize().getWidth(), photoView.getSize().getHeight()).into(imageView);
//            } else {
//                Glide.with(context).load(s).into(imageView);
//            }
//
//        } else {
//            Glide.with(context).load(s).into(imageView);
//        }
//    }
//
//    @Override
//    public void load(final Context context, final String s, final ImageView imageView, @DrawableRes final int drawable) {
//        Glide.with(context).load(s).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(drawable).into(imageView);
//    }
//
//    @Override
//    public void prefetch(final Context context, final String... array) {
//        for (int length = array.length, i = 0; i < length; ++i) {
//            Glide.with(context).load(array[i]).preload();
//        }
//    }
//
//    @Override
//    public void fetchImageForSize(Context context, ShowImageLoader.AspectRatios ratios, String... imageUrls) {
//
//    }
//}
