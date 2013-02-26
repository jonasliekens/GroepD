package be.kdg.android;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import be.kdg.android.fragments.AllTripsFragment;
import be.kdg.android.fragments.ChatFragment;
import be.kdg.android.fragments.MyTripsFragment;
import be.kdg.android.login.LoginActivity;

/**
 * User: Sander
 * Date: 24/02/13 - 18:41
 */
public class TripsActivity extends Activity {
    private SharedPreferences settings;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initControls();
        checkLogin();
    }

    private void initControls() {
        settings = getSharedPreferences(Constants.PREFS_NAME, 0);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab tab = actionBar
                .newTab()
                .setText(R.string.alltrips_name)
                .setIcon(R.drawable.icon_alltrips_tab)
                .setTabListener(new TripsTabListener<AllTripsFragment>(this, "alltrips", AllTripsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.mytrips_name)
                .setIcon(R.drawable.icon_mytrips_tab)
                .setTabListener(new TripsTabListener<MyTripsFragment>(this, "mytrips", MyTripsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.chat_name)
                .setIcon(getResources().getDrawable(R.drawable.icon_chat_tab))
                .setTabListener(new TripsTabListener<ChatFragment>(this, "chat", ChatFragment.class));
        actionBar.addTab(tab);
    }

    private void checkLogin() {
        boolean loggedIn = settings.getBoolean("loggedIn", false);
        if (!loggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, Constants.LOGIN_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.LOGIN_REQUEST) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("loggedIn", true);
            editor.commit();
        }
    }
}