package com.cubix.gist.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.GridView;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;
import com.cubix.gist.utils.GistUtils;


/**
 * Created by mehran.khan on 6/14/2016.
 */
public class BaseGridView extends GridView {

    private SyncResourceManager syncResourceManager;

    public BaseGridView(Context context) {
        super(context);
    }

    public BaseGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);
                String columnWidth = mTypedArray.getString(R.styleable.baseView_cbx_columnWidth);
                String verticalSpacing = mTypedArray.getString(R.styleable.baseView_cbx_verticalSpacing);
                String horizontalSpacing = mTypedArray.getString(R.styleable.baseView_cbx_horizontalSpacing);
                int numColumns = mTypedArray.getInt(R.styleable.baseView_cbx_numColumns , -1);

                setBackgroundColor(bgColor);
                setBaseColoumnWidth(getContext(), columnWidth);
                setBaseVerticalSpacing(getContext(), verticalSpacing);
                setBaseHorizontalSpacing(getContext(), horizontalSpacing);
                setBaseNumberOfColumns(numColumns);

            } catch(Exception e) {

                e.printStackTrace();

            } finally {

                mTypedArray.recycle();
            }
        }
    }

    public void setBaseNumberOfColumns(int numColumns){

        if (numColumns != -1) {
            setNumColumns(numColumns);
        }
    }

    public void setBackgroundColor(String bgColor){

        if (bgColor != null && !bgColor.isEmpty() && syncResourceManager != null) {
            setBackgroundColor(SyncResourceManager.getColor(bgColor));
        }
    }


    public void setBaseColoumnWidth(Context context, String columnWidth) {

        if(columnWidth != null && !columnWidth.isEmpty()) {

            columnWidth = columnWidth.replace("dp", "");
            int columnWidthInt = GistUtils.dpToPx(context, Integer.parseInt(columnWidth));
            setColumnWidth(columnWidthInt);
        }
    }

    public void setBaseVerticalSpacing(Context context, String verticalSpacing) {

        if(verticalSpacing != null && !verticalSpacing.isEmpty()) {

            verticalSpacing = verticalSpacing.replace("dp", "");
            int verticalSpacingInt = GistUtils.dpToPx(context, Integer.parseInt(verticalSpacing));
            setVerticalSpacing(verticalSpacingInt);
        }
    }

    public void setBaseHorizontalSpacing(Context context, String horizontalSpacing) {

        if(horizontalSpacing != null && !horizontalSpacing.isEmpty()) {

            horizontalSpacing = horizontalSpacing.replace("dp", "");
            int horizontalSpacingInt = GistUtils.dpToPx(context, Integer.parseInt(horizontalSpacing));
            setHorizontalSpacing(horizontalSpacingInt);
        }
    }
}
