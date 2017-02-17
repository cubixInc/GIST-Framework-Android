package com.cubix.gist.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.ListViewCompat;
import android.util.AttributeSet;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;


/**
 * Created by mehran.khan on 6/14/2016.
 */
public class BaseListView extends ListViewCompat {

    private SyncResourceManager syncResourceManager;

    public BaseListView(Context context) {
        super(context);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);
                String dividerColor = mTypedArray.getString(R.styleable.baseView_cbx_dividerColor);
                float dividerHeight = mTypedArray.getDimension(R.styleable.baseView_cbx_dividerHeight, 0);

                setBackgroundColor(bgColor);
                setDivider(dividerColor);
                setBaseDividerHeight(dividerHeight);

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

    public void setDivider(String dividerColor){

        if (dividerColor != null && !dividerColor.isEmpty() && syncResourceManager != null) {

            setDivider(new ColorDrawable(SyncResourceManager.getColor(dividerColor)));
        }
    }

    public void setBaseDividerHeight(float dividerHeight) {

        setDividerHeight((int) dividerHeight);
    }
}
