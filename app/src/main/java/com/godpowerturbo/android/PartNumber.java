package com.godpowerturbo.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;

import api.Resource;

public class PartNumber extends ActionBarActivity {
    private static final String TAG = PartNumber.class.getSimpleName();
    private Context mCtx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnumber);
        Log.e(TAG, "onCreate");
        setUpActionBar();
    }

    private void setUpActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
        ab.setDisplayHomeAsUpEnabled(true);
    }

}
