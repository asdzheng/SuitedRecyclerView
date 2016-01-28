package com.asdzheng.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by asdzheng on 2015/12/28.
 */
public class SuitedItemDecoration extends RecyclerView.ItemDecoration
{
    public static int DEFAULT_SPACING;
    private static String TAG;
    private int mSpacing;

    static {
        TAG = SuitedItemDecoration.class.getSimpleName();
        SuitedItemDecoration.DEFAULT_SPACING = 64;
    }

    public SuitedItemDecoration() {
        this(SuitedItemDecoration.DEFAULT_SPACING);
    }

    public SuitedItemDecoration(int mSpacing) {
        this.mSpacing = mSpacing;
    }

    private boolean isLeftChild(int position, SizeCaculator aspectRatioLayoutSizeCalculator) {
        return aspectRatioLayoutSizeCalculator.getFirstChildPositionForRow(aspectRatioLayoutSizeCalculator.getRowForChildPosition(position)) == position;
    }

    private boolean isTopChild(int position, SizeCaculator aspectRatioLayoutSizeCalculator) {
        return aspectRatioLayoutSizeCalculator.getRowForChildPosition(position) == 0;
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (!(recyclerView.getLayoutManager() instanceof SuitedLayoutManager)) {
            throw new IllegalArgumentException(String.format("The %s must be used with a %s", SuitedItemDecoration.class.getSimpleName(), SuitedLayoutManager.class.getSimpleName()));
        }
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        SizeCaculator sizeCalculator = ((SuitedLayoutManager)recyclerView.getLayoutManager()).getSizeCalculator();
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
