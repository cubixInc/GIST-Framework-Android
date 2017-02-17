package com.cubix.gist.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.cubix.gist.R;

/**
 * Created by imran.pyarali on 6/30/2016.
 */
public class GistDialogFragment extends DialogFragment {

    protected FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if ((keyCode ==  android.view.KeyEvent.KEYCODE_BACK)) {

                    onBackPressed();
                    return true; // pretend we've processed it
                } else {

                    return false;
                }
            }
        });

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        fragmentManager = getActivity().getSupportFragmentManager();
        return dialog;
    }

    @Override
    public void onStart() {

        super.onStart();
        initDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void dismissDialog() {

        Dialog dialog = getDialog();
        dialog.cancel();
    }

    protected void initDialog() {

        Dialog dialog = getDialog();
        if (dialog != null) {

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        }
    }

    protected void onBackPressed() {

    }
}
