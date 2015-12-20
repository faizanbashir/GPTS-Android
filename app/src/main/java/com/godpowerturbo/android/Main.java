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
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import api.Connection;
import api.Resource;
import api.Session;

public class Main extends ActionBarActivity {
    private static final String TAG = Main.class.getSimpleName();
    ImageButton about, partNumber, contact, faq, events, hotProducts;
    private Context mCtx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");
        Session session = new Session(mCtx);
        Log.e(TAG, "LOG_ID: " + session.getLogId());
        session.putAll();
        setUpActionBar();
        registerViews();
    }

    @Override
    protected void onStart() {
        Intent i = new Intent(Main.this, MyService.class);
        startService(i);
        super.onStart();
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
                Intent i = new Intent(Main.this, Logout.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                stopService(new Intent(Main.this, MyService.class));
                finish();
                android.os.Process.killProcess(Resource.PID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void registerViews(){
        about = (ImageButton) findViewById(R.id.btnAboutUs);
        partNumber = (ImageButton) findViewById(R.id.btnPartNumber);
        contact = (ImageButton) findViewById(R.id.btnContactUs);
        faq = (ImageButton) findViewById(R.id.btnFAQ);
        events = (ImageButton) findViewById(R.id.btnEvents);
        hotProducts = (ImageButton) findViewById(R.id.btnHP);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, About.class);
                startActivity(i);
            }
        });
        partNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, CompanyListing.class);
                startActivity(i);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Contact.class);
                startActivity(i);
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Troubleshootlisting.class);
                startActivity(i);
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Connection.getConnection(mCtx)){
                    Intent i = new Intent(Main.this, EventsListing.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        hotProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Connection.getConnection(mCtx)){
                    Intent i = new Intent(Main.this, HotProductsListing.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(Main.this, MyService.class));
        finish();
        super.onDestroy();
    }
}