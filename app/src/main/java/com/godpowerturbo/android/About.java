package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import api.Resource;

public class About extends ActionBarActivity{
    private static final String TAG = About.class.getSimpleName();
    private Context mCtx = this;
    TextView txtAbout;
    Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Log.e(TAG, "onCreate");
        setUpActionBar();
        registerViews();
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
                Intent i = new Intent(About.this, Logout.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                stopService(new Intent(About.this, MyService.class));
                android.os.Process.killProcess(Resource.PID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void registerViews(){
        txtAbout = (TextView) findViewById(R.id.txtAbout);
        btnAbout =(Button) findViewById(R.id.btnAbout);
        txtAbout.setMovementMethod(LinkMovementMethod.getInstance());
        txtAbout.setText(Html.fromHtml(getString(R.string.aboutText)));
        btnAbout.setText("OK");
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(About.this, Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }

    private void setUpActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
        ab.setDisplayHomeAsUpEnabled(true);
    }

}
