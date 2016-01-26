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

        LogUtil.e(TAG, "preFillGrid = getChildCount : " + getChildCount());

        if (getChildCount() != 0) {
            decoratedTop = getDecoratedTop(getChildAt(0));
//          LogUtil.w(TAG, "preFillGrid = decoratedTop : " + decoratedTop);

            if (mFirstVisiblePosition != firstChildPositionForRow) {
                switch (direction) {
                    case UP:
                        int upNewViewPosition = mFirstVisiblePosition -1 ;
                        int upNewViewHeight = mSizeCalculator.sizeForChildAtPosition(upNewViewPosition).getHeight();
                        LogUtil.i(TAG, "UP = oldTop " + decoratedTop + " | upNewViewHeight = " + upNewViewHeight + " | newTop = " + (decoratedTop - upNewViewHeight ));
                        decoratedTop = decoratedTop - upNewViewHeight;
                        break;
                    case DOWN:
                        int firstViewHeight = mSizeCalculator.sizeForChildAtPosition(mFirstVisiblePosition).getHeight();
                        LogUtil.i(TAG, "DOWN = oldTop " + decoratedTop + " | firstViewHeight = " + firstViewHeight + " | newTop = " + (decoratedTop + firstViewHeight ));
                        decoratedTop = decoratedTop + firstViewHeight;
                        break;
                }
            }

            for (int i = 0; i < getChildCount(); ++i) {
                sparseArray.put(i + mFirstVisiblePosition, getChildAt(i));
            }
            for (int j = 0; j < sparseArray.size(); ++j) {
                detachView((View) sparseArray.valueAt(j));
            }
        }
        //将新的firstChild赋给全局mFirstChild
        mFirstVisiblePosition = firstChildPositionForRow;
        int childPaddingLeft = paddingLeft;
        int childPaddingTop = decoratedTop;

        for (int i = mFirstVisiblePosition; i < state.getItemCount(); i++) {
            Size sizeForChildAtPosition = mSizeCalculator.sizeForChildAtPosition(i);
            //是否加上下一个view就超过屏幕的宽度
            if (childPaddingLeft + sizeForChildAtPosition.getWidth() > getContentWidth()) {
                childPaddingLeft = paddingLeft;
                //上一个view的坐标
                int lastPosition = i - 1;
                //childPaddingTop一直叠加每一行view的高度
                int lastViewHeight =  mSizeCalculator.sizeForChildAtPosition(lastPosition).getHeight();
                childPaddingTop = childPaddingTop + lastViewHeight;
            }

            if(direction == Direction.DOWN) {
                //是否已经到了不可见的view(不可见的view，不用测量和绘制)
                if (childPaddingTop >= dy + getContentHeight()) {
                        break;
                }
            } else {
                //是否已经到了不可见的view
                if (childPaddingTop >= getContentHeight()) {
                        break;
                }
            }

            View view = (View) sparseArray.get(i);
            if (view == null) {
                view = recycler.getViewForPosition(i);
                LogUtil.w(TAG, "view == null i = " + i + " | sizeForChildAtPosition = " + sizeForChildAtPosition);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, childPaddingLeft, childPaddingTop, childPaddingLeft + sizeForChildAtPosition.getWidth(), childPaddingTop + sizeForChildAtPosition.getHeight());
            } else {
                LogUtil.i(TAG, "view != null i = " + i + " | sizeForChildAtPosition = " + sizeForChildAtPosition);
                attachView(view);
                sparseArray.remove(i);
            }
            childPaddingLeft += sizeForChildAtPosition.getWidth();
        }

        for (int k = 0; k < sparseArray.size(); k++) {
            recycler.recycleView((View) sparseArray.valueAt(k));
        }
        int childCount = getChildCount();
        int lastVisibleViewBottom = 0;
        if (childCount > 0) {
            lastVisibleViewBottom = getChildAt(childCount - 1).getBottom();
        }

        mLastVisiblePosition = mFirstVisiblePosition + childCount - 1;

        return lastVisibleViewBottom;
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

        LogUtil.i(TAG, "onLayoutChildren");

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
    public void scrollToPosition(int position) {
//        LogUtil.i(TAG, "scrollToPosition n :  " + n);

        if (position >= getItemCount()) {
            Log.w(AspectRatioLayoutManager.TAG, String.format("Cannot scroll to %d, item count is %d", position, getItemCount()));
            return;
        }
        mForceClearOffsets = true;
        mFirstVisibleRow = mSizeCalculator.getRowForChildPosition(position);
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
        View firstChild = getChildAt(0);
        View lastChild = getChildAt(-1 + getChildCount());
        int toBottomTopDistance = getContentHeight();
        if (dy > 0) {
            //向下滑动，dy大于0
            if (mFirstVisiblePosition + getChildCount() >= getItemCount()) {
                //最后面的所有视图已经可见
//                mLastVisiblePosition = getItemCount() - 1;

//                preFillGrid(Direction.DOWN, Math.abs(dy), 0, recycler, state);
                //这时n2为实际的移动的距离
                toBottomTopDistance = getDecoratedBottom(lastChild) - getContentHeight();
//                LogUtil.w(TAG, "最后面的所有视图已经可见" + " | dy = " + dy + "  | n2 = " + toBottomTopDistance);

            } else if (getDecoratedBottom(firstChild) - dy <= 0) {
                //第一行的的View底部将移出屏幕
                mFirstVisibleRow++;
                toBottomTopDistance = preFillGrid(Direction.DOWN, Math.abs(dy), 0, recycler, state);
//                LogUtil.w(TAG, "第一行的的View底部将移出屏幕" + " | dy = " + dy + "  | n2 = " + toBottomTopDistance);

            } else if (getDecoratedBottom(lastChild) - dy < getContentHeight()) {
                toBottomTopDistance = preFillGrid(Direction.DOWN, Math.abs(dy), 0, recycler, state);
//                LogUtil.w(TAG, "最底部将出现一排新的view" + " | dy = " + dy + "  | n2 = " + toBottomTopDistance);
            }
        } else if (mFirstVisibleRow == 0 && getDecoratedTop(firstChild) - dy >= 0) {
            //第一行可见，向上滑动的距离大于从现在到顶部的距离
            toBottomTopDistance = -getDecoratedTop(firstChild);
//            LogUtil.w(TAG, "第一行可见，向上滑动的距离大于从现在到顶部的距离" + " | dy = " + dy + "  | n2 = " + toBottomTopDistance);
        } else if (getDecoratedTop(firstChild) - dy >= 0) {
            //顶部将出现一排新的view
            mFirstVisibleRow--;
            toBottomTopDistance = preFillGrid(Direction.UP, Math.abs(dy), 0, recycler, state);
//            LogUtil.w(TAG, "顶部将出现一排新的view" + " | dy = " + dy + "  | n2 = " + toBottomTopDistance);
        } else if (getDecoratedTop(lastChild) - dy > getContentHeight()) {
//            LogUtil.w(TAG, "最后一排将移出屏幕" + " | dy = " + dy + "  | n2 = " + toBottomTopDistance);
            toBottomTopDistance = preFillGrid(Direction.UP, Math.abs(dy), 0, recycler, state);
        }

        //实际移动的距离
        int actualDy;
        if (Math.abs(dy) > toBottomTopDistance) {
            //signum返回的是-1或者1，看dy的正负符号
            actualDy = toBottomTopDistance * (int) Math.signum(dy);
        } else {
            actualDy = dy;
        }

        //向下滚动，对于子view来说，它的移动方向是和滚动方向相反的，所以传入的是负数
        offsetChildrenVertical(-actualDy);
        return actualDy;
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
