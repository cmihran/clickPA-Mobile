package org.clickPA.prototype.Activities;

import org.clickPA.prototype.Event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import clickPA.prototype.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Accessed from tapping an Event in the upcoming list. Used to display an event
 * in greater detail.
 * 
 * @author Charlie Mihran
 * 
 */
public class EventDisplayActivity extends Activity {
	Event event;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_display);
		// Show the Up button in the action bar.
		// setupActionBar();

		// Get the intent used to start this activity
		Intent i = getIntent();
		// Create a new event based on the serializable extra passed with the
		// intent
		event = (Event) i.getSerializableExtra("event");
		// Set the title of the page to the event title
		setTitle(event.getTitle());

		// Image
		ImageView image = (ImageView) findViewById(R.id.EventDisplayImage);
		ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
    	.cacheInMemory(true)
    	.build();
		if(!event.getImage().equals("")){
			ImageAware imageAware = new ImageViewAware(image, false);
			imageLoader.displayImage(event.getImage(), imageAware, options);
		}
		
		
		// Title
		TextView title = (TextView) findViewById(R.id.EventDisplayTitle);
		title.setText(event.getTitle());

		// Location
		TextView location = (TextView) findViewById(R.id.EventDisplayLocation);
		location.setText(event.getLocation());

		// Description
		TextView desc = (TextView) findViewById(R.id.EventDisplayDesc);
		desc.setText("\n" + event.getDescription() + "\n");
		LayoutParams descParams = (LayoutParams) desc.getLayoutParams();
		if (image.getHeight() > location.getHeight()) {
			descParams.addRule(RelativeLayout.BELOW, R.id.EventDisplayImage);
		} else {
			descParams.addRule(RelativeLayout.BELOW, R.id.EventDisplayLocation);
		}
		desc.setLayoutParams(descParams);

	}

}

/* ------------ BUGGY CODE ------------ */
// The below code is used to set up parent hierarchy but it crashes the app
// I think this is because it doesn't have the events ArrayList defined?

// /**
// * Set up the {@link android.app.ActionBar}.
// */
// private void setupActionBar() {
//
// getActionBar().setDisplayHomeAsUpEnabled(true);
//
// }
//
// /*
// * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate
// the
// * menu; this adds items to the action bar if it is present.
// * getMenuInflater().inflate(R.menu.event_display, menu); return true; }
// */
//
// @Override
// public boolean onOptionsItemSelected(MenuItem item) {
// switch (item.getItemId()) {
// case android.R.id.home:
// // This ID represents the Home or Up button. In the case of this
// // activity, the Up button is shown. Use NavUtils to allow users
// // to navigate up one level in the application structure. For
// // more details, see the Navigation pattern on Android Design:
// //
// //
// http://developer.android.com/design/patterns/navigation.html#up-vs-back
// //
// NavUtils.navigateUpFromSameTask(this);
// return true;
// }
// return super.onOptionsItemSelected(item);
// }
