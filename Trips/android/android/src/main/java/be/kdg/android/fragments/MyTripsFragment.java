package be.kdg.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import be.kdg.android.R;

/**
 * User: Sander
 * Date: 26/02/13 11:35
 */
public class MyTripsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_trips_layout, container, false);
    }
}