package com.cubix.gist.views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;
import com.cubix.gist.utils.GistFontUtils;

/**
 * Created by imran.pyarali on 6/6/2016.
 */
public class BaseCheckedTextView extends AppCompatCheckedTextView {

    private SyncResourceManager syncResourceManager;

    public BaseCheckedTextView(Context context) {
        super(context);
    }

    public BaseCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(21)
    @SuppressLint("NewApi")
    public BaseCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);
                String textColor = mTypedArray.getString(R.styleable.baseView_cbx_textColor);
                String hintColor = mTypedArray.getString(R.styleable.baseView_cbx_hintColor);
                String text = mTypedArray.getString(R.styleable.baseView_cbx_text);
                String hint = mTypedArray.getString(R.styleable.baseView_cbx_hint);
                String textSize = mTypedArray.getString(R.styleable.baseView_cbx_textSize);
                String fontType = mTypedArray.getString(R.styleable.baseView_cbx_customTypeface);
                String strokeColor = mTypedArray.getString(R.styleable.baseView_cbx_strokeColor);
                String solidColor = mTypedArray.getString(R.styleable.baseView_cbx_solidColor);
                float strokeWidth = mTypedArray.getDimension(R.styleable.baseView_cbx_strokeWidth, 0);

                setBackgroundColor(bgColor);
                setTextColor(textColor);
                setHintTextColor(hintColor);
                setBaseText(text);
                setBaseHint(hint);
                setTypeface(getContext(), fontType);
                setTextSize(textSize);
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

    public void setTextColor(String textColor){

        if (textColor != null && !textColor.isEmpty() && syncResourceManager != null) {
            setTextColor(SyncResourceManager.getColor(textColor));
        }
    }

    public void setHintTextColor(String hintColor){

        if (hintColor != null && !hintColor.isEmpty() && syncResourceManager != null) {
            setHintTextColor(SyncResourceManager.getColor(hintColor));
        }
    }

    public void setBaseText(String text) {

        if (text != null && !text.isEmpty()) {

            text = syncResourceManager != null ? SyncResourceManager.getString(text) : text;
            if (text.contains("\\n")) {

                text = text.replace("\\n", "\n");
            }

            setText(text);
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

    public void setTypeface(Context context, String fontType) {

        if (fontType != null && !fontType.isEmpty() && syncResourceManager != null) {

            Typeface typeface = GistFontUtils.setFontFace(context, SyncResourceManager.getFont(fontType));
            setTypeface(typeface);
        }
    }

    public void setTextSize(String textSize) {

        if (textSize != null && !textSize.isEmpty() && syncResourceManager != null) {

            float size = SyncResourceManager.getFontSize(textSize);
            setTextSize(size);
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
