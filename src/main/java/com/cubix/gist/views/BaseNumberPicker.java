package com.cubix.gist.views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.cubix.gist.GistApplication;
import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;
import com.cubix.gist.utils.GistConstants;
import com.cubix.gist.utils.GistFontUtils;
import com.cubix.gist.utils.GistUtils;

import java.lang.reflect.Field;


/**
 * Created by khawar on 5/30/16.
 */
public class BaseNumberPicker extends NumberPicker {

    private SyncResourceManager syncResourceManager;

    public BaseNumberPicker(Context context) {
        super(context);
    }

    public BaseNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(21)
    @SuppressLint("NewApi")
    public BaseNumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, GistApplication gistApplication) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);
                String dividerColor = mTypedArray.getString(R.styleable.baseView_cbx_dividerColor);
                String textColor = mTypedArray.getString(R.styleable.baseView_cbx_textColor);
                String textSize = mTypedArray.getString(R.styleable.baseView_cbx_textSize);
                String fontType = mTypedArray.getString(R.styleable.baseView_cbx_customTypeface);

                setBackgroundColor(bgColor);
                setDividerColor(dividerColor);
                setTextColorAndTypeFace(textColor, textSize, fontType);

            } catch (Exception e) {

                e.printStackTrace();

            } finally {

                mTypedArray.recycle();
            }
        }
    }

    public void setBackgroundColor(String bgColor) {

        if (bgColor != null && !bgColor.isEmpty() && syncResourceManager != null) {
            setBackgroundColor(SyncResourceManager.getColor(bgColor));
        }
    }

    public void setDividerColor(String dividerColor) {

        if (dividerColor != null && !dividerColor.isEmpty() && syncResourceManager != null) {

            ShapeDrawable shapeDrawable = new ShapeDrawable();
            shapeDrawable.getPaint().setColor(SyncResourceManager.getColor(dividerColor));

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {

                if (pf.getName().equals("mSelectionDivider")) {

                    try {

                        //pf.set(picker, getResources().getColor(R.color.my_orange));
                        pf.setAccessible(true);
                        pf.set(this, shapeDrawable);

                    } catch (IllegalArgumentException e) {

                        e.printStackTrace();

                    } catch (Resources.NotFoundException e) {

                        e.printStackTrace();

                    } catch (IllegalAccessException e) {

                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public void setTextColorAndTypeFace(String textColor, String textSize, String fontType) {

        if ((textColor != null && !textColor.isEmpty()) || (textSize != null && !textSize.isEmpty()) || (fontType != null && !fontType.isEmpty())) {

            final int count = this.getChildCount();
            for(int i = 0; i < count; i++) {

                View child = this.getChildAt(i);
                if(child instanceof EditText) {

                    try {

                        Field selectorWheelPaintField = NumberPicker.class.getDeclaredField("mSelectorWheelPaint");
                        selectorWheelPaintField.setAccessible(true);
                        Paint paint = (Paint)selectorWheelPaintField.get(this);
                        setPaintProperties(paint, textColor, textSize, fontType);
                        EditText editText = (EditText)child;
                        setEditTextProperties(editText, textColor, textSize, fontType);
                        editText.setFocusable(false);
                        this.invalidate();

                    } catch(NoSuchFieldException e) {

                        GistUtils.Log(GistConstants.LOG_W, GistConstants.TAG, "setNumberPickerTextColor: " + e);

                    } catch(IllegalAccessException e) {

                        GistUtils.Log(GistConstants.LOG_W, GistConstants.TAG, "setNumberPickerTextColor: " + e);

                    } catch(IllegalArgumentException e) {

                        GistUtils.Log(GistConstants.LOG_W, GistConstants.TAG, "setNumberPickerTextColor: " + e);
                    }
                }
            }
        }
    }

    public void setTextColor(String textColor, String textSize) {

        if ((textColor != null && !textColor.isEmpty()) || (textSize != null && !textSize.isEmpty())) {

            final int count = this.getChildCount();
            for(int i = 0; i < count; i++) {

                View child = this.getChildAt(i);
                if(child instanceof EditText) {

                    try {

                        Field selectorWheelPaintField = NumberPicker.class.getDeclaredField("mSelectorWheelPaint");
                        selectorWheelPaintField.setAccessible(true);
                        Paint paint = (Paint)selectorWheelPaintField.get(this);
                        setPaintProperties(paint, textColor, textSize, "");
                        EditText editText = (EditText)child;
                        setEditTextProperties(editText, textColor, textSize, "");
                        editText.setFocusable(false);
                        this.invalidate();

                    } catch(NoSuchFieldException e) {

                        GistUtils.Log(GistConstants.LOG_W, GistConstants.TAG, "setNumberPickerTextColor: " + e);

                    } catch(IllegalAccessException e) {

                        GistUtils.Log(GistConstants.LOG_W, GistConstants.TAG, "setNumberPickerTextColor: " + e);

                    } catch(IllegalArgumentException e) {

                        GistUtils.Log(GistConstants.LOG_W, GistConstants.TAG, "setNumberPickerTextColor: " + e);
                    }
                }
            }
        }
    }

    private void setPaintProperties(Paint paint, String textColor, String textSize, String fontType) {

        if (paint != null && syncResourceManager != null) {

            if (textColor != null && !textColor.isEmpty()) {

                paint.setColor(SyncResourceManager.getColor(textColor));
            }

            if (textSize != null && !textSize.isEmpty()) {

                float size = SyncResourceManager.getFontSize(textSize, TypedValue.COMPLEX_UNIT_SP);
                paint.setTextSize(size);
            }

            if (fontType != null && !fontType.isEmpty()) {

                Typeface typeface = GistFontUtils.setFontFace(getContext(), SyncResourceManager.getFont(fontType));
                paint.setTypeface(typeface);
            }

        }
    }

    private void setEditTextProperties(EditText editText, String textColor, String textSize, String fontType) {

        if (editText != null && syncResourceManager != null) {

            if (textColor != null && !textColor.isEmpty()) {

                editText.setTextColor(SyncResourceManager.getColor(textColor));
            }

            if (textSize != null && !textSize.isEmpty()) {

                float size = SyncResourceManager.getFontSize(textSize);
                editText.setTextSize(size);
            }

            if (fontType != null && !fontType.isEmpty()) {

                Typeface typeface = GistFontUtils.setFontFace(getContext(), SyncResourceManager.getFont(fontType));
                editText.setTypeface(typeface);
            }
        }
    }
}