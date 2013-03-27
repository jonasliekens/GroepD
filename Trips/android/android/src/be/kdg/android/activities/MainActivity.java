package be.kdg.android.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import be.kdg.android.R;
import be.kdg.android.fragments.*;
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
        switch (item.getItemId()) {
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

        actionBar.removeAllTabs();

        ActionBar.Tab tab = actionBar
                .newTab()
                .setText(R.string.all_trips_tab_name)
                .setIcon(R.drawable.icon_all_trips_tab)
                .setTabListener(new CustomTabListener<AllTripsFragment>(this, "all_trips_layout", AllTripsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.my_trips_tab_name)
                .setIcon(R.drawable.icon_my_trips_tab)
                .setTabListener(new CustomTabListener<MyTripsFragment>(this, "my_trips_layout", MyTripsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.registered_trips_tab_name)
                .setIcon(R.drawable.icon_registered_trips_tab)
                .setTabListener(new CustomTabListener<RegisteredTripsFragment>(this, "registered_trips_layout", RegisteredTripsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.invited_trips_tab_name)
                .setIcon(R.drawable.icon_invitations_tab)
                .setTabListener(new CustomTabListener<InvitedTripsFragment>(this, "invited_trips_layout", InvitedTripsFragment.class));
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
}