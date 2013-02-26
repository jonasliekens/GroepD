package be.kdg.android.fragments;

import be.kdg.android.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: Sander
 * Date: 26/02/13 11:30
 */
public class AllTripsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alltrips, container, false);
    }
}