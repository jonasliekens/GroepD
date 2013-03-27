package be.kdg.android.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import be.kdg.android.entities.Trip;
import be.kdg.android.fragments.AllTripsFragment;
import be.kdg.android.fragments.ChatFragment;
import be.kdg.android.fragments.StopListFragment;
import be.kdg.android.fragments.StopMapFragment;
import be.kdg.android.listadapters.StopsListAdapter;
import com.qualcomm.QCARSamples.CloudRecognition.CloudReco;
import be.kdg.android.R;
/**
 * User: Sander
 * Date: 10/03/13 13:51
 */
public class TripActivity extends ListActivity {
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trip = (Trip) getIntent().getSerializableExtra("trip");

        initControls();
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
    }

    public final Trip getTrip() {
        return trip;
    }

    public void startVuforia(View view){
        Intent intent = new Intent(this, CloudReco.class);
        startActivity(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentActivityIntent = new Intent(this, MainActivity.class);
                parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(parentActivityIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
