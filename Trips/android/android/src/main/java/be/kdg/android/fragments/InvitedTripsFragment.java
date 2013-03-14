package be.kdg.android.fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import be.kdg.android.R;
import be.kdg.android.activities.InvitationActivity;
import be.kdg.android.entities.Trip;
import be.kdg.android.listadapters.TripsListAdapter;
import be.kdg.android.networking.RestHttpConnection;
import be.kdg.android.utilities.Utilities;

import java.io.IOException;
import java.util.List;

/**
 * User: Sander
 * Date: 26/02/13 11:35
 */
public class InvitedTripsFragment extends ListFragment {
    private TripsListAdapter tripsListAdapter;
    private Trip[] trips;
    private SharedPreferences settings;

    private void initSettings() {
        settings = getActivity().getSharedPreferences(Utilities.PREFS_NAME, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.invited_trips_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.getListView().setItemsCanFocus(false);

        initSettings();
        downloadList();
    }

    private void downloadList() {
        InvitedTripsTask invitedTripsTask = new InvitedTripsTask();
        invitedTripsTask.execute();
    }

    private void fillList() {
        try {
            tripsListAdapter = new TripsListAdapter(getActivity(), trips);
            setListAdapter(tripsListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Trip trip = trips[position];
        Intent intent = new Intent(getActivity(), InvitationActivity.class);
        intent.putExtra("trip", trip);
        startActivity(intent);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.trips_menu, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_refresh:
//                lastUpdate = 0L;
//                downloadList();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public class InvitedTripsTask extends AsyncTask<String, String, List<Trip>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), getString(R.string.downloading_progress_title), getString(R.string.downloading_progress_desc), true, false);
        }

        @Override
        protected List<Trip> doInBackground(String... data) {
            try {
                RestHttpConnection restHttpConnection = new RestHttpConnection();
                String result = restHttpConnection.doGet(String.format(Utilities.INVITED_TRIPS_ADDRESS, settings.getInt("userId", 0)));

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
            InvitedTripsFragment.this.trips = trips.toArray(new Trip[trips.size()]);
            fillList();
        }
    }
}