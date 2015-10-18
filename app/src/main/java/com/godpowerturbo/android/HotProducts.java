package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import api.Resource;

public class HotProducts extends ActionBarActivity{
    private static final String TAG = HotProducts.class.getSimpleName();
    private Context mCtx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotproducts);
        Log.e(TAG, "onCreate");
        setUpActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.menu_logout:
                Intent i = new Intent(HotProducts.this, Logout.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                stopService(new Intent(HotProducts.this, MyService.class));
                android.os.Process.killProcess(Resource.PID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
