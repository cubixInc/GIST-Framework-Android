package com.cubix.gist.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;


/**
 * Created by khawar on 5/30/16.
 */
public class BaseProgressBar extends ProgressBar {

    private SyncResourceManager syncResourceManager;

    public BaseProgressBar(Context context) {
        super(context);
    }

    public BaseProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);
                int seekbarMax = mTypedArray.getInt(R.styleable.baseView_cbx_seekbarMax, 100);
                int seekbarProgress = mTypedArray.getInt(R.styleable.baseView_cbx_seekbarProgress, 0);
                String seekbarProgressBarColor = mTypedArray.getString(R.styleable.baseView_cbx_seekbarProgressBarColor);
                String seekbarSecondaryProgressColor = mTypedArray.getString(R.styleable.baseView_cbx_seekbarSecondaryProgressBarColor);

                setBaseMax(seekbarMax);
                setBaseProgress(seekbarProgress);
                setProgressBarColor(seekbarProgressBarColor);  // set ProgressBar color
                setBackgroundColor(bgColor);
                setBaseSecondaryProgress(seekbarSecondaryProgressColor);

            } catch(Exception e) {

                e.printStackTrace();

            } finally {

                mTypedArray.recycle();
            }
        }
    }

    public void setBackgroundColor(String bgColor){

        if (bgColor != null && !bgColor.isEmpty() && syncResourceManager != null) {
            setBackgroundColor(SyncResourceManager.getColor(bgColor));
        }
    }

    public void setBaseProgress(int seekbarProgress) {

        setProgress(seekbarProgress);
    }

    public void setBaseMax(int seekbarMax) {

        setMax(seekbarMax);
    }

    public void setProgressBarColor(String seekbarProgressBarColor) {

        if (seekbarProgressBarColor != null && !seekbarProgressBarColor.isEmpty() && syncResourceManager != null) {

            getProgressDrawable().setColorFilter(SyncResourceManager.getColor(seekbarProgressBarColor), PorterDuff.Mode.MULTIPLY);
        }
    }

    public void setBaseSecondaryProgress(String seekbarSecondaryProgressColor) {

        if (seekbarSecondaryProgressColor != null && !seekbarSecondaryProgressColor.isEmpty() && syncResourceManager != null) {

            setSecondaryProgress(SyncResourceManager.getColor(seekbarSecondaryProgressColor));
        }
    }
}