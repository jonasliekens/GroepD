package be.kdg.android.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import be.kdg.android.entities.Trip;
import be.kdg.android.entities.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: Sander
 * Date: 26/02/13 15:38
 */
public class Utilities {
    public static final String PREFS_NAME = "TripsPreferences";
    //    public static final String SERVER_ADDRESS = "http://192.168.0.196:8080/web";
    public static final String SERVER_ADDRESS = "http://192.168.113.1:8080/web";

    public static final String LOGIN_ADDRESS = SERVER_ADDRESS + "/rest/login";
    public static final int LOGIN_REQUEST = 0;

    public static final String  TRIPS_ADDRESS = SERVER_ADDRESS + "/rest/trips";

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
            List<Trip> trips = gson.fromJson(json, new TypeToken<List<Trip>>(){}.getType());
            return trips;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
