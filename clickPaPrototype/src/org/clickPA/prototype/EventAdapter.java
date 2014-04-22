package org.clickPA.prototype;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
 * Subclass of ArrayAdapter of type Event. Used to inflate a ListView with
 * properties from an Event object.
 * 
 * @author Charlie Mihran
 * @see Event
 * @see ArrayAdapter
 */

public class EventAdapter extends ArrayAdapter<Event> {
	private ArrayList<Event> events;
	private Context context;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	public EventAdapter(Context context, ArrayList<Event> events) {
		super(context, R.layout.list_item_event, events);
		this.context = context;
		this.events = events;
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(
				R.drawable.ic_launcher).build();
		imageLoader = ImageLoader.getInstance();
		//imageLoader.init(ImageLoaderConfiguration.createDefault(context));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item_event, null, false);

		TextView titleView = (TextView) rowView.findViewById(R.id.EventTitle);
		TextView dateMonView = (TextView) rowView
				.findViewById(R.id.EventDateMon);
		TextView dateNNView = (TextView) rowView.findViewById(R.id.EventDateNN);
		TextView dateDayView = (TextView) rowView
				.findViewById(R.id.EventDateDay);
		ImageView img = (ImageView) rowView.findViewById(R.id.EventImg);
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
    	.cacheInMemory(true)
    	.build();

		titleView.setText(Html.fromHtml("<b><font color=\"#2A6496\">"
				+ events.get(position).getTitle() + "</font></b> "
				+ events.get(position).getLocation()));
		dateMonView.setText(events.get(position).getMon());
		dateNNView.setText(events.get(position).getNN());
		dateDayView.setText(events.get(position).getDay());
		if(!events.get(position).getImage().equals("")){
			ImageAware imageAware = new ImageViewAware(img, false);
			imageLoader.displayImage(events.get(position).getImage(), imageAware, options);
		}
		
		TextView separator = (TextView) rowView.findViewById(R.id.VerticalSeparator);
		LayoutParams sepParams = (LayoutParams) separator.getLayoutParams();
		sepParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		sepParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		separator.setLayoutParams(sepParams);
		
		return rowView;
	}
}
