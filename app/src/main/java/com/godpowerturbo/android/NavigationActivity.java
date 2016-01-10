package com.godpowerturbo.android;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mikepenz.materialdrawer.DrawerBuilder;

public class NavigationActivity extends ActionBarActivity {

    private static final String TAG = NavigationActivity.class.getSimpleName();
    public DrawerLayout dw  = null;
    public PercentRelativeLayout rl = null;
    EditText searchbox;
    android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;
    ImageButton nav, hom, catalogue, upcomingevents, newlaunch,
            turbofailure, aboutus, enquiry, faq, feedback,
            back, exit, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_main);
        dw = (DrawerLayout) findViewById(R.id.nav_drawer);
        rl = (PercentRelativeLayout) findViewById(R.id.nav_left);
        new DrawerBuilder().withActivity(this).build();
        pk();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    private void pk(){
        ActionBar idk = getSupportActionBar();
        idk.setDisplayHomeAsUpEnabled(false);
        idk.setDisplayShowTitleEnabled(false);
        idk.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        idk.setStackedBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.header, null);
        idk.setCustomView(mCustomView);
        idk.setDisplayShowCustomEnabled(true);
        registerpk(mCustomView);
        mDrawerToggle = new android.support.v4.app.ActionBarDrawerToggle(this, dw, R.drawable.option, 0, 0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        };
        dw.setDrawerListener(mDrawerToggle);
    }

    private void registerpk(View mCustomView){
        nav = (ImageButton) mCustomView.findViewById(R.id.nav_menu);
        search = (ImageButton) mCustomView.findViewById(R.id.search);
        searchbox = (EditText) mCustomView.findViewById(R.id.query);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "NAV MENU CLICK");
                dw.openDrawer(Gravity.LEFT);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchbox.getText().toString();
                Log.e(TAG, "NAV SEARCH QUERY: " + query);
            }
        });
    }

}