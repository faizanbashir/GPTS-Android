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
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import api.MySingleton;
import api.Resource;

public class BlackBoard extends ActionBarActivity {

    private static final String TAG = BlackBoard.class.getSimpleName();
    private Context mCtx = this;
    NetworkImageView nm;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackboard);
        Log.e(TAG, "onCreate()");
        setUpActionBar();
        registerViews();
    }

    private void registerViews(){
        Log.e(TAG, "ID : " + getIntent().getStringExtra("id") +
                " Title: " + getIntent().getStringExtra("title") +
                " Description: " + getIntent().getStringExtra("description") +
                " Image: " + getIntent().getStringExtra("image") +
                " Loc: " + getIntent().getStringExtra("loc"));
        String id = getIntent().getStringExtra("id");
        String description = getIntent().getStringExtra("description");
        String loc = getIntent().getStringExtra("loc");
        String image;
        if(loc.equals("1")){
            image = Resource.FETCH_HOT_PRODUCT_IMAGES + id + ".png";
        }else{
            image = Resource.FETCH_EVENT_IMAGES + id + ".png";
        }
        ImageLoader iLoader = MySingleton.getInstance(mCtx).getImageLoader();
        nm = (NetworkImageView) findViewById(R.id.imageBoard);
        tv = (TextView) findViewById(R.id.textBoard);
        nm.setImageUrl(image, iLoader);
        tv.setText(description);
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
                Intent i = new Intent(BlackBoard.this, Logout.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                stopService(new Intent(BlackBoard.this, MyService.class));
                android.os.Process.killProcess(Resource.PID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getIntent().getStringExtra("title"));
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
        ab.setDisplayHomeAsUpEnabled(true);
    }

}