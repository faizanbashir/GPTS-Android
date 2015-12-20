package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import api.ActionBarListActivity;
import api.Database;
import api.Resource;

public class CompanyListing extends ActionBarListActivity {
    private static final String TAG = CompanyListing.class.getSimpleName();
    ListView mList;
    private Context mCtx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        Log.e(TAG, "onCreate");
        setUpActionBar();
        mList = (ListView) findViewById(R.id.list);
        listView();
        registerViews();
    }

    private void listView(){
        Database db = new Database(mCtx);
        try{
            db.open();
            List<String> company = db.getCompany();
            Log.e(TAG, "DB DATA: " + company.toString());
            ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(CompanyListing.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, company);
            mList.setAdapter(arrayAdapter);
            db.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void registerViews(){
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Position: " + position + " Id: " + id);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                String company = tv.getText().toString();
                Log.e(TAG, "List Item Value: " + company);
                Bundle bd = new Bundle();
                bd.putString("company", company);
                Intent i = new Intent(CompanyListing.this, PartListing.class);
                i.putExtras(bd);
                startActivity(i);
            }
        });
    }

    private void setUpActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
        ab.setDisplayHomeAsUpEnabled(true);
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
                Intent i = new Intent(CompanyListing.this, Logout.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                stopService(new Intent(CompanyListing.this, MyService.class));
                android.os.Process.killProcess(Resource.PID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getListViewId() {
        return 0;
    }
}
