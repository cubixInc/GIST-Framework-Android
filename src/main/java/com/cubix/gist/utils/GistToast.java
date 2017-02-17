package com.cubix.gist.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by noor on 4/13/2016.
 */
public class GistToast {

    private static Toast toast;

    public static void showToast(Activity activity, String msg, int duration) {

        cancelToast();

        if (activity != null && !activity.isFinishing()) {

            toast = Toast.makeText(activity, msg, duration);
            toast.show();
        }
    }

    public static void showToast(Activity activity, int string_resource, int duration) {

        cancelToast();

        if (activity != null && !activity.isFinishing()) {

            toast = Toast.makeText(activity, string_resource, duration);
            toast.show();
        }
    }

    private static void cancelToast() {

        if (toast !=  null) {

            toast.cancel();
        }
    }
}
