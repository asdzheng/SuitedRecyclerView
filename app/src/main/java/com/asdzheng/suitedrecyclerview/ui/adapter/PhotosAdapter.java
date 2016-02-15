package com.asdzheng.suitedrecyclerview.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.asdzheng.layoutmanager.SizeCaculator;
import com.asdzheng.suitedrecyclerview.bean.NewChannelInfoDetailDto;
import com.asdzheng.suitedrecyclerview.ui.activity.ChannelPhotoDetailActivity;
import com.asdzheng.suitedrecyclerview.ui.view.SuitedImageView;
import com.asdzheng.suitedrecyclerview.utils.StringUtil;
import com.asdzheng.suitedrecyclerview.utils.transition.ActivityTransitionEnterHelper;

import java.util.List;

/**
 * Created by asdzheng on 2015/12/28.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> implements SizeCaculator.SizeCalculatorDelegate {
    private List<NewChannelInfoDetailDto> mPhotos;

//    private ArrayMap<String, Double> photoAspectRatios;

    private Context mContext;

    public PhotosAdapter(List<NewChannelInfoDetailDto> mPhotos, Context context) {
        this.mPhotos = mPhotos;
        mContext = context;
    }

    @Override
    public double aspectRatioForIndex(int position) {
        if (position < getItemCount()) {
            NewChannelInfoDetailDto info = mPhotos.get(position);
            double ratio = StringUtil.getAspectRadioFromUrl(info.photo);
            return ratio;
        }
        return 1.0;
    }

    public void bind(@NonNull List<NewChannelInfoDetailDto> mPhotos) {
        this.mPhotos.addAll(mPhotos);
        notifyDataSetChanged();
    }

    public void clear() {
        mPhotos.clear();
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(new SuitedImageView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
//        LogUtil.i("PhotosAdapter", "ionBindViewHolder" + position);
        ((SuitedImageView) holder.itemView).bind(mPhotos.get(position).photo);
        holder.itemView.setTag(mPhotos.get(position).photo);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public PhotoViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scaleUpAnimation(v);
                }
            });
        }
    }

    private void scaleUpAnimation(View view) {
        Activity context = (Activity) view.getContext();
        ActivityTransitionEnterHelper.with(context).fromView(view).
               start(ChannelPhotoDetailActivity.class);
    }

}
