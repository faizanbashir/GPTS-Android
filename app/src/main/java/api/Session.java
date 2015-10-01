package api;

import android.content.SharedPreferences;
import android.content.Context;
import android.util.Log;

import java.util.HashMap;

public class Session {

    private static String TAG = Session.class.getSimpleName();
    static SharedPreferences pref;
    static SharedPreferences.Editor editor;
    Context _context;
    //SharedPreference Mode
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "GPTSLogin";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String LOG_ID = "log_id";
    private static final String USER = "user";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String MOBILE = "mobile";
    private static final String ID = "id";
    private static final String ACTIVE_STATUS = "status";


    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public void setUser(HashMap<String, String> map){
        editor.putString(LOG_ID, map.get(LOG_ID));
        editor.putString(USER, map.get(USER));
        editor.putString(NAME, map.get(NAME));
        editor.putString(EMAIL, map.get(EMAIL));
        editor.putString(MOBILE, map.get(MOBILE));
        editor.putString(ID, map.get(ID));
        editor.putString(ACTIVE_STATUS, map.get(ACTIVE_STATUS));
        editor.commit();
        Log.d(TAG, "Log id: " + map.get(LOG_ID) + ", User: " + map.get(USER));
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}