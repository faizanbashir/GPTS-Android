package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.util.KeyboardUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import api.ActionBarListActivity;
import api.Connection;
import api.Database;
import api.Resource;

public class PartNumberListing extends ActionBarListActivity {
    private static final String TAG = PartNumberListing.class.getSimpleName();
    private Vibrator vibrate;
    String query;
    ListView mList;
    DrawerLayout dw;
    PercentRelativeLayout rl, l;
    android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;
    EditText searchbox;
    ImageButton nav, nav_home, nav_catalogue, nav_upcomingevents, nav_newlaunch,
            nav_turbofailure, nav_aboutus, nav_enquiry, nav_faq, nav_feedback,
            nav_search;
    private Context mCtx = this;
    List<String> ref = new ArrayList<>();
    EditText et;
    ImageButton im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnumberlisting);
        Log.e(TAG, "onCreate");
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mList = (ListView) findViewById(R.id.list);
        //listView();
        registerViews();
    }

    public boolean searchEngine(String src, String what) {
        Log.e(TAG, "SearchEngine() -> src " + src + ", what: " + what);
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained
        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));
        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;
            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }
        return false;
    }

    private void registerViews(){
        String query = getIntent().getStringExtra("query");
        et = (EditText) findViewById(R.id.editTextSearch);
        im = (ImageButton) findViewById(R.id.search);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et.getText().toString().isEmpty()){
                    String query = et.getText().toString();
                    Database db = new Database(mCtx);
                    try{
                        KeyboardUtil.hideKeyboard(PartNumberListing.this);
                        db.open();
                        Cursor c = db.getAll();
                        boolean eureka;
                        List<String> similar = new ArrayList<>();
                        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                            String str = c.getString(c.getColumnIndex("oemno"));
                            String id = c.getString(c.getColumnIndex("_id"));
                            Log.e(TAG, "OEMZ: " + str);
                            Log.e(TAG, "_ID " + id);
                            eureka = searchEngine(str, query);
                            if(eureka) {
                                Log.e(TAG, "Eureka a match: " + str);
                                Log.e(TAG, "Eureka a match ID: " + id);
                                ref.add(id);
                                similar.add(str);
                            }
                        }
                        if(similar.isEmpty()) {
                            vibrate();
                            et.setError("Cannot be found");
                        }
                        listView(similar);
                        db.close();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }else{
                    vibrate();
                    et.setError("Enter a Search Query");
                }
                Log.e(TAG, "Search Module");
            }
        });
        if(!query.isEmpty()){
            et.setText(query);
            im.performClick();
        }
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Position: " + position + " Id: " + id);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                String part = ref.get(position);
                Log.e(TAG, "Sending Id: " + ref.get(position));
                Log.e(TAG, "List Item Value: " + part);
                Bundle bd = new Bundle();
                bd.putString("_id", part);
                Intent i = new Intent(PartNumberListing.this, PartNumber.class);
                i.putExtras(bd);
                startActivity(i);
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
                Intent i = new Intent(PartNumberListing.this, Main.class);
                startActivity(i);
            }
        });
        nav_catalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumberListing.this, CompanyListing.class);
                startActivity(i);
            }
        });
        nav_upcomingevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.getConnection(mCtx)){
                    Intent i = new Intent(PartNumberListing.this, EventsListing.class);
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
                    Intent i = new Intent(PartNumberListing.this, HotProductsListing.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nav_turbofailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumberListing.this, Troubleshootlisting.class);
                startActivity(i);
            }
        });
        nav_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumberListing.this, About.class);
                startActivity(i);
            }
        });
        nav_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumberListing.this, Contact.class);
                startActivity(i);
            }
        });
        nav_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumberListing.this, Faq.class);
                startActivity(i);
            }
        });
        nav_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PartNumberListing.this, Feedback.class);
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
        l.setVisibility(View .INVISIBLE);
        searchbox = (EditText) mCustomView.findViewById(R.id.query);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "NAV MENU CLICK");
                if(!dw.isDrawerOpen(Gravity.LEFT)){
                    dw.openDrawer(rl);
                }else{
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
                Intent i = new Intent(PartNumberListing.this, PartNumberListing.class);
                i.putExtras(bd);
                startActivity(i);
            }
        });
    }

    private void listView(List<String> list){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PartNumberListing.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        arrayAdapter.notifyDataSetChanged();
        vibrate();
        mList.setAdapter(arrayAdapter);
    }

    @Override
    protected int getListViewId() {
        return android.R.id.list;
    }

    private void vibrate(){
        if(vibrate.hasVibrator())
            vibrate.vibrate(Resource.VIBR_TIME);
    }

}
