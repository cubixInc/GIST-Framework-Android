package com.cubix.gist.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.RelativeLayout.LayoutParams;

/**
 * Created by imran.pyarali on 6/30/2016.
 */
public class GistDialog extends Dialog {

    public GistDialog(Context context) {
        super(context);

        initDialog();
    }

    public GistDialog(Context context, int themeResId) {
        super(context, themeResId);

        initDialog();
    }

    public GistDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

        initDialog();
    }

    protected void initDialog() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }
}
