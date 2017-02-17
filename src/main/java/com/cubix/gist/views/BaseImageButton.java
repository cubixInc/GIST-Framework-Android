package com.cubix.gist.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;


/**
 * Created by khawar on 5/30/16.
 */
public class BaseImageButton extends AppCompatImageButton {

    private SyncResourceManager syncResourceManager;

    public BaseImageButton(Context context) {
        super(context);
    }

    public BaseImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);
                String strokeColor = mTypedArray.getString(R.styleable.baseView_cbx_strokeColor);
                String solidColor = mTypedArray.getString(R.styleable.baseView_cbx_solidColor);
                float strokeWidth = mTypedArray.getDimension(R.styleable.baseView_cbx_strokeWidth, 0);

                setBackgroundColor(bgColor);
                setBaseBackgroundDrawable(solidColor, strokeColor, strokeWidth);

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

    public void setBaseBackgroundDrawable(String solidColor, String strokeColor, float strokeWidth){

        if (solidColor != null && !solidColor.isEmpty() && syncResourceManager != null) {

            Drawable background = getBackground();
            if (background instanceof GradientDrawable) {

                GradientDrawable gradientDrawable = (GradientDrawable) background;
                gradientDrawable.setColor(SyncResourceManager.getColor(solidColor));

                if (strokeColor != null && !strokeColor.isEmpty()) {

                    gradientDrawable.setStroke((int)strokeWidth, SyncResourceManager.getColor(strokeColor));

                } else {

                    gradientDrawable.setStroke((int)strokeWidth, SyncResourceManager.getColor(solidColor));
                }
            }
        }
    }
}