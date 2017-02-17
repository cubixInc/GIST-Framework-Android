package com.cubix.gist.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.cubix.gist.R;


public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Activity activity) {
        super(activity, R.style.ProgressDialogTheme);

        initDialog();
    }

    public CustomProgressDialog(Context context) {
        super(context, R.style.ProgressDialogTheme);

        initDialog();
    }

    public CustomProgressDialog(Activity activity, int style) {

        super(activity, style);
        initDialog();
    }

    private void initDialog() {

        setContentView(R.layout.dialog_my_progressbar);
        //setCancelable(true);
    }
}
