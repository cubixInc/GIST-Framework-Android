package com.cubix.gist.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;
import com.cubix.gist.utils.GistFontUtils;

/**
 * Created by khawar on 5/30/16.
 */
public class BaseTextInputLayout extends TextInputLayout {

    private SyncResourceManager syncResourceManager;

    public BaseTextInputLayout(Context context) {
        super(context);
    }

    public BaseTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);
                String hint = mTypedArray.getString(R.styleable.baseView_cbx_hint);
                String error = mTypedArray.getString(R.styleable.baseView_cbx_error);
                String fontType = mTypedArray.getString(R.styleable.baseView_cbx_customTypeface);
                boolean counterEnable = mTypedArray.getBoolean(R.styleable.baseView_cbx_charCounterEnabled, isCounterEnabled());
                int counterMaxLength = mTypedArray.getInt(R.styleable.baseView_cbx_charCounterMaxLength,  getCounterMaxLength());
                String strokeColor = mTypedArray.getString(R.styleable.baseView_cbx_strokeColor);
                String solidColor = mTypedArray.getString(R.styleable.baseView_cbx_solidColor);
                float strokeWidth = mTypedArray.getDimension(R.styleable.baseView_cbx_strokeWidth, 0);

                setBackgroundColor(bgColor);
                setBaseHint(hint);
                setBaseError(error);
                setTypeface(getContext(), fontType);
                setBaseCounterEnable(counterEnable);
                setBaseCounterMaxLength(counterMaxLength);
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

    public void setBaseHint(String hint) {

        if (hint != null && !hint.isEmpty()) {

            hint = syncResourceManager != null ? SyncResourceManager.getString(hint) : hint;
            if (hint.contains("\\n")) {

                hint = hint.replace("\\n", "\n");
            }

            setHint(hint);
        }
    }

    public void setBaseError(String error) {

        if (error != null && !error.isEmpty()) {

            error = syncResourceManager != null ? SyncResourceManager.getString(error) : error;
            if (error.contains("\\n")) {

                error = error.replace("\\n", "\n");
            }

            setError(error);
        }
    }

    public void setBaseCounterEnable(boolean counterEnable){

        setCounterEnabled(counterEnable);
    }

    public void setBaseCounterMaxLength(int counterMaxLength){

        setCounterMaxLength(counterMaxLength);
    }

    public void setTypeface(Context context, String fontType) {

        if (fontType != null && !fontType.isEmpty() && syncResourceManager != null) {

            Typeface typeface = GistFontUtils.setFontFace(context, SyncResourceManager.getFont(fontType));
            setTypeface(typeface);
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