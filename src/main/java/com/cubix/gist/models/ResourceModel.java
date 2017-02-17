package com.cubix.gist.models;

import java.util.Properties;

/**
 * Created by imran.pyarali on 5/24/2016.
 */
public class ResourceModel {

    private Properties text;
    private Properties color;
    private Properties constant;
    private Properties font_style;

    public Properties getText() {
        return text;
    }

    public void setText(Properties text) {
        this.text = text;
    }

    public Properties getColor() {
        return color;
    }

    public void setColor(Properties color) {
        this.color = color;
    }

    public Properties getConstant() {
        return constant;
    }

    public void setConstant(Properties constant) {
        this.constant = constant;
    }

    public Properties getFontStyle() {
        return font_style;
    }

    public void setFontStyle(Properties font_style) {
        this.font_style = font_style;
    }
}
