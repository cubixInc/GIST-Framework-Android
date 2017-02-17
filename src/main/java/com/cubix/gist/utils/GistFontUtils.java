package com.cubix.gist.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;

/**
 * Created by Nooruddin on 18th June, 2014.
 */
public class GistFontUtils {

	public static Typeface setFontFace(Context context, String fontName){

		// Font path
		String fontPath = fontName;

		// Loading Font Face
		Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);

		return tf;
	}

	public static void applyFontToMenuItem(Activity activity, MenuItem mi, String font) {

		//Typeface font = Typeface.createFromAsset(getAssets(), "ds_digi_b.TTF");
		SpannableString mNewTitle = new SpannableString(mi.getTitle());
		mNewTitle.setSpan(new CustomTypefaceSpan("", GistFontUtils.setFontFace(activity, font)), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		mi.setTitle(mNewTitle);
	}

	/*public static void setDifferentFont(TextView tv, String text,String family, Typeface type) { 
		
		SpannableStringBuilder sb = new SpannableStringBuilder(getString(R.string.forgot_something_txt));
		
	}*/
	
}
