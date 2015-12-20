package com.godpowerturbo.android;

import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.ActionBarListActivity;
import api.Model;
import api.MySingleton;
import api.Resource;

public class EventsListing extends ActionBarListActivity {
    private static final String TAG = EventsListing.class.getSimpleName();
    private Context mCtx = this;
    private ProgressDialog dialog;
    private List<Model> dataList = new ArrayList<>();
    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        Log.e(TAG, "onCreate");
        setUpActionBar();
        registerViews();
        listView();
    }

    private void registerViews(){
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Position: " + position + " Id: " + id);
                String tid = ((TextView) view.findViewById(R.id.id)).getText().toString();
                String ttitle = ((TextView) view.findViewById(R.id.title)).getText().toString();
                String tdescription = ((TextView) view.findViewById(R.id.description)).getText().toString();
                Bundle b = new Bundle();
                b.putString("id", tid);
                b.putString("description", tdescription);
                b.putString("title", ttitle);
                b.putString("loc", "2");
                Intent i = new Intent(EventsListing.this, BlackBoard.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    private void listView(){
        Log.e(TAG, "listView()");
        adapter = new CustomListAdapter(EventsListing.this, dataList);
        dialog = new ProgressDialog(EventsListing.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading Events...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        showDialog();
        makeJsonRequest();
        listView.setAdapter(adapter);
    }

    private void makeJsonRequest(){
        JsonObjectRequest jsonReq = new JsonObjectRequest(Resource.FETCH_EVENTS,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, response.toString());
                        hideDialog();
                        try {
                            JSONArray obj = response.getJSONArray("events");
                            // Parsing json
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject jok = obj.getJSONObject(i);
                                String id = jok.getString("id");
                                String title = jok.getString("title");
                                String description = jok.getString("description");
                                String created = jok.getString("created_at");
                                String image = Resource.FETCH_EVENT_IMAGES + id + ".png";
                                Model mod = new Model(id, title, description, created, image);
                                mod.setTitle(title);
                                mod.setThumbnailUrl(image);

                                // adding mod to movies array
                                dataList.add(mod);

                            }
                        }catch (JSONException e) {
                            e.printStackTrace();

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideDialog();
                Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EventsListing.this, Main.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });

        // Adding request to request queue
        RetryPolicy policy = new DefaultRetryPolicy(Resource.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonReq.setRetryPolicy(policy);
        MySingleton.getInstance(mCtx).addToRequestQueue(jsonReq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected int getListViewId() {
        return android.R.id.list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.menu_logout:
                Intent i = new Intent(EventsListing.this, Logout.class);
                startActivity(i);
                break;
            case R.id.menu_exit:
                stopService(new Intent(EventsListing.this, MyService.class));
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

    private void showDialog(){
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    private void hideDialog(){
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

}