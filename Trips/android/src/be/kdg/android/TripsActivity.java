package be.kdg.android;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class TripsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trips);

                final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // actionBar.setDisplayShowTitleEnabled(false);

        Tab tab = actionBar
                .newTab()
                .setText(R.string.all_trips)
                .setTabListener(new TripsTabListener<AllTripsFragment>(this, "alltrips", AllTripsFragment.class));
        actionBar.addTab(tab);

        tab = actionBar
                .newTab()
                .setText(R.string.all_trips)
                .setTabListener(new TripsTabListener<MyTripsFragment>(this, "mytrips", MyTripsFragment.class));
        actionBar.addTab(tab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
            case R.id.action_all_trips:
                intent = new Intent(this, AllTripsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.action_my_trips:
                intent = new Intent(this, MyTripsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_chat:
                intent = new Intent(this, ChatActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }     */
}
