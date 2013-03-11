package be.kdg.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import be.kdg.android.activities.TripActivity;
import be.kdg.android.entities.Stop;
import be.kdg.android.entities.Trip;
import be.kdg.android.utilities.Utilities;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * User: Sander
 * Date: 10/03/13 - 19:21
 */
public class StopMapFragment extends MapFragment {
    private GoogleMap mMap;
    private Stop[] stops;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().getClass().getName().equals(TripActivity.class.getName())) {
            Trip trip = ((TripActivity) getActivity()).getTrip();
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

        // Antwerp
        mMap.moveCamera(Utilities.getCameraUpdate(Utilities.ANTWERP, 13));

        fillMap();
    }

    private void fillMap() {
        for(Stop stop : stops) {
            LatLng position = new LatLng(stop.getLatitude(), stop.getLongitude());
            mMap.addMarker(Utilities.getMarker(position, stop.getName(), stop.getDescription()));
        }
    }
}
