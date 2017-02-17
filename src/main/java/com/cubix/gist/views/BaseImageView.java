package com.cubix.gist.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;


/**
 * Created by khawar on 5/30/16.
 */
public class BaseImageView extends AppCompatImageView {

    private SyncResourceManager syncResourceManager;
    private Path path;
    private RectF rect;
    private float cornerRadius;

    public BaseImageView(Context context) {
        super(context);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
                cornerRadius = mTypedArray.getDimension(R.styleable.baseView_cbx_cornerRadius, 0);

                setBackgroundColor(bgColor);
                setBaseBackgroundDrawable(solidColor, strokeColor, strokeWidth);
                setCornerRadius();

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

    private void setCornerRadius() {

        if (cornerRadius > 0) {

            path = new Path();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (cornerRadius > 0) {

            rect = new RectF(0, 0, this.getWidth(), this.getHeight());
            path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW);
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }
}