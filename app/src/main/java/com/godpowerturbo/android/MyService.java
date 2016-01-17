package com.godpowerturbo.android;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import api.Connection;
import api.MySingleton;
import api.Resource;
import api.Session;

public class MyService extends Service {
    private static final String TAG = MyService.class.getSimpleName();
    private Context mCtx = this;
    private Session session;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        session = new Session(mCtx);
        Connection conn = new Connection(mCtx);
        boolean isConnected = conn.getConnection();
        if(isConnected){
            if(getString(R.string.login).equals("false"))
                validate();
        }
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        logout();
        super.onDestroy();
    }

    private void validate(){
        StringRequest strReq = new StringRequest(Request.Method.POST, Resource.VALIDATE_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Login Response: " + response);
                        try{
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");
                            if(status.equals(Resource.TAG_SUCCESS)){
                                String user = jObj.getString("user");
                                JSONObject jUser = new JSONObject(user);
                                String activeStatus = jUser.getString("status");
                                if(activeStatus.equals("Active")){
                                    Log.e(TAG, "User statuts: " + activeStatus);
                                }else if(activeStatus.equals("Inactive")){
                                    session.logOut();
                                    Toast.makeText(getApplicationContext(), "Your LOG ID has been block listed!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MyService.this, Login.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                }else{
                                    Log.e(TAG, "User Status" + activeStatus);
                                }
                                //Start Activity
                            }else{
                                Log.e(TAG, "Status: " + status);
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error Response: " + error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String IMEI = getIMEI();
                params.put(Resource.TAG_MOBILE, session.getMobile());
                params.put(Resource.TAG_IP, IMEI);
                Log.e(TAG, "Sending data: " + params.values());
                return params;
            }
        };
        RetryPolicy policy = new DefaultRetryPolicy(Resource.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        MySingleton.getInstance(this).addToRequestQueue(strReq);
    }

    private void logout(){
        Connection conn = new Connection(this);
        boolean isConnected = conn.getConnection();
        if(isConnected){
            StringRequest strReq = new StringRequest(Request.Method.POST, Resource.LOGOUT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e(TAG, "onDestroy: " + response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onDestroy Error: " + error.toString());
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    Session session = new Session(mCtx);
                    String LOG_ID = session.getLogId();
                    params.put("log_id", LOG_ID);
                    Log.e(TAG, "Sending Data: " + params.values());
                    return params;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(Resource.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            strReq.setRetryPolicy(policy);
            MySingleton.getInstance(this).addToRequestQueue(strReq);
        }else{
            Log.e(TAG, "onDestroy: Not Connected");
        }
    }

    private String getIMEI(){
        TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        return tel.getDeviceId();
    }

}

