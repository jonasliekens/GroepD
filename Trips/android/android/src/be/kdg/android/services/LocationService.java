package be.kdg.android.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
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


    private class MyLocationListener implements LocationListener {
        public MyLocationListener(String provider) {
            lastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            if (isBetterLocation(location, lastLocation)) {
                lastLocation.set(location);
                GetLocationTask getLocationTask = new GetLocationTask();
                getLocationTask.execute();
            }
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

    MyLocationListener[] myLocationListeners = new MyLocationListener[]{
            new MyLocationListener(LocationManager.GPS_PROVIDER)//,
            //new MyLocationListener(LocationManager.NETWORK_PROVIDER)
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
        /*try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    myLocationListeners[1]);
        } catch (Exception ex) {
        } */

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    myLocationListeners[0]);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            for (int i = 0; i < myLocationListeners.length; i++) {
                locationManager.removeUpdates(myLocationListeners[i]);
            }
        }
    }

    private void initializeLocationManager() {
        Log.v("TRIPS", "in initialization");
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        }
    }

    /**
     * http://developer.android.com/training/basics/location/currentlocation.html
     * Determines whether one Location reading is better than the current Location fix
     *
     * @param location            The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > Utilities.LOCATION_UPDATE_TIME;
        boolean isSignificantlyOlder = timeDelta < -Utilities.LOCATION_UPDATE_TIME;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public class GetLocationTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("tripId", tripId.toString()));
            params.add(new BasicNameValuePair("userId", userId.toString()));
            params.add(new BasicNameValuePair("latitude", String.format("%f", lastLocation.getLatitude())));
            params.add(new BasicNameValuePair("longitude", String.format("%f", lastLocation.getLongitude())));

            try {
                RestHttpConnection restHttpConnection = new RestHttpConnection();
                String result = restHttpConnection.doPostWithResult(Utilities.UPDATE_LOCATION_ADDRESS, params);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
