package be.kdg.android.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import be.kdg.android.entities.MyLocation;
import be.kdg.android.networking.RestHttpConnection;
import be.kdg.android.utilities.Utilities;
import de.akquinet.android.androlog.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maarten
 * Date: 15/03/13
 * Time: 10:27
 */
public class LocationService extends Service {
    private LocationManager locationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    Location lastLocation;
    private Integer tripId;
    private Integer userId;

    private class LocationListener implements android.location.LocationListener {
        public LocationListener(String provider) {
            lastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            lastLocation.set(location);

        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        Log.v("TRIPS", "in de functie onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("TRIPS", "in de functie onStartCommand");
        super.onStartCommand(intent, flags, startId);
        tripId = intent.getIntExtra("tripId", 0);
        userId = intent.getIntExtra("userId", 0);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.v("TRIPS", "in de functie onCreate");
        initializeLocationManager();
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (Exception ex) {
        }

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                locationManager.removeUpdates(mLocationListeners[i]);
            }
        }
    }

    private void initializeLocationManager() {
        Log.v("TRIPS", "in initialization");
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        }
    }

    public class GetLocationTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("tripId", tripId.toString()));
            params.add(new BasicNameValuePair("userId", userId.toString()));
            params.add(new BasicNameValuePair("latitude", String.format("%d", lastLocation.getLatitude())));
            params.add(new BasicNameValuePair("latitude", String.format("%d", lastLocation.getLongitude())));

            RestHttpConnection restHttpConnection = new RestHttpConnection();
            try {
                String result = restHttpConnection.doPostWithResult(Utilities.UPDATE_LOCATION_ADDRESS, params);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
