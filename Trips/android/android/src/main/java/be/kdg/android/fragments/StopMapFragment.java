package be.kdg.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.MapFragment;

/**
 * User: Sander
 * Date: 10/03/13 - 19:21
 */
public class StopMapFragment extends MapFragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
