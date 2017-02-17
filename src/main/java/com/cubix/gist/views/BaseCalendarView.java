package com.cubix.gist.views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CalendarView;

import com.cubix.gist.R;
import com.cubix.gist.synresource.SyncResourceManager;


/**
 * Created by imran.pyarali on 6/6/2016.
 */
public class BaseCalendarView extends CalendarView {

    private SyncResourceManager syncResourceManager;

    public BaseCalendarView(Context context) {
        super(context);
    }

    public BaseCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public BaseCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    @TargetApi(21)
    @SuppressLint("NewApi")
    public BaseCalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(AttributeSet attrs) {

        syncResourceManager = SyncResourceManager.getInstance();

        if (attrs != null) {

            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.baseView);

            try {

                String bgColor = mTypedArray.getString(R.styleable.baseView_cbx_bgColor);

                /*ViewGroup vg = (ViewGroup) getChildAt(0);
                View child = vg.getChildAt(0);

                if(child instanceof TextView) {
                    ((TextView)child).setTextColor(Color.RED);
                }*/

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
