package com.asdzheng.suitedrecyclerview.utils.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by asdzheng on 2015/12/26.
 */
public class LinearSpacingItemDecoration extends RecyclerView.ItemDecoration
{
    public static final boolean DEFAULT_EDGE_INCLUSION = true;
    public static final int DEFAULT_SPACING = 32;
    private boolean mIncludeEdge;
    private int mSpacing;

    public LinearSpacingItemDecoration() {
        this(32, true);
    }

    public LinearSpacingItemDecoration(final int n) {
        this(n, true);
    }

    public LinearSpacingItemDecoration(final int mSpacing, final boolean mIncludeEdge) {
        this.mSpacing = mSpacing;
        this.mIncludeEdge = mIncludeEdge;
    }

    @Override
    public void getItemOffsets(final Rect rect, final View view, final RecyclerView recyclerView, final RecyclerView.State state) {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        final int itemCount = recyclerView.getAdapter().getItemCount();
        final int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        int n;
        if (linearLayoutManager.getOrientation() == 0) {
            n = 1;
        }
        else {
            n = 0;
        }
        int n2;
        if (this.mIncludeEdge) {
            n2 = this.mSpacing - childAdapterPosition * this.mSpacing / itemCount;
        }
        else {
            n2 = childAdapterPosition * this.mSpacing / itemCount;
        }
        int n3;
        if (this.mIncludeEdge) {
            n3 = (childAdapterPosition + 1) * this.mSpacing / itemCount;
        }
        else {
            n3 = this.mSpacing - (childAdapterPosition + 1) * this.mSpacing / itemCount;
        }
        if (n != 0) {
            rect.left = n2;
            rect.right = n3;
            return;
        }
        rect.top = n2;
        rect.bottom = n3;
    }
}
