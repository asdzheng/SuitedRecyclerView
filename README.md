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


- PhotosAdapter需要继承SizeCaculator.SizeCalculatorDelegate，实现aspectRatioForIndex(int position)方法，返回图片宽高比
        
      @Override
      public double aspectRatioForIndex(int position) {
          if (position < getItemCount()) {
              PhotoInfo info = mPhotos.get(position);
              double ratio = SuitStringUtil.getAspectRadioFromUrl(info.photo);//如果你的图片url是以_w750_h750.jpg这样的格式结尾
              return ratio;
          }
          return 1.0;
      }
  

## 效果图
  
  
  
  
  
