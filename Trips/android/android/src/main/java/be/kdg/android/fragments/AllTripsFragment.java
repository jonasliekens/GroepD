package be.kdg.android.fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;
import be.kdg.android.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import be.kdg.android.entities.Trip;
import be.kdg.android.networking.RestHttpConnection;
import be.kdg.android.utilities.Utilities;

import java.io.IOException;
import java.util.List;

/**
 * User: Sander
 * Date: 26/02/13 11:30
 */
public class AllTripsFragment extends ListFragment {
    private ListView listView;
    private TripsListAdapter tripsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alltrips, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AllTripsTask allTripsTask = new AllTripsTask();
        allTripsTask.execute();
    }

    private void fillList(List<Trip> trips) {
        try {
        //listView = (ListView) getView().findViewById(R.id.alltrips_list);
        tripsListAdapter = new TripsListAdapter(getActivity(), trips.toArray(new Trip[trips.size()]));
        //listView.setAdapter(tripsListAdapter);
        setListAdapter(tripsListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class AllTripsTask extends AsyncTask<String, String, List<Trip>> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), getString(R.string.downloading_progress_title),getString(R.string.downloading_progress_desc), true, false);
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
            fillList(trips);
        }
    }
}