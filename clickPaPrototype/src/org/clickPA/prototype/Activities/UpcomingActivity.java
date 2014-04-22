package org.clickPA.prototype.Activities;

import java.io.Serializable;
import java.util.ArrayList;

import org.clickPA.prototype.Event;
import org.clickPA.prototype.EventAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import clickPA.prototype.R;

/**
 * Accessed from the home screen. Shows a ListView of upcoming events.
 * 
 * @author Charlie Mihran
 * @see Event
 */

public class UpcomingActivity extends Activity {
	ListView list;
	ArrayList<Event> events;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upcoming);
		setTitle("Upcoming Events");
		
		Intent intent = getIntent();
		events = (ArrayList<Event>) intent.getSerializableExtra("events");
		list = (ListView) findViewById(R.id.listview);
		CreateListView();

	}

	private void CreateListView() {
		list.setAdapter(new EventAdapter(UpcomingActivity.this, events));
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

}