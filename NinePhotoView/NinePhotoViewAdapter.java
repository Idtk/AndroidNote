package com.idtk.androiddemo.widget;

import android.view.View;

import java.util.Observable;

/**
 * Created by Idtk on 2017/3/8.
 */

public abstract class NinePhotoViewAdapter extends Observable{

    public NinePhotoViewAdapter() {
        super();
    }

    public int getItemCount(){
      return 0;
    }
	
	/**
	 * 建议增加此设置imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	 */
    public abstract View createView();

    public abstract void displayView(View childView, int position);

    public void notifyChanged(){
        setChanged();
        notifyObservers();
    }


}
