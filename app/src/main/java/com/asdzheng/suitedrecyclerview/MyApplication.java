package com.asdzheng.suitedrecyclerview;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.asdzheng.suitedrecyclerview.ui.drawable.Drawables;
import com.asdzheng.suitedrecyclerview.utils.ConfigConstants;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;


/**
 * Created by asdzheng on 2015/12/10.
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        Drawables.init(getResources());
        configure();
    }

    public void configure() {
        Picasso.setSingletonInstance(new Picasso.Builder(context.getApplicationContext()).
                downloader(new OkHttpDownloader(context.getApplicationContext(), ConfigConstants.MAX_DISK_CACHE_SIZE)).
                memoryCache(new LruCache(ConfigConstants.MAX_MEMORY_CACHE_SIZE)).defaultBitmapConfig(Bitmap.Config.RGB_565).
                build());
    }
}
