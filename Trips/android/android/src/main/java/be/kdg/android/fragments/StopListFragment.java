package be.kdg.android.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import be.kdg.android.R;
import be.kdg.android.activities.TripActivity;
import be.kdg.android.entities.Stop;
import be.kdg.android.entities.Trip;
import be.kdg.android.listadapters.StopsListAdapter;

/**
 * User: Sander
 * Date: 10/03/13 - 17:33
 */
public class StopListFragment extends ListFragment {
    private StopsListAdapter stopsListAdapter;
    private Stop[] stops;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.stop_list_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().getClass().getName().equals(TripActivity.class.getName())) {
            Trip trip = ((TripActivity) getActivity()).getTrip();
            stops = trip.getStops().toArray(new Stop[trip.getStops().size()]);
            fillList();
        }
    }

    private void fillList() {
        try {
            stopsListAdapter = new StopsListAdapter(getActivity(), stops);
            setListAdapter(stopsListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
