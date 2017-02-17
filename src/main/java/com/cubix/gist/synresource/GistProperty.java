package com.cubix.gist.synresource;

import android.content.Context;
import android.content.res.AssetManager;

import com.cubix.gist.utils.GistConstants;
import com.cubix.gist.utils.GistUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by imran.pyarali on 5/24/2016.
 */
public class GistProperty {

    private static String DB_PATH = "";
    private String filename = "";
    private Properties properties;

    public GistProperty(Context context, String filename) {

        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "Package Name: " + context.getPackageName());
        DB_PATH = "/data/data/" + context.getPackageName() + "/";
        this.filename = filename;
        properties = new Properties();

        if (!isFileExist()) {

            if (isAssetFileExist(context)) {

                GistUtils.Log(GistConstants.LOG_D, GistConstants.TAG, "copyPropertyFile");
                copyPropertyFile(context);
                readProperty();

            } else {

                GistUtils.Log(GistConstants.LOG_D, GistConstants.TAG, "createFile");
                createFile();
            }
        } else {

            syncResourcesFromAsset(context);
        }
    }

    public boolean isFileExist() {

        File file = new File(DB_PATH + filename);
        return file.exists();
    }

    public boolean isAssetFileExist(Context context) {

        boolean isExist = false;

        try {

            InputStream stream = context.getAssets().open(filename);
            stream.close();
            isExist = true;

        } catch (FileNotFoundException e) {

            GistUtils.Log(GistConstants.LOG_W, GistConstants.TAG, "assetExists failed: " + e.toString());
            e.printStackTrace();

        } catch (IOException e) {

            GistUtils.Log(GistConstants.LOG_W, GistConstants.TAG, "assetExists failed: " + e.toString());
            e.printStackTrace();
        }

        return isExist;
    }

    public void createFile() {

        try {

            File file = new File(DB_PATH + filename);
            file.createNewFile();

        } catch(IOException e) {

            e.printStackTrace();
        }
    }

    private void copyPropertyFile(Context context) {

        InputStream mInput = null;
        OutputStream mOutput = null;

        try {

            mInput = context.getAssets().open(filename);
            String outFileName = DB_PATH + filename;
            mOutput = new FileOutputStream(outFileName);
            byte[] mBuffer = new byte[1024];
            int mLength;

            while ((mLength = mInput.read(mBuffer)) > 0) {
                mOutput.write(mBuffer, 0, mLength);
            }

            mOutput.flush();
            mOutput.close();
            mInput.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    private Properties getPropertiesFromAsset(Context context) {

        Properties properties = new Properties();

        try {

            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(filename);
            properties.loadFromXML(inputStream);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return properties;
    }

    private void syncResourcesFromAsset(Context context) {

        Properties resourceProperties = getPropertiesFromAsset(context);
        if (resourceProperties.size() > properties.size()) {

            Enumeration propEnum = resourceProperties.propertyNames();
            while(propEnum.hasMoreElements()) {

                String key = (String)propEnum.nextElement();
                if (!properties.containsKey(key)) {

                    setPropertyData(key, resourceProperties.getProperty(key));
                }
            }

            writeProperty();
        }
    }

    public Properties getProperties() {

        return properties;
    }

    public void setProperties(Properties properties) {

        this.properties = properties;
    }

    public void readProperty() {

        try {

            if (properties != null) {

                FileInputStream fileInputStream = new FileInputStream(DB_PATH + filename);
                properties.loadFromXML(fileInputStream);

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void writeProperty() {

        try {

            if (properties != null) {

                FileOutputStream fileOutputStream = new FileOutputStream(DB_PATH + filename);
                properties.storeToXML(fileOutputStream, "");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void setPropertyData(String key, String value) {

        if (properties != null) {

            properties.setProperty(key, value);
        }
    }

    public String getPropertyData(String key) {

        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "Key: " + properties.getProperty(key));
        return (properties != null) ? ((properties.getProperty(key) != null) ? properties.getProperty(key) : key): key;
    }
}
