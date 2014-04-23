package org.clickPA.prototype.Activities;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.clickPA.prototype.Constants;
import org.clickPA.prototype.Event;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import clickPA.prototype.R;

/**
 * Main activity in project. Displays 4 buttons to be used as navigation
 * throughout the app. Also includes a AsyncTask to download
 * 
 * @author Charlie Mihran
 * 
 */

public class HomeScreenActivity extends Activity {
	// Tag used in logging
	private static final String TAG = "EventAdd";

	// Holds all the events downloaded to pass onto other activies
	private ArrayList<Event> events = new ArrayList<Event>();
	// Contains all the elements JSOUP picks up
	Elements elements;

	// Used to hold menu to add about tab later on
	Menu menuInst;

	// Used to display loading dialog to user
	ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_home_screen);
		setTitle("clickPA Mobile");

		if (isOnline(this)) {
			progress = new ProgressDialog(this);
			progress.setTitle("Loading");
			progress.setMessage("Downloading events ...");
			progress.show()
			;setUpElements();
		} else {
			setContentView(R.layout.activity_blank);
			TextView txt = (TextView) findViewById(R.id.blankText);
			txt.setText("Couldn't connect to the internet. Please check your connection and restart the app.");
		}
		
	}

	// Checks to see
	public boolean isOnline(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();

		if (ni != null && ni.isConnected())
			return true;
		else
			return false;
	}

	/* ------------ Populate event with Events from Website ------------ */
	// The following few methods populate the events ArrayList with events from
	// the clickPA website.
	private void setUpElements() {
		DownloadTask task = new DownloadTask();
		task.execute("http://clickpa.org/calendar-2/action~posterboard/");

	}

	// This class used to grab information from the clickPa website.
	// It can grab things such as titles, times, dates, ect.
	private class DownloadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) {
			HttpResponse response = null;
			HttpGet httpGet = null;
			HttpClient mHttpClient = null;
			String s = "";

			try {
				if (mHttpClient == null) {
					mHttpClient = new DefaultHttpClient();
				}

				httpGet = new HttpGet(urls[0]);
				response = mHttpClient.execute(httpGet);
				s = EntityUtils.toString(response.getEntity(), "UTF-8");

			} catch (IOException e) {
				e.printStackTrace();
			}
			return s;
		}

		@Override
		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
			progress.show();
		}

		@Override
		protected void onPostExecute(String result) {
			Document doc = Jsoup.parse(result);
			elements = doc.select("div.ai1ec-event");
			for (Element entry : elements) {
				Element titleElement = entry.select(".ai1ec-event-title")
						.first();
				Element timeElement = entry.select(".ai1ec-event-time").first();
				Element dateMonElement = entry.select(".ai1ec-month").first();
				Element dateNnElement = entry.select(".ai1ec-day").first();
				Element dateDayElement = entry.select(".ai1ec-weekday").first();
				Element descElement = entry.select(".ai1ec-event-description")
						.first();
				// Element imageElement =
				// entry.select(".ai1ec-event-avatar timely  ai1ec-content_img ai1ec-portrait").first();
				Element imageURL = entry.select("[src]").first();
				String img = imageURL.attr("abs:src");

				String[] eventParams = new String[Constants.NUM_INDICIES];
				eventParams[Constants.TITLE_AND_LOC] = titleElement.text();
				eventParams[Constants.TIME] = timeElement.text();
				eventParams[Constants.MON] = dateMonElement.text();
				eventParams[Constants.NN] = dateNnElement.text();
				eventParams[Constants.DAY] = dateDayElement.text();
				eventParams[Constants.DESC] = descElement.text();
				eventParams[Constants.IMG] = img;

				Event event = new Event(eventParams);

				if (Constants.LOGGING)
					LogEvent(eventParams);
				if (!event.hasPassed())
					events.add(event);

			}

			setProgressBarIndeterminateVisibility(false);
			progress.dismiss();
			getMenuInflater().inflate(R.menu.home_screen, menuInst);
		}

		private void LogEvent(String[] eventParams) {

			Log.d(TAG, "Title and Loc: " + eventParams[Constants.TITLE_AND_LOC]);
			Log.d(TAG, "Time: " + eventParams[Constants.TIME]);
			Log.d(TAG, "Mon: " + eventParams[Constants.MON]);
			Log.d(TAG, "NN: " + eventParams[Constants.NN]);
			Log.d(TAG, "Day: " + eventParams[Constants.DAY]);
			Log.d(TAG, "Desc: " + eventParams[Constants.DESC]);
			Log.d(TAG, "Img: " + eventParams[Constants.IMG]);

		}
	}

	/* ------------ About Tab ------------ */
	// The following 3 methods are used to created the action bar tab which
	// leads to the about page
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuInst = menu;
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			openAbout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void openAbout() {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	/* ------------ Starting Other Activities ------------ */
	// These 3 methods are invoked when pressing a button on the home screen
	// (see relevant XML file)
	public void chooseUpcoming(View view) {
		buttonPress(Constants.UPCOMING);
	}

	public void chooseCalendar(View view) {
		buttonPress(Constants.CALENDAR);
	}

	public void chooseSaved(View view) {
		buttonPress(Constants.SAVED);
	}

	/**
	 * Called when pressing a button on the home screen
	 * 
	 * @param activity
	 *            The integer used to describe which activity we are starting,
	 *            see top of class for the 4 predefined ints
	 */
	public void buttonPress(int activity) {
		Intent i = new Intent(this, UpcomingActivity.class);
		switch (activity) {
		case Constants.UPCOMING:
			i = new Intent(this, UpcomingActivity.class);
			break;
		case Constants.SAVED:
			i = new Intent(this, SavedActivity.class);
			break;
		case Constants.MAP:
			// TODO Create the map activity first!
			break;
		case Constants.CALENDAR:
			i = new Intent(this, CalendarActivity.class);
			break;
		default:
			throw new IllegalArgumentException();
		}
		i.putExtra("events", events);
		startActivity(i);
	}
}
