package com.cubix.gist.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;

/**
 * Created by khawar on 5/30/16.
 */
public class BaseSeekBar extends AppCompatSeekBar {

    private SyncResourceManager syncResourceManager;

    public BaseSeekBar(Context context) {
        super(context);
    }

    public BaseSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);
                String seekbarThumbColor = mTypedArray.getString(R.styleable.baseView_cbx_seekbarThumbColor);
                String seekbarProgressBarColor = mTypedArray.getString(R.styleable.baseView_cbx_seekbarProgressBarColor);
                int seekbarMax = mTypedArray.getInt(R.styleable.baseView_cbx_seekbarMax, 100);
                int seekbarProgress = mTypedArray.getInt(R.styleable.baseView_cbx_seekbarProgress, 0);
                int seekbarSecProgress = mTypedArray.getInt(R.styleable.baseView_cbx_seekbarSecProgress, 0);
                int seekbarThumbWidth = mTypedArray.getInt(R.styleable.baseView_cbx_seekbarThumbWidth, 30);
                int seekbarThumbHeight = mTypedArray.getInt(R.styleable.baseView_cbx_seekbarThumbHeight, 30);

                setBackgroundColor(bgColor);
                setBaseMax(seekbarMax);
                setBaseProgress(seekbarProgress);
                setBaseSecondaryProgress(seekbarSecProgress);

                // set thumb views
                setThumbView(seekbarThumbColor,seekbarThumbWidth,seekbarThumbHeight);

                // set ProgressBar color
                setProgressBarColor(seekbarProgressBarColor);

            } catch(Exception e) {

                e.printStackTrace();

            } finally {

                mTypedArray.recycle();
            }
        }
    }

    public void setProgressBarColor(String seekbarProgressBarColor) {

        if (seekbarProgressBarColor != null && !seekbarProgressBarColor.isEmpty() && syncResourceManager != null) {

            getProgressDrawable().setColorFilter(SyncResourceManager.getColor(seekbarProgressBarColor), PorterDuff.Mode.MULTIPLY);
        }
    }

    public void setThumbView(String seekbarThumbColor, int seekbarThumbWidth, int seekbarThumbHeight) {

        if (seekbarThumbColor != null && !seekbarThumbColor.isEmpty() && syncResourceManager != null) {

            ShapeDrawable thumb = new ShapeDrawable(new OvalShape());
            thumb.setIntrinsicWidth(seekbarThumbWidth);
            thumb.setIntrinsicHeight(seekbarThumbHeight);

            thumb.getPaint().setColor(SyncResourceManager.getColor(seekbarThumbColor));

            setThumb(thumb);
        }
    }

    public void setBaseSecondaryProgress(int seekbarSecProgress) {

        setSecondaryProgress(seekbarSecProgress);
    }

    public void setBaseProgress(int seekbarProgress) {

        setProgress(seekbarProgress);
    }

    public void setBaseMax(int seekbarMax) {

        setMax(seekbarMax);
    }

    public void setBackgroundColor(String bgColor){

        if (bgColor != null && !bgColor.isEmpty() && syncResourceManager != null) {
            setBackgroundColor(SyncResourceManager.getColor(bgColor));
        }
    }

    @Override
    protected synchronized void onDraw(Canvas c) {
        super.onDraw(c);


        /*String text = Integer.toString(getProgress());

        Paint p = new Paint();

        p.setTypeface(Typeface.DEFAULT_BOLD);

        p.setTextSize(40);

        p.setColor(Color.parseColor("#000000"));

        int width = (int) p.measureText(text);

        int yPos = (int) ((c.getHeight() / 2) - ((p.descent() + p

                .ascent()) / 2));

        c.drawText(text, (getThumb().getIntrinsicWidth() - width) / 2, yPos, p);*/
    }
}