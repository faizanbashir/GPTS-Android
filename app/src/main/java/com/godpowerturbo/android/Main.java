package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import api.Resource;

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
        setUpActionBar();
        registerViews();
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
                Intent i = new Intent(Main.this, PartNumber.class);
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
                Intent i = new Intent(Main.this, FAQ.class);
                startActivity(i);
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Events.class);
                startActivity(i);
            }
        });
        hotProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, HotProducts.class);
                startActivity(i);
            }
        });
    }

    private void setUpActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
    }

}
