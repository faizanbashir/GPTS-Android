package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import api.Connection;
import api.Database;

public class PartNumber extends AppCompatActivity{
    private static final String TAG = PartNumber.class.getSimpleName();
    private Context mCtx = this;
    DrawerLayout dw;
    PercentRelativeLayout rl, l;
    android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;
    EditText searchbox;
    ImageButton nav, nav_home, nav_catalogue, nav_upcomingevents, nav_newlaunch,
            nav_turbofailure, nav_aboutus, nav_enquiry, nav_faq, nav_feedback,
            nav_search;
    String TAG_PARTNUMBER;
    String TAG_ID;
    TextView cmp, pat, app, gpn, mod;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnumber);
        registerViews();
    }

    private void registerViews(){
        iv = (ImageView) findViewById(R.id.partImage);
        cmp = (TextView) findViewById(R.id.companyName);
        pat = (TextView) findViewById(R.id.partNumber);
        app = (TextView) findViewById(R.id.application);
        gpn = (TextView) findViewById(R.id.gpno);
        mod = (TextView) findViewById(R.id.model);
        displayImage();
        //Drawer Buttons
        nav_home = (ImageButton) findViewById(R.id.nav_home);
        nav_catalogue = (ImageButton) findViewById(R.id.nav_catalogue);
        nav_upcomingevents = (ImageButton) findViewById(R.id.nav_upcomingevents);
        nav_newlaunch = (ImageButton) findViewById(R.id.nav_newlaunch);
        nav_turbofailure = (ImageButton) findViewById(R.id.nav_turbofailure);
        nav_aboutus = (ImageButton) findViewById(R.id.nav_aboutus);
        nav_enquiry = (ImageButton) findViewById(R.id.nav_enquiry);
        nav_faq = (ImageButton) findViewById(R.id.nav_faq);
        nav_feedback = (ImageButton) findViewById(R.id.nav_feedback);
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumber.this, Main.class);
                startActivity(i);
            }
        });
        nav_catalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumber.this, CompanyListing.class);
                startActivity(i);
            }
        });
        nav_upcomingevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.getConnection(mCtx)){
                    Intent i = new Intent(PartNumber.this, EventsListing.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nav_newlaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.getConnection(mCtx)){
                    Intent i = new Intent(PartNumber.this, HotProductsListing.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nav_turbofailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumber.this, Troubleshootlisting.class);
                startActivity(i);
            }
        });
        nav_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumber.this, About.class);
                startActivity(i);
            }
        });
        nav_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumber.this, Contact.class);
                startActivity(i);
            }
        });
        nav_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumber.this, Faq.class);
                startActivity(i);
            }
        });
        nav_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumber.this, Feedback.class);
                startActivity(i);
            }
        });
        dw = (DrawerLayout) findViewById(R.id.nav_main);
        rl = (PercentRelativeLayout) findViewById(R.id.nav_left);
        pk();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        mDrawerToggle = new android.support.v4.app.ActionBarDrawerToggle(this, dw, R.drawable.option, 0, 0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                l.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                l.setVisibility(View.VISIBLE);
            }
        };
        dw.setDrawerListener(mDrawerToggle);
        l = (PercentRelativeLayout) mCustomView.findViewById(R.id.linear_search);
        nav = (ImageButton) mCustomView.findViewById(R.id.nav_menu);
        nav_search = (ImageButton) mCustomView.findViewById(R.id.search);
        searchbox = (EditText) mCustomView.findViewById(R.id.query);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dw.isDrawerOpen(Gravity.LEFT)){
                    l.setVisibility(View.INVISIBLE);
                    dw.openDrawer(rl);
                }else{
                    l.setVisibility(View.VISIBLE);
                    dw.closeDrawer(rl);
                }

            }
        });
        nav_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchbox.getText().toString();
                Bundle bd = new Bundle();
                bd.putString("query", query);
                Intent i = new Intent(PartNumber.this, PartNumberListing.class);
                i.putExtras(bd);
                startActivity(i);
            }
        });
    }

    private void displayImage(){
        TAG_PARTNUMBER = getIntent().getStringExtra("partnumber");
        TAG_ID = getIntent().getStringExtra("_id");
        Database db = new Database(mCtx);
        try{
            db.open();
            Cursor c = db.getDetails(TAG_ID);
            setData(c);
            db.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void setData(Cursor c){
        c.moveToFirst();
        String gpno  = c.getString(c.getColumnIndex("gpno"));
        String company = c.getString(c.getColumnIndex("company"));
        String application = c.getString(c.getColumnIndex("application"));
        String model = c.getString(c.getColumnIndex("model"));
        String partnumber = c.getString(c.getColumnIndex("oemno"));
        String image = c.getString(c.getColumnIndex("image"));
        int i = setImage(image);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setImageResource(i);
        gpn.setText(gpno);
        cmp.setText(company);
        pat.setText(partnumber);
        mod.setText(model);
        app.setText(application);
    }

    private int setImage(String id){
        switch(id){
            case "0":
                return 0;
            case "1":
                return R.drawable.i1;
            case "2":
                return R.drawable.i2;
            case "3":
                return R.drawable.i3;
            case "4":
                return R.drawable.i4;
            case "5":
                return R.drawable.i5;
            case "6":
                return R.drawable.i6;
            case "7":
                return R.drawable.i7;
            case "8":
                return R.drawable.i8;
            case "9":
                return R.drawable.i9;
            case "10":
                return R.drawable.i10;
            case "11":
                return R.drawable.i11;
            case "12":
                return R.drawable.i12;
            case "13":
                return R.drawable.i13;
            case "14":
                return R.drawable.i14;
            case "16":
                return R.drawable.i16;
            case "17":
                return R.drawable.i17;
            case "18":
                return R.drawable.i18;
            case "19":
                return R.drawable.i19;
            case "20":
                return R.drawable.i20;
            case "21":
                return R.drawable.i21;
            case "22":
                return R.drawable.i22;
            case "23":
                return R.drawable.i23;
            case "24":
                return R.drawable.i24;
            case "25":
                return R.drawable.i25;
            default:
                return 0;
        }
    }
}
