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
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import api.Connection;
import api.Database;


public class CauseListing extends AppCompatActivity {
    private static final String TAG = CauseListing.class.getSimpleName();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    DrawerLayout dw;
    PercentRelativeLayout rl, l;
    android.support.v4.app.ActionBarDrawerToggle mDrawerToggle;
    EditText searchbox;
    ImageButton nav, nav_home, nav_catalogue, nav_upcomingevents, nav_newlaunch,
            nav_turbofailure, nav_aboutus, nav_enquiry, nav_faq, nav_feedback,
            nav_search;
    HashMap<String, List<String>> listDataChild;
    TextView tv;
    private Context mCtx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandable);

        // preparing list data
        //prepareListData();
        String symptom = getIntent().getStringExtra("symptom");
        setData(symptom);

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        tv = (TextView) findViewById(R.id.textExpandable);
        tv.setText(symptom);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
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
                Intent i = new Intent(CauseListing.this, Main.class);
                startActivity(i);
            }
        });
        nav_catalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CauseListing.this, CompanyListing.class);
                startActivity(i);
            }
        });
        nav_upcomingevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connection.getConnection(mCtx)){
                    Intent i = new Intent(CauseListing.this, EventsListing.class);
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
                    Intent i = new Intent(CauseListing.this, HotProductsListing.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nav_turbofailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CauseListing.this, Troubleshootlisting.class);
                startActivity(i);
            }
        });
        nav_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CauseListing.this, About.class);
                startActivity(i);
            }
        });
        nav_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CauseListing.this, Contact.class);
                startActivity(i);
            }
        });
        nav_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CauseListing.this, Faq.class);
                startActivity(i);
            }
        });
        nav_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CauseListing.this, Feedback.class);
                startActivity(i);
            }
        });
        dw = (DrawerLayout) findViewById(R.id.nav_main);
        rl = (PercentRelativeLayout) findViewById(R.id.nav_left);
        pk();
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
                Intent i = new Intent(CauseListing.this, PartNumberListing.class);
                i.putExtras(bd);
                startActivity(i);
            }
        });
    }

    private void setData(String symptom){
        try{
            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<>();
            List<List<String>> dexter = new ArrayList<>();
            List<String> vexed = new ArrayList<>();
            Database db = new Database(mCtx);
            db.open();
            Cursor c = db.getCauses(symptom);
            c.moveToFirst();
            int e = 0;
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                String i = e+1 + ". " + c.getString(c.getColumnIndex("cause"));
                listDataHeader.add(i);
                String x = c.getString(c.getColumnIndex("cause_no"));
                Cursor d = db.getPotentialCause(x);
                d.moveToFirst();
                String y = d.getString(d.getColumnIndex("potential_cause"));
                vexed.add(y);
                dexter.add(Arrays.asList(vexed.get(e)));
                for(int k = 0; k < dexter.size(); k++){
                    listDataChild.put(listDataHeader.get(e), dexter.get(k));
                }
                e++;
            }
            db.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}