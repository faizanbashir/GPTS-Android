package com.godpowerturbo.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.text.Html;
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

public class Troubleshootlisting extends ActionBarListActivity{
    private static final String TAG = Troubleshootlisting.class.getSimpleName();
    ListView mList;
    //TextView tv;
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
            List<String> symptoms = db.getSymptoms();
            Log.e(TAG, "DB DATA: " + symptoms.toString());
            ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(Troubleshootlisting.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, symptoms);
            mList.setAdapter(arrayAdapter);
            db.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void registerViews(){
        //tv = (TextView) findViewById(R.id.txtCaution);
        //tv.setText(Html.fromHtml(getResources().getString(R.string.textCaution)));
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Position: " + position + " Id: " + id);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                String symptoms = tv.getText().toString();
                Log.e(TAG, "List Item Value: " + symptoms);
                Bundle bd = new Bundle();
                bd.putString("symptom", symptoms);
                Intent i = new Intent(Troubleshootlisting.this, CauseListing.class);
                i.putExtras(bd);
                startActivity(i);
            }
        });
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
                Intent i = new Intent(Troubleshootlisting.this, Logout.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                stopService(new Intent(Troubleshootlisting.this, MyService.class));
                android.os.Process.killProcess(Resource.PID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.STACKED_BACKGROUND)));
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(Resource.BACKGROUND_DRAWABLE)));
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getListViewId() {
        return 0;
    }

}