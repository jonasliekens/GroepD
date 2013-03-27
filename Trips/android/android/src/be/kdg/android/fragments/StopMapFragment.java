package be.kdg.android.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import be.kdg.android.R;
import be.kdg.android.activities.InvitationActivity;
import be.kdg.android.activities.RegisteredTripActivity;
import be.kdg.android.activities.TripActivity;
import be.kdg.android.entities.ParticipatedTrip;
import be.kdg.android.entities.Stop;
import be.kdg.android.entities.Trip;
import be.kdg.android.networking.RestHttpConnection;
import be.kdg.android.utilities.Utilities;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Sander
 * Date: 10/03/13 - 19:21
 */
public class StopMapFragment extends MapFragment {
    private GoogleMap mMap;
    private Stop[] stops;
    private Trip trip = null;

    private SharedPreferences settings;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        initSettings();
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    private void initSettings() {
        settings = getActivity().getSharedPreferences(Utilities.PREFS_NAME, 0);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        switch (getActivity().getClass().getSimpleName()) {
            case "TripActivity":
                trip = ((TripActivity) getActivity()).getTrip();
                break;
            case "RegisteredTripActivity":
                trip = ((RegisteredTripActivity) getActivity()).getTrip();
                break;
            case "InvitationActivity":
                trip = ((InvitationActivity) getActivity()).getTrip();
                break;
        }

        if (trip != null) {
            stops = trip.getStops().toArray(new Stop[trip.getStops().size()]);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setupMapIfNeeded();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupMapIfNeeded();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    private void setupMapIfNeeded() {
        if (mMap == null) {
            mMap = getMap();
            if (mMap != null) {
                initMap();
            }
        } else {
            initMap();
        }
    }

    private void initMap() {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        fillMap();

        ShowLocationsTask showLocationsTask = new ShowLocationsTask();
        showLocationsTask.execute();
    }

    private void fillMap() {
        for (Stop stop : stops) {
            LatLng position = new LatLng(stop.getLatitude(), stop.getLongitude());
            mMap.addMarker(Utilities.getMarker(position, stop.getName(), stop.getDescription()));
        }

        // center first stop or else center Antwerp
        if (stops.length > 0) {
            LatLng position = new LatLng(stops[0].getLatitude(), stops[0].getLongitude());
            mMap.moveCamera(Utilities.getCameraUpdate(position, 17));
        } else {
            mMap.moveCamera(Utilities.getCameraUpdate(Utilities.ANTWERP, 13));
        }
    }

    private void showUsers(List<ParticipatedTrip> participatedTrips) {
        for (ParticipatedTrip pt : participatedTrips) {
            LatLng position = new LatLng(pt.getLatitude(), pt.getLongitude());
            mMap.addMarker(Utilities.getUserMarker(position, pt.getUser().getFirstName(), pt.getUser().getLastName()));
        }
    }

    public class ShowLocationsTask extends AsyncTask<String, String, List<ParticipatedTrip>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), getString(R.string.downloading_progress_title), getString(R.string.downloading_progress_desc), true, false);
        }

        @Override
        protected List<ParticipatedTrip> doInBackground(String... data) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                Integer tripId = trip.getId();
                Integer userId = settings.getInt("userId", 0);

                params.add(new BasicNameValuePair("tripId", tripId.toString()));
                params.add(new BasicNameValuePair("userId", userId.toString()));
                RestHttpConnection restHttpConnection = new RestHttpConnection();
                String result = restHttpConnection.doGetWithParams(Utilities.LOCATION_USERS_ADDRESS, params);

                if (!(result.equals("null") || result.isEmpty())) {
                    List<ParticipatedTrip> participatedTrips = Utilities.getParticipatedTrips(result);
                    return participatedTrips;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<ParticipatedTrip> participatedTrips) {
            progressDialog.dismiss();
            showUsers(participatedTrips);
        }
    }
}
