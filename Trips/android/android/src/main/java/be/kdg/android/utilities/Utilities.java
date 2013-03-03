package be.kdg.android.utilities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import be.kdg.android.R;

/**
 * User: Sander
 * Date: 26/02/13 15:38
 */
public class Utilities {
    public static final String PREFS_NAME = "TripsPreferences";
    public static final String SERVER_ADDRESS = "http://192.168.0.196:8080/web";
//    public static final String SERVER_ADDRESS = "http://192.168.113.1:8080/web";

    public static final String LOGIN_ADDRESS = SERVER_ADDRESS + "/rest/login";
    public static final int LOGIN_REQUEST = 0;

    private static ConnectivityManager connectivityManager;

    /*
     *  http://stackoverflow.com/a/10242593
     */
    public static boolean isOnline(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            return false;
        }

        return true;
    }
}
