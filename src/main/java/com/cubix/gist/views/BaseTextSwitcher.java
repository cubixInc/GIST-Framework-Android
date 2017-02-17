package com.cubix.gist.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextSwitcher;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;

/**
 * Created by khawar on 5/30/16.
 */
public class BaseTextSwitcher extends TextSwitcher {

    private SyncResourceManager syncResourceManager;

    public BaseTextSwitcher(Context context) {
        super(context);
    }

    public BaseTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseTextSwitcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);

                setBackgroundColor(bgColor);

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
}