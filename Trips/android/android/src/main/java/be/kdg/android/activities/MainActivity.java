package be.kdg.android.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import be.kdg.android.R;
import be.kdg.android.fragments.AllTripsFragment;
import be.kdg.android.fragments.ChatFragment;
import be.kdg.android.fragments.MyTripsFragment;
import be.kdg.android.utilities.Utilities;

/**
 * User: Sander
 * Date: 24/02/13 - 18:41
 */
public class MainActivity extends Activity {
    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSettings();
        checkLogin();
    }

    private void startApp() {
        initControls();

        if (!Utilities.isOnline(this.getApplicationContext())) {
            Toast.makeText(this, R.string.network_noconnection, Toast.LENGTH_LONG).show();
        }
    }

    private void initSettings() {
        settings = getSharedPreferences(Utilities.PREFS_NAME, 0);
        settingsEditor = settings.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initControls() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // actionBar.setDisplayShowTitleEnabled(false);

        actionBar.removeAllTabs();

        ActionBar.Tab tab = actionBar
                .newTab()
                .setText(R.string.alltrips_name)
                .setIcon(R.drawable.icon_alltrips_tab)
                .setTabListener(new TripsTabListener<AllTripsFragment>(this, "all_trips_layout", AllTripsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.mytrips_name)
                .setIcon(R.drawable.icon_mytrips_tab)
                .setTabListener(new TripsTabListener<MyTripsFragment>(this, "my_trips_layout", MyTripsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.chat_name)
                .setIcon(getResources().getDrawable(R.drawable.icon_chat_tab))
                .setTabListener(new TripsTabListener<ChatFragment>(this, "chat_layout", ChatFragment.class));
        actionBar.addTab(tab);
    }

    private void checkLogin() {
        boolean loggedIn = settings.getBoolean("loggedIn", false);
        if (!loggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, Utilities.LOGIN_REQUEST);
        } else {
            startApp();
        }
    }

    private void logout() {
        settingsEditor.putBoolean("loggedIn", false);
        settingsEditor.commit();
        checkLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Utilities.LOGIN_REQUEST) {
            settingsEditor.putBoolean("loggedIn", true);
            settingsEditor.commit();
            startApp();
        } else {
            finish();
        }
    }

    public class TripsTabListener<T extends Fragment> implements ActionBar.TabListener {
        private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        public TripsTabListener(Activity activity, String tag, Class<T> mClass) {
            this.mActivity = activity;
            this.mTag = tag;
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
}