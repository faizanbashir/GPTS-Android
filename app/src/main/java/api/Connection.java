package api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class Connection {
    Context _CONTEXT;

    public Connection(Context context){
        _CONTEXT = context;
    }

    public boolean getConnection(){
        ConnectivityManager cm = (ConnectivityManager) _CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        try{
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if(isConnected){
                Log.e("Network Status", "Connected");
                return true;
            }else{
                Log.e("Network Status", "Not Connected");
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean getConnection(Context ctx){
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        try{
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if(isConnected){
                Log.e("Network Status", "Connected");
                return true;
            }else{
                Log.e("Network Status", "Not Connected");
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
