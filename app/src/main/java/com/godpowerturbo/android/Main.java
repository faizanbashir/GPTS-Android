package com.godpowerturbo.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import api.Connection;
import api.Session;

public class Main extends NavigationActivity {
    DrawerLayout dw;
    PercentRelativeLayout rl;
    private static final String TAG = Main.class.getSimpleName();
    ImageButton about, partNumber, turbofailure, contact, faq, events, hotProducts, feedback;
    ImageButton nav;
    private Context mCtx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");
        Session session = new Session(mCtx);
        Log.e(TAG, "LOG_ID: " + session.getLogId());
        session.putAll();
        registerViews();
    }

    @Override
    protected void onStart() {
        Intent i = new Intent(Main.this, MyService.class);
        startService(i);
        super.onStart();
        rl = (PercentRelativeLayout) findViewById(R.id.nav_left);
        dw = (DrawerLayout) findViewById(R.id.nav_drawer);
    }

    public void  searchDB(View v){
        Log.e(TAG, "Search the Database");
    }

    private void registerViews(){
        about = (ImageButton) findViewById(R.id.AboutUs);
        partNumber = (ImageButton) findViewById(R.id.Catalogue);
        contact = (ImageButton) findViewById(R.id.Enquiry);
        faq = (ImageButton) findViewById(R.id.FAQ);
        turbofailure = (ImageButton) findViewById(R.id.TurboFaliure);
        events = (ImageButton) findViewById(R.id.UpComingEvent);
        hotProducts = (ImageButton) findViewById(R.id.NewLaunch);
        feedback = (ImageButton) findViewById(R.id.Feedback);
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
        turbofailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Troubleshootlisting.class);
                startActivity(i);
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(Main.this, .class);
                //startActivity(i);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(Main.this, .class);
                //startActivity(i);
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

    @Override
    protected void onDestroy() {
        stopService(new Intent(Main.this, MyService.class));
        finish();
        super.onDestroy();
    }
}