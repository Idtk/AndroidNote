package com.idtk.androiddemo.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Idtk on 2017/3/8.
 */

public class NinePhotoView extends FrameLayout implements Observer{

    private NinePhotoViewAdapter adapter;
    private int border = 5;
    private int childSize;

    public NinePhotoView(@NonNull Context context) {
        super(context);
    }

    public NinePhotoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NinePhotoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NinePhotoView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        generateDefaultLayoutParams();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ninePhotoMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ninePhotoCreateView();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        childLayout(left, top, right, bottom);
    }

    private void ninePhotoCreateView(){
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.createView() == null) {
                return;
            }
            addView(adapter.createView(),generateDefaultLayoutParams());
        }
    }

    private void ninePhotoMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec)-getPaddingLeft()-getPaddingRight();
        int height;

        if (adapter.getItemCount() < 0 || adapter.getItemCount() > 9) {
            throw new IllegalStateException("此参数不可以 小于0 or 大于9");
        }
        if (adapter.getItemCount() == 0) {
            setMeasuredDimension(0, 0);
        }
//        if (adapter.getItemCount() > 0) {
//            childSize = (width - border * 2) / 3;
//            if (adapter.getItemCount() < 4) {
//                setMeasuredDimension(childSize * adapter.getItemCount() + border * (
//                        adapter.getItemCount() - 1), childSize);
//            } else if (adapter.getItemCount() == 4) {
//                setMeasuredDimension(childSize * 2 + border, childSize * 2 + border);
//            } else {
//                setMeasuredDimension(childSize * 3 + border * 2, (int) (childSize *
//                        Math.ceil(adapter.getItemCount() / 3) + border * Math.ceil(adapter.getItemCount() / 3 - 1)));
//            }
//        }
        if (adapter.getItemCount() > 1) {
            childSize = (width - border * 2) / 3;
            height = (int) (childSize * (int) Math.ceil(adapter.getItemCount() / 3.0) + border * (int) Math.ceil(adapter.getItemCount() / 3.0 - 1));
            setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), height + getPaddingTop() + getPaddingBottom());
            Log.d("size",width+":"+height+":"+childSize+":"+Math.ceil(adapter.getItemCount() / 3.0));
        } else {
            childSize = width;
            height = width;
            setMeasuredDimension(width + getPaddingLeft() + getPaddingRight(), height + getPaddingTop() + getPaddingBottom());
        }
    }

    private void childLayout(int left, int top, int right, int bottom) {
        if (adapter.getItemCount() < 0 || adapter.getItemCount() > 9) {
            throw new IllegalStateException("此参数不可以 小于0 or 大于9");
        }
        int count = adapter.getItemCount();
        int colNum = 3;
        if (count == 4){
            colNum = 2;
        }

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (adapter != null && childView != null) {
                adapter.displayView(childView, i);
            }

            int rows = i / colNum;
            int cols = i % colNum;

            int childLeft = left + getPaddingLeft() + (childSize + border) * (cols);
            int childTop = top + getPaddingTop() + (childSize + border) * (rows);
            int childRight = childLeft + childSize;
            int childBottom = childTop + childSize;
            Log.d("layout",i + ":" + childLeft + ":" + childTop + ":" + childRight + ":" + childBottom);
            childView.layout(childLeft, childTop, childRight, childBottom);
        }
    }

    public void setAdapter(NinePhotoViewAdapter adapter){
        this.adapter = adapter;
//        for (int i = 0; i < adapter.getItemCount(); i++) {
//            if (adapter.createView() == null) {
//                return;
//            }
//            addView(adapter.createView(),generateDefaultLayoutParams());
//        }
    }

    public void setBorder(int border){
        this.border = border;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof NinePhotoViewAdapter){
            this.adapter = (NinePhotoViewAdapter) o;
            requestLayout();
            invalidate();
        }
    }
}
