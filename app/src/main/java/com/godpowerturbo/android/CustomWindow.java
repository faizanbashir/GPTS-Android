package com.godpowerturbo.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class CustomWindow extends Activity {

    EditText et;
    Context mCtx = this;
    private final static String TAG = CustomWindow.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.header);
    }

    public void openNav(View v){
        Log.e(TAG, "Navigation Opened");
    }

    public void searchDB(View v){
        Log.e(TAG, "Search DB");
    }
}
