package com.cubix.gist.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;

/**
 * Created by imran.pyarali on 6/14/2016.
 */
public class BaseCardView extends CardView {

    private SyncResourceManager syncResourceManager;

    public BaseCardView(Context context) {
        super(context);
    }

    public BaseCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
