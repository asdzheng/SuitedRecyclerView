package com.asdzheng.suitedrecyclerview.utils.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by asdzheng on 2015/12/28.
 */
public class AspectRatioSpacingItemDecoration extends RecyclerView.ItemDecoration
{
    public static int DEFAULT_SPACING;
    private static String TAG;
    private int mSpacing;

    static {
        TAG = AspectRatioSpacingItemDecoration.class.getSimpleName();
        AspectRatioSpacingItemDecoration.DEFAULT_SPACING = 64;
    }

    public AspectRatioSpacingItemDecoration() {
        this(AspectRatioSpacingItemDecoration.DEFAULT_SPACING);
    }

    public AspectRatioSpacingItemDecoration(int mSpacing) {
        this.mSpacing = mSpacing;
    }

    private boolean isLeftChild(int position, AspectRatioLayoutSizeCalculator aspectRatioLayoutSizeCalculator) {
        return aspectRatioLayoutSizeCalculator.getFirstChildPositionForRow(aspectRatioLayoutSizeCalculator.getRowForChildPosition(position)) == position;
    }

    private boolean isTopChild(int position, AspectRatioLayoutSizeCalculator aspectRatioLayoutSizeCalculator) {
        return aspectRatioLayoutSizeCalculator.getRowForChildPosition(position) == 0;
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (!(recyclerView.getLayoutManager() instanceof AspectRatioLayoutManager)) {
            throw new IllegalArgumentException(String.format("The %s must be used with a %s", AspectRatioSpacingItemDecoration.class.getSimpleName(), AspectRatioLayoutManager.class.getSimpleName()));
        }
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        AspectRatioLayoutSizeCalculator sizeCalculator = ((AspectRatioLayoutManager)recyclerView.getLayoutManager()).getSizeCalculator();
        rect.top = 0;
        rect.bottom = this.mSpacing;
        rect.left = 0;
        rect.right = this.mSpacing;
        //只有在顶部的View才需要设置top Decoration
        if (isTopChild(childAdapterPosition, sizeCalculator)) {
            rect.top = this.mSpacing;
        }
        //只有在最左边的View才需要设置left Decoration
        if (isLeftChild(childAdapterPosition, sizeCalculator)) {
            rect.left = this.mSpacing;
        }
    }
}
