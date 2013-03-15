package be.kdg.android.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.qualcomm.QCARSamples.CloudRecognition.R;
import be.kdg.android.entities.Stop;

/**
 * User: Sander
 * Date: 10/03/13 14:24
 */
public class StopsListAdapter extends ArrayAdapter<Stop> {
    private final Context context;
    private final Stop[] stops;

    public StopsListAdapter(Context context, Stop[] stops) {
        super(context, R.layout.trip_row_layout, stops);
        this.context = context;
        this.stops = stops;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.stop_row_layout, parent, false);

        Stop stop = stops[position];

        TextView stopName = (TextView) rowLayout.findViewById(R.id.list_stop_name);

        stopName.setText(stop.getName());

        return rowLayout;
    }
}
