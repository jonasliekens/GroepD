package be.kdg.android.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
* User: Sander
* Date: 10/03/13 - 17:51
*/
public class CustomTabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    public CustomTabListener(Activity mActivity, String mTag, Class<T> mClass) {
        this.mActivity = mActivity;
        this.mTag = mTag;
        this.mClass = mClass;
    }

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Fragment currentFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);

        if (mFragment == null && currentFragment == null) {
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment, mTag);
        } else if (mFragment != null) {
            //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft.attach(mFragment);
        } else if (currentFragment != null) {
            ft.attach(currentFragment);
            mFragment = currentFragment;
        }
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft.detach(mFragment);
        }
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}
