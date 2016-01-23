package com.asdzheng.suitedrecyclerview.utils.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.asdzheng.suitedrecyclerview.utils.LogUtil;


/**
 * Created by asdzheng on 2015/12/26.
 */
public class AspectRatioLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG;
    //第一个可见的position
    private int mFirstVisiblePosition;
    //最后一个可见的position
    private int mLastVisiblePosition;

    private int mFirstVisibleRow;
    private boolean mForceClearOffsets;
    private AspectRatioLayoutSizeCalculator mSizeCalculator;

    static {
        TAG = AspectRatioLayoutManager.class.getSimpleName();
    }

    public AspectRatioLayoutManager(AspectRatioLayoutSizeCalculator.SizeCalculatorDelegate sizeCalculatorDelegate) {
        mSizeCalculator = new AspectRatioLayoutSizeCalculator(sizeCalculatorDelegate);
    }

    private int getContentHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private int getContentWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int preFillGrid(Direction direction, int dy, int childDecorateTop, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int firstChildPositionForRow = mSizeCalculator.getFirstChildPositionForRow(mFirstVisibleRow);
        SparseArray sparseArray = new SparseArray(getChildCount());
        int paddingLeft = getPaddingLeft();
        int decoratedTop = childDecorateTop + getPaddingTop();


        if (getChildCount() != 0) {
            decoratedTop = getDecoratedTop(getChildAt(0));
            LogUtil.w(TAG, "preFillGrid = decoratedTop : " + decoratedTop);

            if (mFirstVisiblePosition != firstChildPositionForRow) {
                switch (direction) {
                    case UP:
                        decoratedTop -= mSizeCalculator.sizeForChildAtPosition(-1 + mFirstVisiblePosition).getHeight();
                        LogUtil.i(TAG, "UP = " + decoratedTop);
                        break;
                    case DOWN:
                        decoratedTop += mSizeCalculator.sizeForChildAtPosition(mFirstVisiblePosition).getHeight();
                        LogUtil.i(TAG, "DOWN = " + decoratedTop);
                        break;
                }
            }
            for (int i = 0; i < getChildCount(); ++i) {
                sparseArray.put(i + mFirstVisiblePosition, (Object) getChildAt(i));
            }
            for (int j = 0; j < sparseArray.size(); ++j) {
                detachView((View) sparseArray.valueAt(j));
            }
        }
        mFirstVisiblePosition = firstChildPositionForRow;
        int childPaddingLeft = paddingLeft;
        int childPaddingTop = decoratedTop;

        for (int mFirstVisiblePosition = this.mFirstVisiblePosition; mFirstVisiblePosition >= 0 && mFirstVisiblePosition < state.getItemCount(); ++mFirstVisiblePosition) {
            Size sizeForChildAtPosition = mSizeCalculator.sizeForChildAtPosition(mFirstVisiblePosition);
            //是否加上下一个view就超过屏幕的宽度
            if (childPaddingLeft + sizeForChildAtPosition.getWidth() > getContentWidth()) {
                childPaddingLeft = paddingLeft;
                childPaddingTop += mSizeCalculator.sizeForChildAtPosition(mFirstVisiblePosition - 1).getHeight();
            }
            //是否已经到了不可见的view
            boolean reachUnVisablePosition = false;
            switch (direction) {
                default:
                    if (childPaddingTop >= getContentHeight()) {
                        reachUnVisablePosition = true;
                        break;
                    }
                    break;
                case DOWN:
                    if (childPaddingTop >= dy + getContentHeight()) {
                        reachUnVisablePosition = true;
                    } else {
                    }
                    break;
            }

//            LogUtil.i(TAG, "childPaddingTop = " + childPaddingTop + " | dy = " + dy + " | getContentHeight = " + getContentHeight() + " | n5 = " + n5);

            if (reachUnVisablePosition) {
                break;
            }

            View view = (View) sparseArray.get(mFirstVisiblePosition);
            if (view == null) {
                View viewForPosition = recycler.getViewForPosition(mFirstVisiblePosition);
                LogUtil.i(TAG, "view == null mFirstVisiblePosition = " + mFirstVisiblePosition + " | sizeForChildAtPosition = " + sizeForChildAtPosition);
                addView(viewForPosition);
                measureChildWithMargins(viewForPosition, 0, 0);
                layoutDecorated(viewForPosition, childPaddingLeft, childPaddingTop, childPaddingLeft + sizeForChildAtPosition.getWidth(), childPaddingTop + sizeForChildAtPosition.getHeight());
                LogUtil.w(TAG, "preFillGrid = getChildCount : " + getChildCount());
            } else {
                LogUtil.i(TAG, "view != null mFirstVisiblePosition = " + mFirstVisiblePosition + " | sizeForChildAtPosition = " + sizeForChildAtPosition);
                attachView(view);
                sparseArray.remove(mFirstVisiblePosition);
            }
            childPaddingLeft += sizeForChildAtPosition.getWidth();
        }
        for (int k = 0; k < sparseArray.size(); ++k) {
            recycler.recycleView((View) sparseArray.valueAt(k));
        }
        int childCount = getChildCount();
        int bottom = 0;
        if (childCount > 0) {
            bottom = getChildAt(-1 + getChildCount()).getBottom();
        }

        mLastVisiblePosition = mFirstVisiblePosition + childCount - 1;

        return bottom;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    public int findFirstVisibleItemPosition() {
        return mFirstVisiblePosition;
    }

    public int getmLastVisiblePosition() {
        return mLastVisiblePosition;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    public AspectRatioLayoutSizeCalculator getSizeCalculator() {
        return mSizeCalculator;
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter adapter, RecyclerView.Adapter adapter2) {
        removeAllViews();
        mSizeCalculator.reset();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }

        if (state.isPreLayout()) {
            return;
        }

        LogUtil.w(TAG, "onLayoutChildren");

        mSizeCalculator.setContentWidth(getContentWidth());
        mSizeCalculator.reset();
        int decoratedTop;
        if (getChildCount() == 0) {
            mFirstVisiblePosition = 0;
            mFirstVisibleRow = 0;
            decoratedTop = 0;
        } else {
            View child = getChildAt(0);
            if (mForceClearOffsets) {
                mForceClearOffsets = false;
                decoratedTop = 0;
            } else {
                //代替getTop()获取子视图的 top 边缘
                decoratedTop = getDecoratedTop(child);
            }
        }
        //解绑之前所有绑定在一起的view进recycle bin
        detachAndScrapAttachedViews(recycler);
        preFillGrid(Direction.NONE, 0, decoratedTop, recycler, state);
    }

    @Override
    public void scrollToPosition(int n) {
        LogUtil.i(TAG, "scrollToPosition n :  " + n);

        if (n >= getItemCount()) {
            Log.w(AspectRatioLayoutManager.TAG, String.format("Cannot scroll to %d, item count is %d", n, getItemCount()));
            return;
        }
        mForceClearOffsets = true;
        mFirstVisibleRow = mSizeCalculator.getRowForChildPosition(n);
        mFirstVisiblePosition = mSizeCalculator.getFirstChildPositionForRow(mFirstVisibleRow);
        requestLayout();
    }

    /**
     * Scroll vertically by dy pixels in screen coordinates and return the distance traveled.
     * The default implementation does nothing and returns 0.
     *
     * @param dy       distance to scroll in pixels. Y increases as scroll position
     *                 approaches the bottom.
     * @param recycler Recycler to use for fetching potentially cached views for a
     *                 position
     * @param state    Transient state of RecyclerView
     * @return The actual distance scrolled. The return value will be negative if dy was
     * negative and scrolling proceeeded in that direction.
     * <code>Math.abs(result)</code> may be less than dy if a boundary was reached.
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || dy == 0) {
            return 0;
        }
//        LogUtil.w(TAG, "scrollVerticallyBy");
        View child = getChildAt(0);
        View child2 = getChildAt(-1 + getChildCount());
        int n2 = getContentHeight();
        if (dy > 0) {
            boolean b;
            if (1 + mFirstVisiblePosition + getChildCount() >= getItemCount()) {
                b = true;
            } else {
                b = false;
            }
            if (b && n2 <= getContentHeight()) {
                preFillGrid(Direction.DOWN, Math.abs(dy), 0, recycler, state);
                n2 = getDecoratedBottom(getChildAt(-1 + getChildCount())) - getContentHeight();
            } else if (getDecoratedBottom(child) - dy <= 0) {
                ++mFirstVisibleRow;
                n2 = preFillGrid(Direction.DOWN, Math.abs(dy), 0, recycler, state);
            } else if (getDecoratedBottom(child2) - dy < getContentHeight()) {
                n2 = preFillGrid(Direction.DOWN, Math.abs(dy), 0, recycler, state);
            }
        } else if (mFirstVisibleRow == 0 && getDecoratedTop(child) - dy >= 0) {
            n2 = -getDecoratedTop(child);
        } else if (getDecoratedTop(child) - dy >= 0) {
            --mFirstVisibleRow;
            n2 = preFillGrid(Direction.UP, Math.abs(dy), 0, recycler, state);
        } else if (getDecoratedTop(child2) - dy > getContentHeight()) {
            n2 = preFillGrid(Direction.UP, Math.abs(dy), 0, recycler, state);
        }
        int n3;
        if (Math.abs(dy) > n2) {
            n3 = n2 * (int) Math.signum(dy);
        } else {
            n3 = dy;
        }
        offsetChildrenVertical(-n3);
        return n3;
    }

    public void setMaxRowHeight(int maxRowHeight) {
        mSizeCalculator.setMaxRowHeight(maxRowHeight);
    }

    private enum Direction {
        DOWN,
        NONE,
        UP
    }
}
