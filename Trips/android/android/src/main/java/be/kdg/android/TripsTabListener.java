package be.kdg.android;

import android.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * User: Sander
 * Date: 26/02/13 12:12
 */
public class TripsTabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment fragment;
    private final Activity activity;
    private final String tag;
    private final Class<T> mClass;

    public TripsTabListener(Activity activity, String tag, Class<T> mClass) {
        this.activity = activity;
        this.tag = tag;
        this.mClass = mClass;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragment == null) {
            fragment = Fragment.instantiate(activity, mClass.getName());
            ft.add(android.R.id.content, fragment, tag);
        } else {
            ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
            ft.attach(fragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragment != null) {
            ft.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
            ft.detach(fragment);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}

