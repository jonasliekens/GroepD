package be.kdg.android.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import be.kdg.android.R;
import be.kdg.android.entities.Stop;
import be.kdg.android.entities.Trip;
import be.kdg.android.listadapters.StopsListAdapter;
import com.google.android.gms.maps.MapFragment;

/**
 * User: Sander
 * Date: 10/03/13 13:51
 */
public class TripActivity extends ListActivity {
    private StopsListAdapter stopsListAdapter;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_layout);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        trip = (Trip) getIntent().getSerializableExtra("trip");
        setTitle(trip.getName());

        // Test!
        MapFragment fragment = new MapFragment();

        fillList();
    }

    private void fillList() {
        try {
            stopsListAdapter = new StopsListAdapter(this, trip.getStops().toArray(new Stop[trip.getStops().size()]));
            setListAdapter(stopsListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
