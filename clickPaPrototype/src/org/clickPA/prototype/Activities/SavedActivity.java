package org.clickPA.prototype.Activities;

import java.io.Serializable;
import java.util.ArrayList;

import org.clickPA.prototype.Event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import clickPA.prototype.R;

/**
 * Accessed from the home screen. Displays a list of events saved locally to the
 * event.
 * 
 * @author Charlie Mihran
 * 
 */

public class SavedActivity extends Activity {
	ArrayList<Event> events;
	ArrayList<Event> eventsSaved;
	ListView list;

	// Eclipse complains about an unchecked cast to ArrayList but in this case
	// it doesn't matter since I know it will be coming in as ArrayList
	// Suppress the useless warning to get rid of the yellow squiggly
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved);
		// Show the Up button in the action bar.
		// setupActionBar();

		Intent intent = getIntent();
		events = (ArrayList<Event>) intent.getSerializableExtra("events");
		eventsSaved = new ArrayList<Event>();
		for (Event e : events) {
			if (e.isSaved())
				eventsSaved.add(e);
		}
		list = (ListView) findViewById(R.id.listview);
		TextView text = (TextView) findViewById(R.id.savedText);

		if (checkForSaves()) {
			text.setVisibility(View.GONE);
			CreateListView();
		}
	}

	private void CreateListView() {
		list.setAdapter(new ArrayAdapter<Event>(SavedActivity.this,
				android.R.layout.simple_list_item_1, eventsSaved));
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				startEvent(events.get(arg2));
			}
		});

	}

	public void startEvent(Event event) {
		Intent i = new Intent(this, EventDisplayActivity.class);
		i.putExtra("event", (Serializable) event);
		startActivity(i);
	}

	public boolean checkForSaves() {
		return (!(eventsSaved.size() == 0));
	}

	// /**
	// * Set up the {@link android.app.ActionBar}.
	// */
	// private void setupActionBar() {
	//
	// getActionBar().setDisplayHomeAsUpEnabled(true);
	//
	// }
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.saved, menu);
	// return true;
	// }
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

}
