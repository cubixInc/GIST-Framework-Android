package com.cubix.gist.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class GistUtils {

    public static void Log(int logType, String tag, String message) {

        switch (logType) {

            case GistConstants.LOG_V:
                Log.v(tag, message);
                break;

            case GistConstants.LOG_D:
                Log.d(tag, message);
                break;
            case GistConstants.LOG_I:
                Log.i(tag, message);
                break;

            case GistConstants.LOG_W:
                Log.w(tag, message);
                break;

            case GistConstants.LOG_E:
                Log.e(tag, message);
                break;
        }
    }

    public static boolean isTablet(Context context) {

		/*if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                == Configuration.SCREENLAYOUT_SIZE_LARGE) {

					GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "ScreenLayout: large");
				} else {
					GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "ScreenLayout: xlarge");
				}*/

        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static int dpToPx(Context context, int dps) {

        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

    public static String getVersion(Activity activity) {

        String result = "";
        try {

            PackageManager manager = activity.getPackageManager();
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);

            result = String.format("%s", info.versionName);

        } catch (PackageManager.NameNotFoundException e) {

            result = "Unable to get application version.";
        }

        return result;
    }

    public static void hideSoftKey(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isVisibleSoftKey(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    public static int[] getDeviceWidthHeightInPixels(Activity activity) {

        int[] densityArr = new int[2];

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        densityArr[0] = displaymetrics.widthPixels;
        densityArr[1] = displaymetrics.heightPixels;

        return densityArr;
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static boolean isInternetConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();

    }

    public final static boolean isValidEmail(CharSequence target) {

        if (target == null) {

            return false;

        } else {

            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidPhone(CharSequence target) {

        if (target == null) {

            return false;

        } else {

            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

    public static int toInt(String data) {

        int nRet = 0;

        if (data != null) {

            if (!data.equalsIgnoreCase("null") && !data.equals("")) {
                nRet = Integer.parseInt(data);
            }
        }

        return nRet;
    }

    public static long toLong(String data) {

        long nRet = 0;

        if (data != null) {

            if (!data.equalsIgnoreCase("null") && !data.equals("")) {
                nRet = Long.parseLong(data);
            }
        }

        return nRet;
    }

    public static double toDouble(String data) {

        double nRet = 0;

        if (data != null) {

            if (!data.equalsIgnoreCase("null") && !data.equals("")) {
                nRet = Double.parseDouble(data);
            }
        }

        return nRet;
    }

    public static float toFloat(String data) {

        float nRet = 0;

        if (data != null) {

            if (!data.equalsIgnoreCase("null") && !data.equals("")) {
                nRet = Float.parseFloat(data);
            }
        }

        return nRet;
    }

    public static int getAge(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }

        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

    public static int getAge(Date dateOfBirth) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(dateOfBirth);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }

        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }

    public static String capEachWordFirstLetter(String source) {
        String result = "";
        String[] splitString = source.split(" ");
        for (String target : splitString) {
            result += target.charAt(0)
                    + target.substring(1).toLowerCase() + " ";
        }
        return result.trim();
    }

    public static void copyDBToExternalStorage(Activity activity, String db_name) {

        // e.g => db_name = example.db

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + activity.getPackageName() + "/databases/" + db_name;
                String backupDBPath = db_name + "_test.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }

    public static String capFirstLetter(String str) {

        String result = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        return result;
    }

    public static double convertStringToDoubleFormation(double angle) {

        //double angle = Double.parseDouble(price);
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.DOWN);
        double angleFormated = Double.parseDouble(df.format(angle));

        return angleFormated;
    }

    public static void openKeyboard(final EditText et, final Activity activity) {

        et.post(new Runnable() {

            public void run() {

                if (et != null && activity != null) {

                    et.requestFocus();
                    InputMethodManager lManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    lManager.showSoftInput(et, 0);
                }
            }
        });
    }

    public static void closeKeyboardView(Activity activity) {

        View view = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (view != null) {

            view.requestFocus();
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @SuppressLint("PackageManagerGetSignatures")
    public static void getDebugKeyHash(Activity activity) {

        try {

            PackageInfo info = activity.getPackageManager().getPackageInfo( activity.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void openURLBrowser(String url, Activity activity) {

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

    public static boolean checkIsGpsEnable(Activity activity) {

        LocationManager service = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return service.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAirplaneModeOn(Context context) {

        boolean isAirplaneModeOn;

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN){

            isAirplaneModeOn = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;

        }else{

            isAirplaneModeOn = Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }

        return isAirplaneModeOn;

    }

    public static boolean isTelephonyEnabled(Activity activity){
        TelephonyManager tm = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    public static byte[] convertStreamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return os.toByteArray();
    }

    public static float[] getDeviceActualDpi(Activity activity, int requestCode){

        float[] densityArr = new float[2];
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        switch (requestCode) {

            case GistConstants.REQUEST_FOR_LOGICAL_DPI:

                densityArr[0] = dm.density; // The logical density of the display
                break;

            case GistConstants.REQUEST_FOR_SCREEN_DPI:

                densityArr[0] = dm.densityDpi; // The screen density expressed as dots-per-inch
                break;

            case GistConstants.REQUEST_FOR_ABSOLUTE_HEIGHT:

                densityArr[0] = dm.heightPixels; // The absolute height of the display in pixels
                break;

            case GistConstants.REQUEST_FOR_ABSOLUTE_WIDTH:

                densityArr[0] = dm.widthPixels; // The absolute width of the display in pixels
                break;

            case GistConstants.REQUEST_FOR_SCALING_FACTOR:

                densityArr[0] = dm.scaledDensity; // A scaling factor for fonts displayed on the display
                break;

            case GistConstants.REQUEST_FOR_EXACT_DPI:

                densityArr[0] = dm.xdpi; // The exact physical pixels per inch of the screen in the Xfloat dimension
                densityArr[1] = dm.ydpi; // The exact physical pixels per inch of the screen in the Y dimension
                break;

        }

        return densityArr;
    }

    public static boolean isNumeric(String str){

        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static String decodeStringUTF(String s) {

        // URLEncoder.encode(params[6], "UTF-8")
        String str = "";
        try {
            str = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    public static Date getNextDayDate() {

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE  , 1);

        return date.getTime();
    }

    public static int getDateDifferenceInDay(Date date1, Date date2) {

        long diff = date2.getTime() - date1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static int getTimeInMinutes(String time) {

        String[] tokens = time.split(":");
        int hours = Integer.parseInt(tokens[0]);
        int minutes = Integer.parseInt(tokens[1]);
        int seconds = Integer.parseInt(tokens[2]);

        return 60 * hours +  minutes;
    }

    public static String getEncodedUrl(String urlStr) {

        URL url;
        try {

            url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());

            return uri.toASCIIString();

        } catch (MalformedURLException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }

    public static long convertHourToMilliseconds(float value) {

        return  (long)(value * 60 * 60) * 1000;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getWeekOfDayPrefix(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("E"); //E for Prefix (Mon) and EEEE for Complete (Monday)
        return sdf.format(date);
    }

    public static long getCurrentGMTTimeDifference(String date, String format) {

        if (date.equals("")) {
            return 0;
        }

        String currentGmtString = getCurrentGmtString(format); //"yyyy-MM-dd HH:mm:ss"

        Date currentDate = getDateFromString(currentGmtString, format); //"yyyy-MM-dd HH:mm:ss"
        Date oldDate = getDateFromString(date, format);

        return currentDate.getTime() - oldDate.getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentGmtString(String format) {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat(format); //"yyyy-MM-dd HH:mm:ss"
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        String date = dateFormatGmt.format(new Date()) + "";

        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(String date, String format) {

        try {

            SimpleDateFormat df = new SimpleDateFormat(format); //"yyyy-MM-dd HH:mm:ss"
            Date gmtdate = df.parse(date);

            return gmtdate;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static Date getLocalGMTDate(String date, String format) {

        try {

            SimpleDateFormat df = new SimpleDateFormat(format); //"yyyy-MM-dd HH:mm:ss"
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date gmtdate = df.parse(date);

            return gmtdate;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static Date getDate(String date, String format) {

        try {

            SimpleDateFormat df = new SimpleDateFormat(format); //"yyyy-MM-dd HH:mm:ss"
            Date gmtdate = df.parse(date);

            return gmtdate;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static String chatTime(long unixSeconds, boolean isChat) {

        SimpleDateFormat dateformat = null;
        Date currentDate = new Date();

        Date oldDate = new Date(unixSeconds * 1000L);

        if (oldDate != null) {

            long millsec = currentDate.getTime() - oldDate.getTime();
            long days = TimeUnit.MILLISECONDS.toDays(millsec);

            GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "remain days : " + days);

            if (days > GistConstants.DATE_YEAR) {

                dateformat = isChat ? new SimpleDateFormat("dd/MM/yyyy hh:mm a") : new SimpleDateFormat("dd/MM/yyyy");

            } else if (days > GistConstants.DATE_WEEK) {

                //dateformat = isChat ? new SimpleDateFormat("dd MMM hh:mm a") : new SimpleDateFormat("DD MMM");
                //dateformat = isChat ? new SimpleDateFormat("dd MMM hh:mm a") : new SimpleDateFormat("dd MMM");
                dateformat = isChat ? new SimpleDateFormat("dd MMM hh:mm a") : new SimpleDateFormat("dd MMM hh:mm a");

            } else if (days >= GistConstants.DATE_DAY) {

                //dateformate = isChat ? new SimpleDateFormat("EEE hh:mm a") : new SimpleDateFormat("EEE");
                dateformat = isChat ? new SimpleDateFormat("EEE hh:mm a") : new SimpleDateFormat("EEE hh:mm a");

            } else {

                dateformat = new SimpleDateFormat("hh:mm a");
            }
        }

        return dateformat.format(oldDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getChatTime(String date, String format, boolean isChat) {

        SimpleDateFormat dateformat = null;
        Date currentDate = new Date();
        Date oldDate = getLocalGMTDate(date, format);//"yyyy-MM-dd HH:mm:ss"

        if (oldDate != null) {

            long millsec = currentDate.getTime() - oldDate.getTime();
            long days = TimeUnit.MILLISECONDS.toDays(millsec);

            if (days > GistConstants.DATE_YEAR) {

                dateformat = isChat ? new SimpleDateFormat("dd/MM/yyyy hh:mm a") : new SimpleDateFormat("dd/MM/yyyy");

            } else if (days > GistConstants.DATE_WEEK) {

                dateformat = isChat ? new SimpleDateFormat("dd MMM hh:mm a") : new SimpleDateFormat("dd MMM");

            } else if (days >= GistConstants.DATE_DAY) {

                dateformat = isChat ? new SimpleDateFormat("EEE hh:mm a") : new SimpleDateFormat("EEE");

            } else {

                dateformat = new SimpleDateFormat("hh:mm a");
            }
        }

        return (oldDate == null) ? "" : dateformat.format(oldDate);
    }
}
