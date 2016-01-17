package com.godpowerturbo.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import api.Connection;
import api.Resource;
import api.Session;

public class Feedback extends AppCompatActivity {

    DrawerLayout dw;
    PercentRelativeLayout rl, l;
    android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;
    EditText searchbox;
    private Vibrator vibrate;
    ImageButton nav, nav_home, nav_catalogue, nav_upcomingevents,
            nav_newlaunch, nav_turbofailure, nav_aboutus, nav_enquiry,
            nav_faq, nav_feedback, nav_search;
    private static final String TAG = Faq.class.getSimpleName();
    private Context mCtx = this;
    TextView txtFeedback;
    EditText et_name, et_suggestion, et_feedback;
    Button btn_send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_feedback);
        registerViews();
    }

    private void registerViews(){
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        txtFeedback = (TextView) findViewById(R.id.textFeedback);
        txtFeedback.setText("Feedback");
        et_name = (EditText) findViewById(R.id.editTextName);
        et_suggestion = (EditText) findViewById(R.id.editTextSubject);
        et_feedback = (EditText) findViewById(R.id.editTextMsg);
        btn_send = (Button) findViewById(R.id.buttonSubmit);
        final AwesomeValidation av = new AwesomeValidation(ValidationStyle.COLORATION);
        av.setColor(Color.YELLOW);
        setValidation(av);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = av.validate();
                Connection conn = new Connection(mCtx);
                boolean isConnected = conn.getConnection();
                if(isValid){
                    if(isConnected){
                        sendMail();
                    }else{
                        Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                        vibrate();
                    }
                }
            }
        });
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
                Intent i = new Intent(Feedback.this, Main.class);
                startActivity(i);
            }
        });
        nav_catalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Feedback.this, CompanyListing.class);
                startActivity(i);
            }
        });
        nav_upcomingevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.getConnection(mCtx)){
                    Intent i = new Intent(Feedback.this, EventsListing.class);
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
                    Intent i = new Intent(Feedback.this, HotProductsListing.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nav_turbofailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Feedback.this, Troubleshootlisting.class);
                startActivity(i);
            }
        });
        nav_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Feedback.this, About.class);
                startActivity(i);
            }
        });
        nav_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Feedback.this, Contact.class);
                startActivity(i);
            }
        });
        nav_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Feedback.this, Faq.class);
                startActivity(i);
            }
        });
        nav_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Feedback.this, Feedback.class);
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

    private void sendMail(){
        Session session = new Session(mCtx);
        String name = ((session.getName() == null || session.getName().isEmpty()) ? et_name.getText().toString() : session.getName());
        String email = ((session.getEmailAddr() == null || session.getEmailAddr().isEmpty()) ? "abc@xyz.com" : session.getEmailAddr() );
        String mobile = ((session.getMobile() == null || session.getMobile().isEmpty()) ? "1234567890" : session.getMobile());
        String body = "Name: " + name + "\n"
                + "Email:  " + email + "\n"
                + "Mobile: " + mobile + "\n"
                + "Suggestion: " + et_suggestion.getText().toString() + "\n"
                + "Feedback: " + et_feedback.getText().toString();
        Log.e(TAG, "Message Body: " + body);
        BackgroundMail mail = new BackgroundMail(Feedback.this);
        mail.setGmailUserName("caregodpower@gmail.com");
        mail.setGmailPassword("Godpower@1234");
        mail.setMailTo("gtmahendru@yahoo.com");
        mail.setFormBody(body);
        mail.setFormSubject("Mail from GPTS APP");
        mail.send();
    }

    private void setValidation(AwesomeValidation av){
        av.addValidation(Feedback.this, R.id.editTextName, "^[a-z A-z\\\\s]+", R.string.err_phone);
        av.addValidation(Feedback.this, R.id.editTextSubject, "^[a-z A-z\\\\s]+", R.string.err_email);
        av.addValidation(Feedback.this, R.id.editTextMsg, "^[a-z A-z\\\\s]+", R.string.err_name);
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
                Log.e(TAG, "NAV MENU CLICK");
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
                Log.e(TAG, "NAV SEARCH QUERY: " + query);
                Bundle bd = new Bundle();
                bd.putString("query", query);
                Intent i = new Intent(Feedback.this, PartNumberListing.class);
                i.putExtras(bd);
                startActivity(i);
            }
        });
    }

    private void vibrate(){
        if(vibrate.hasVibrator()){
            vibrate.vibrate(Resource.VIBR_TIME);
        }
    }
}
