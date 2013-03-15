package be.kdg.android.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import be.kdg.android.R;
import be.kdg.android.entities.Trip;
import be.kdg.android.fragments.ChatFragment;
import be.kdg.android.fragments.StopListFragment;
import be.kdg.android.fragments.StopMapFragment;
import be.kdg.android.networking.RestHttpConnection;
import be.kdg.android.utilities.Utilities;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Sander
 * Date: 10/03/13 13:51
 */
public class RegisteredTripActivity extends ListActivity {
    private Trip trip;

    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trip = (Trip) getIntent().getSerializableExtra("trip");

        initSettings();
        initControls();
    }

    private void initSettings() {
        settings = getSharedPreferences(Utilities.PREFS_NAME, 0);
        settingsEditor = settings.edit();
    }

    private void initControls() {
        setTitle(trip.getName());

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.removeAllTabs();

        ActionBar.Tab tab = actionBar
                .newTab()
                .setText(R.string.stop_list_tab_name)
                .setIcon(R.drawable.icon_all_trips_tab)
                .setTabListener(new CustomTabListener<StopListFragment>(this, "stop_list_layout", StopListFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.stop_map_tab_name)
                .setIcon(R.drawable.icon_map_tab)
                .setTabListener(new CustomTabListener<StopMapFragment>(this, "", StopMapFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.chat_tab_name)
                .setIcon(getResources().getDrawable(R.drawable.icon_chat_tab))
                .setTabListener(new CustomTabListener<ChatFragment>(this, "chat_layout", ChatFragment.class));
        actionBar.addTab(tab);
    }

    public final Trip getTrip() {
        return trip;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                Intent parentActivityIntent = new Intent(this, MainActivity.class);
                parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(parentActivityIntent);
                finish();
                return true;
            case R.id.trip_start_menu:
                StartTripTask startTripTask = new StartTripTask();
                startTripTask.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.registered_menu, menu);
        return true;
    }

    public class StartTripTask extends AsyncTask<String, String, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(RegisteredTripActivity.this, getString(R.string.downloading_progress_title), getString(R.string.downloading_progress_desc), true, false);
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                Integer tripId = trip.getId();
                Integer userId = settings.getInt("userId", 0);

                params.add(new BasicNameValuePair("tripId", tripId.toString()));
                params.add(new BasicNameValuePair("userId", userId.toString()));

                RestHttpConnection restHttpConnection = new RestHttpConnection();
                String result = restHttpConnection.doPostWithResult(Utilities.START_TRIP_ADDRESS, params);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
        }
    }
}
