package be.kdg.android;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import be.kdg.android.fragments.AllTripsFragment;
import be.kdg.android.fragments.ChatFragment;
import be.kdg.android.fragments.MyTripsFragment;

/**
 * User: Sander
 * Date: 24/02/13 - 18:41
 */
public class TripsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
}