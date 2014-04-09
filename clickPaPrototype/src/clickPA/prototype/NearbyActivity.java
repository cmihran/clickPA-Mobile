package clickPA.prototype;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
 * This class is used when the user presses the "Nearby" button on the home page.
 */

public class NearbyActivity extends Activity {
	ListView list;
	ArrayList<Event> events;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby);
		setTitle("Nearby Events");
		setupActionBar();
		
		events = new ArrayList<Event>();
		list = (ListView) findViewById(R.id.listview);
		CreateListView();

		/*
		 * Button eventButton1 = (Button) findViewById(R.id.eventButton1);
		 * eventButton1.setText(events.get(0).toString());
		 */
	}

	private void CreateListView() {
		// These are just a bunch of sample events that were added to test the functionality of the page
		events.add(new Event("Youth Speaks Out Opening Celebration",
				"March 22, 2014 @ 5:00 pm - 8:00 pm", false,
				"Palo Alto Art Center", "0"));
		for(int i = 2; i < 12; i++){
			events.add(new Event("event" + i,
					"Month Date, Year @ BeginTime - EndTime", false,
					"Location", "Cost"));
		}
		
		

		list.setAdapter(new ArrayAdapter<Event>(NearbyActivity.this, android.R.layout.simple_list_item_1,events));
        list.setOnItemClickListener(new OnItemClickListener()
          {
               @Override
               public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
               {
                   startEvent(events.get(arg2));
               }
          });
		
	}

	public void startEvent(Event event) {
		Intent i = new Intent(this, EventDisplayActivity.class);
		i.putExtra("event", (Serializable)event);
		startActivity(i);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
