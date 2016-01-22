package com.asdzheng.suitedrecyclerview.utils.recyclerview;


import com.asdzheng.suitedrecyclerview.utils.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 纵横比计算工具类
 * Created by asdzheng on 2015/12/26.
 */
public class AspectRatioLayoutSizeCalculator {

    private static final int DEFAULT_MAX_ROW_HEIGHT = 600;
    private static final int INVALID_CONTENT_WIDTH = -1;
    private static final String TAG;
    private static int mMaxRowHeight;
    private int mContentWidth;
    //每一行第一个可见的View的position
    private List<Integer> mFirstChildPositionForRow;
    //记录每个child在哪一行
    private List<Integer> mRowForChildPosition;
    //view的横纵比
    private SizeCalculatorDelegate mSizeCalculatorDelegate;
    //记录每个childView的宽高
    private List<Size> mSizeForChildAtPosition;

    static {
        TAG = AspectRatioLayoutSizeCalculator.class.getName();
        //每个view的最大高度
        AspectRatioLayoutSizeCalculator.mMaxRowHeight = 600;
    }


    public AspectRatioLayoutSizeCalculator(SizeCalculatorDelegate mSizeCalculatorDelegate) {
        mContentWidth = -1;
        this.mSizeCalculatorDelegate = mSizeCalculatorDelegate;
        mSizeForChildAtPosition = new ArrayList<Size>();
        mFirstChildPositionForRow = new ArrayList<Integer>();
        mRowForChildPosition = new ArrayList<Integer>();
    }

    private void computeFirstChildPositionsUpToRow(int row) {
        while (row >= mFirstChildPositionForRow.size()) {
            LogUtil.i(TAG, "row = " + row + " | mSizeForChildAtPosition.size()" + mSizeForChildAtPosition.size());

            computeChildSizesUpToPosition( mSizeForChildAtPosition.size());
        }
    }

    public void computeChildSizesUpToPosition(int n) {
        if (mContentWidth == -1) {
            throw new RuntimeException("Invalid content width. Did you forget to set it?");
        }
        if (mSizeCalculatorDelegate == null) {
            throw new RuntimeException("Size calculator delegate is missing. Did you forget to set it?");
        }
        //已经计算过的子View的size
        int haveComputeChildSize = mSizeForChildAtPosition.size();

        int firstPositionNotRow;
        if (mRowForChildPosition.size() > 0) {
            firstPositionNotRow = 1 + mRowForChildPosition.get(-1 + mRowForChildPosition.size());
        } else {
            firstPositionNotRow = 0;
        }
        //横纵比
        double aspectRatioWidth = 0.0;

        int rowHeight = Integer.MAX_VALUE;
        ArrayList<Double> list = new ArrayList<Double>();

        while ( rowHeight > AspectRatioLayoutSizeCalculator.mMaxRowHeight) {
            double aspectRatioForIndex = mSizeCalculatorDelegate.aspectRatioForIndex(haveComputeChildSize);

            aspectRatioWidth = aspectRatioWidth + aspectRatioForIndex;

            list.add(aspectRatioForIndex);

            rowHeight = (int)Math.ceil(mContentWidth / aspectRatioWidth);
            if (rowHeight <= mMaxRowHeight) {
                mFirstChildPositionForRow.add(1 + (haveComputeChildSize - list.size()));
                int leftContentWidth = this.mContentWidth;
                Iterator<Double> iterator = list.iterator();
                while (iterator.hasNext()) {
                    //取剩余的宽度和原本缩小的宽度较小值
                    int min = Math.min(leftContentWidth, (int)Math.ceil(rowHeight * iterator.next()));
                    mSizeForChildAtPosition.add(new Size(min, rowHeight));
                    mRowForChildPosition.add(firstPositionNotRow);
                    leftContentWidth = leftContentWidth - min;
                }
                list.clear();
                aspectRatioWidth = 0.0;
                ++firstPositionNotRow;
            }
            haveComputeChildSize ++;
        }
    }

    /**
     * 得到某一列第一个View的position
     * @param row
     * @return
     */
    int getFirstChildPositionForRow(int row) {
        //如果list里没有row行的数据，就需要重新计算
        if (row >= mFirstChildPositionForRow.size()) {
            computeFirstChildPositionsUpToRow(row);
        }
        return mFirstChildPositionForRow.get(row);
    }

    int getRowForChildPosition(int n) {
        if (n >= mRowForChildPosition.size()) {
            computeChildSizesUpToPosition(n);
        }
        return mRowForChildPosition.get(n);
    }

    void reset() {
        mSizeForChildAtPosition.clear();
        mFirstChildPositionForRow.clear();
        mRowForChildPosition.clear();
    }

    public void setContentWidth(int mContentWidth) {
        if (this.mContentWidth != mContentWidth) {
            this.mContentWidth = mContentWidth;
            reset();
        }
    }

    public void setMaxRowHeight(int mMaxRowHeight) {
        if (this.mMaxRowHeight != mMaxRowHeight) {
            this.mMaxRowHeight = mMaxRowHeight;
            reset();
        }
    }

    Size sizeForChildAtPosition(int n) {
        if (n >= mSizeForChildAtPosition.size()) {
            computeChildSizesUpToPosition(n);
        }
        return mSizeForChildAtPosition.get(n);
    }

    public interface SizeCalculatorDelegate
    {
        double aspectRatioForIndex(int p0);
    }
}
