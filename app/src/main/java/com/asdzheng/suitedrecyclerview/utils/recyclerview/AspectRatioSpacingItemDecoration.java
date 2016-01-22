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
    private static final String TAG;
    private int mSpacing;

    static {
        TAG = AspectRatioSpacingItemDecoration.class.getSimpleName();
        AspectRatioSpacingItemDecoration.DEFAULT_SPACING = 64;
    }

    public AspectRatioSpacingItemDecoration() {
        this(AspectRatioSpacingItemDecoration.DEFAULT_SPACING);
    }

    public AspectRatioSpacingItemDecoration(final int mSpacing) {
        this.mSpacing = mSpacing;
    }

    private boolean isLeftChild(final int n, final AspectRatioLayoutSizeCalculator aspectRatioLayoutSizeCalculator) {
        return aspectRatioLayoutSizeCalculator.getFirstChildPositionForRow(aspectRatioLayoutSizeCalculator.getRowForChildPosition(n)) == n;
    }

    private boolean isTopChild(final int n, final AspectRatioLayoutSizeCalculator aspectRatioLayoutSizeCalculator) {
        return aspectRatioLayoutSizeCalculator.getRowForChildPosition(n) == 0;
    }

    @Override
    public void getItemOffsets(final Rect rect, final View view, final RecyclerView recyclerView, final RecyclerView.State state) {
        if (!(recyclerView.getLayoutManager() instanceof AspectRatioLayoutManager)) {
            throw new IllegalArgumentException(String.format("The %s must be used with a %s", AspectRatioSpacingItemDecoration.class.getSimpleName(), AspectRatioLayoutManager.class.getSimpleName()));
        }
        final int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        final AspectRatioLayoutSizeCalculator sizeCalculator = ((AspectRatioLayoutManager)recyclerView.getLayoutManager()).getSizeCalculator();
        rect.top = 0;
        rect.bottom = this.mSpacing;
        rect.left = 0;
        rect.right = this.mSpacing;
        if (this.isTopChild(childAdapterPosition, sizeCalculator)) {
            rect.top = this.mSpacing;
        }
        if (this.isLeftChild(childAdapterPosition, sizeCalculator)) {
            rect.left = this.mSpacing;
        }
    }
}
