package be.kdg.android.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import be.kdg.android.R;
import be.kdg.android.entities.Trip;

/**
 * User: Sander
 * Date: 7/03/13 15:05
 */
public class TripsListAdapter extends ArrayAdapter<Trip> {
    private final Context context;
    private final Trip[] trips;

    public TripsListAdapter(Context context, Trip[] trips) {
        super(context, R.layout.trip_row_layout, trips);
        this.context = context;
        this.trips = trips;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.trip_row_layout, parent, false);

        Trip trip = trips[position];

        TextView tripName = (TextView) rowLayout.findViewById(R.id.list_trip_name);
        TextView tripDate = (TextView) rowLayout.findViewById(R.id.list_trip_date);
        TextView stopCount = (TextView) rowLayout.findViewById(R.id.list_trip_stops_count);

        tripName.setText(trip.getName());
        tripDate.setText("datum");

        int count = trip.getStops().size();
        // getQuantityString is used for strins with plurals (e.g. 1 stop, 2 stops, ...)
        // first parameter is the string to show, second parameter is the quantity to test and the third is
        // the quantity to show in the string format (%d)
        stopCount.setText(context.getResources().getQuantityString(R.plurals.trips_stops_count, count, count));

        return rowLayout;
    }
}
