package com.cubix.gist.synresource;

import android.content.Context;

import java.util.Locale;

/**
 * Created by imran.pyarali on 5/24/2016.
 */
public class ResourceManager {

    private GistProperty stringProperty;
    private GistProperty colorProperty;
    private GistProperty constantProperty;
    private GistProperty fontProperty;

    public ResourceManager(Context context) {

        initStringResource(context);
        initColorResource(context);
        initConstantResource(context);
        initFontResource(context);
    }

    public void updateStringResource(Context context) {

        initStringResource(context);
    }

    private void initStringResource(Context context) {

        String filename = "string_" + Locale.getDefault().getLanguage() + ".xml";

        stringProperty = new GistProperty(context, filename);
        //stringProperty.readProperty();
    }

    private void initColorResource(Context context) {

        colorProperty = new GistProperty(context, "color.xml");
        //colorProperty.readProperty();
    }

    private void initFontResource(Context context) {

        fontProperty = new GistProperty(context, "font.xml");
        //fontProperty.readProperty();
    }

    private void initConstantResource(Context context) {

        constantProperty = new GistProperty(context, "constant.xml");
        //constantProperty.readProperty();
    }

    public GistProperty getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(GistProperty stringProperty) {
        this.stringProperty = stringProperty;
    }

    public GistProperty getColorProperty() {
        return colorProperty;
    }

    public void setColorProperty(GistProperty colorProperty) {
        this.colorProperty = colorProperty;
    }

    public GistProperty getConstantProperty() {
        return constantProperty;
    }

    public void setConstantProperty(GistProperty constantProperty) {
        this.constantProperty = constantProperty;
    }

    public GistProperty getFontProperty() {
        return fontProperty;
    }

    public void setFontProperty(GistProperty fontsProperty) {
        this.fontProperty = fontsProperty;
    }

}
