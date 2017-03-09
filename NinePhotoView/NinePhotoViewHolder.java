package com.idtk.androiddemo.widget;

import android.view.View;

/**
 * Created by Idtk on 2017/3/8.
 */

public class NinePhotoViewHolder {
    private boolean flag = false;
    private View itemView;

    public NinePhotoViewHolder(View itemView) {
        if (itemView == null) {
            throw new IllegalArgumentException("itemView may not be null");
        }
        this.itemView = itemView;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }
}
