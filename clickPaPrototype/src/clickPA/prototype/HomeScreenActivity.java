package clickPA.prototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/*
 * This class is the default activity, called upon startup.
 */

public class HomeScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		setTitle("clickPA Mobile");
	}

	// About Tab
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void openSettings() {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
	
	// These 3 methods are invoked when pressing a button on the home screen (see relevant XML file)
	public void chooseNearby(View view) {
		startActivity(new Intent(this, NearbyActivity.class));
	}

	public void chooseCalendar(View view) {
		startActivity(new Intent(this, CalendarActivity.class));
	}

	public void chooseSaved(View view) {
		startActivity(new Intent(this, SavedActivity.class));
	}

}
