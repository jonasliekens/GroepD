package be.kdg.android.fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.*;
import android.widget.ListView;
import be.kdg.android.R;
import android.os.Bundle;
import be.kdg.android.activities.TripActivity;
import be.kdg.android.entities.Trip;
import be.kdg.android.listadapters.TripsListAdapter;
import be.kdg.android.networking.RestHttpConnection;
import be.kdg.android.utilities.Utilities;

import java.io.IOException;
import java.util.List;

/**
 * User: Sander
 * Date: 26/02/13 11:30
 */
public class AllTripsFragment extends ListFragment {
    private TripsListAdapter tripsListAdapter;
    private List<Trip> trips;
    private Long lastUpdate = 0L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.all_trips_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && trips != null) {
            lastUpdate = savedInstanceState.getLong("lastUpdate");
        }

        downloadList();
    }

    private void downloadList() {
        Long now = System.currentTimeMillis();
        if ((now - lastUpdate) > Utilities.TRIPS_RELOAD_TIME) {
            AllTripsTask allTripsTask = new AllTripsTask();
            allTripsTask.execute();
        } else {
            fillList();
        }
    }

    private void fillList() {
        try {
            tripsListAdapter = new TripsListAdapter(getActivity(), trips.toArray(new Trip[trips.size()]));
            setListAdapter(tripsListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Trip trip = trips.get(position);
        Intent intent = new Intent(getActivity(), TripActivity.class);
        intent.putExtra("trip", trip);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.trips_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                lastUpdate = 0L;
                downloadList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (lastUpdate < System.currentTimeMillis()) {
            outState.putLong("lastUpdate", lastUpdate);
        }
    }

    public class AllTripsTask extends AsyncTask<String, String, List<Trip>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), getString(R.string.downloading_progress_title), getString(R.string.downloading_progress_desc), true, false);
        }

        @Override
        protected List<Trip> doInBackground(String... data) {
            try {
                RestHttpConnection restHttpConnection = new RestHttpConnection();
                String result = restHttpConnection.doGet(Utilities.TRIPS_ADDRESS);

                if (!(result.equals("null") || result.isEmpty())) {
                    List<Trip> trips = Utilities.getTrips(result);
                    return trips;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Trip> trips) {
            progressDialog.dismiss();
            AllTripsFragment.this.trips = trips;
            lastUpdate = System.currentTimeMillis();
            fillList();
        }
    }
}