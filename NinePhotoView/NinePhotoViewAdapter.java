package com.idtk.NinePhotoView.widget;

import android.view.ViewGroup;

import java.util.Observable;

/**
 * Created by Idtk on 2017/3/8.
 */

public abstract class NinePhotoViewAdapter<T extends NinePhotoViewHolder> extends Observable {

    public NinePhotoViewAdapter() {
        super();
    }

    public int getItemCount(){
        return 0;
    }

    public abstract T createView(ViewGroup parent);

    public abstract void displayView(T holder, int position);


    public void notifyChanged(){
        setChanged();
        notifyObservers();
    }

}