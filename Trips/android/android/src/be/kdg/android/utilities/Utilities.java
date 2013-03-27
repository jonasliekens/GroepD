package be.kdg.android.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import be.kdg.android.entities.Stop;
import be.kdg.android.entities.Trip;
import be.kdg.android.entities.User;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: Sander
 * Date: 26/02/13 15:38
 */
public class Utilities {
    // Basic settings
    public static final String PREFS_NAME = "TripsPreferences";
    public static final long TRIPS_RELOAD_TIME = 5 * 60 * 1000; // 5 minutes
    public static final long LOCATION_UPDATE_TIME = 10000;

    // IP-addresses
//    public static final String SERVER_ADDRESS = "http://192.168.0.195:8080/web";
//    public static final String SERVER_ADDRESS = "http://192.168.0.196:8080/web";
    public static final String SERVER_ADDRESS = "http://192.168.3.1:8080/web";

    // RestControllers
    public static final String LOGIN_ADDRESS = SERVER_ADDRESS + "/rest/login";
    public static final int LOGIN_REQUEST = 0;
    public static final String ALL_TRIPS_ADDRESS = SERVER_ADDRESS + "/rest/trips/alltrips";
    public static final String MY_TRIPS_ADDRESS = SERVER_ADDRESS + "/rest/trips/mytrips/%d";
    public static final String REGISTERED_TRIPS_ADDRESS = SERVER_ADDRESS + "/rest/trips/registeredtrips/%d";
    public static final String INVITED_TRIPS_ADDRESS = SERVER_ADDRESS + "/rest/trips/invitations/%d";
    public static final String START_TRIP_ADDRESS = SERVER_ADDRESS + "/rest/trips/registeredtrips/start";
    public static final String UPDATE_LOCATION_ADDRESS = SERVER_ADDRESS + "/rest/trips/participant/location";

    // Google Maps
    public static final LatLng ANTWERP = new LatLng(51.2192159, 4.4028818);

    // Networking
    private static ConnectivityManager connectivityManager;
    private static Gson gson;

    private static void createGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });
            gson = builder.create();
        }
    }

    /*
         *  http://stackoverflow.com/a/10242593
         */
    public static boolean isOnline(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            return false;
        }

        return true;
    }

    public static String getEncryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();

        for(byte b : digest){
            sb.append(Integer.toHexString(b & 0xff));
        }

        return sb.toString();
    }

    public static User getUser(String json) {
        createGson();
        try {
            User user = gson.fromJson(json, User.class);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Trip> getTrips(String json) {
        createGson();
        try {
            List<Trip> trips = gson.fromJson(json, new TypeToken<List<Trip>>() { }.getType());
            return trips;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Stop getStop(String json){
        createGson();
        try {
            Stop stop = gson.fromJson(json, Stop.class);
            return stop;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CameraUpdate getCameraUpdate(LatLng target, int zoom) {
        CameraPosition cameraPositition = new CameraPosition.Builder()
                .target(target)
                .zoom(zoom)
                .build();

        return CameraUpdateFactory.newCameraPosition(cameraPositition);
    }

    public static MarkerOptions getMarker(LatLng position, String title, String description) {
        return new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(description)
                .draggable(false);
    }
}
