## 效果图
  ![](https://github.com/asdzheng/SuitedRecyclerView/blob/master/screenshot.png)

## 最基本的用法
- RecyclerView 的设置

        mPhotosAdapter = new PhotosAdapter(list, this);
        recyclerView.setAdapter(mPhotosAdapter);
        //自定义LayoutManager
        SuitedLayoutManager layoutManager = new SuitedLayoutManager(mPhotosAdapter);
        recyclerView.setLayoutManager(layoutManager);
        //设置最大的图片显示高度，默认为600px
        layoutManager.setMaxRowHeight(getResources().getDisplayMetrics().heightPixels / 3);
        //设置Item之间的空隙
        recyclerView.addItemDecoration(new SuitedItemDecoration(DisplayUtils.dpToPx(4.0f, this)));

- PhotosAdapter需要实现SizeCaculator.SizeCalculatorDelegate接口里的aspectRatioForIndex(int position)方法，返回图片宽高比
        
    
        @Override
        public double aspectRatioForIndex(int position) {
          if (position < getItemCount()) {
              PhotoInfo info = mPhotos.get(position);
              //如果你的图片url是以_w750_h750.jpg这样的格式结尾,可以用SuitUrlUtil这个工具类获取它的宽高比
              double ratio = SuitUrlUtil.getAspectRadioFromUrl(info.photo);
              return ratio;
          }
          return 1.0;
        } 

##内存的优化使用

由于这个LayoutManager会根据自身宽高和相邻图片宽高的比率最后计算出每张图片的大小，所以每张图片大小几乎都不一样，recyclerView里View的复用情况很少，这种情况下如果不对图片进行等比缩小和对滑动做优化，将会造成严重的卡顿。

在我个人实践的过程中呢，有两处优化的点可以让图片滑动时更为流畅。

- 在ImageView确定Width和Height后，再进行网络请求，具体可参考library里的SuitImageView的做法

          @Override
          protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            if(w != 0 && h != 0) {
                Picasso.with(getContext()).load(mPhoto).tag(getContext()).resize(w,h).into(this);
          }

- 滑动过程中，中断图片请求，以使用Picasso框架请求图片为例：

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Picasso.with(PhotoActivity.this).resumeTag(PhotoActivity.this);
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Picasso.with(PhotoActivity.this).pauseTag(PhotoActivity.this);
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    Picasso.with(PhotoActivity.this).pauseTag(PhotoActivity.this);
                }
            }
        });
        
##Demo的滑动效果

![](https://github.com/asdzheng/SuitedRecyclerView/blob/master/record.gif)


下拉刷新上拉加载更多框架修改自[WaveSwipeRefreshLayout](https://github.com/fishCoder/WaveSwipeRefreshLayout)开源库


##License Apache 2.0

        
  


  
  
  
  
