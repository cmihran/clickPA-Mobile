package org.clickPA.prototype;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

/*
 * Basic prototype of how to store an event into an object.
 */

public class Event implements Serializable {
	private static final long serialVersionUID = 7105009895060374075L;
	private static final String TAG = "EventCreate";

	private String title;
	private String time;
	private String location;
	private String cost;
	private boolean isSaved;
	private String description;
	private String imgURL;

	private String mon;
	private String nn;
	private String day;

	/* ------------ Constructors ------------ */
	// Construct from Elements
	public Event(String... elements) {
		String titleAndLoc = elements[Constants.TITLE_AND_LOC];
		String time = elements[Constants.TIME];
		this.mon = elements[Constants.MON];
		this.nn = elements[Constants.NN];
		this.day = elements[Constants.DAY];
		this.description = elements[Constants.DESC];
		this.imgURL = elements[Constants.IMG];

		String title = "";
		String location = "";
		int atIndex = -1;
		for (int i = 0; i < titleAndLoc.length() - 1; i++) {
			if (titleAndLoc.substring(i, i + 1).equals("@")) {
				atIndex = i;
			}
		}
		if (atIndex == -1) {
			title = titleAndLoc;
			location = "";
		} else {
			title = titleAndLoc.substring(0, atIndex - 1);
			location = titleAndLoc.substring(atIndex);
		}

		this.title = title;
		this.time = time;
		this.location = location;
	}

	/* ------------ Methods ------------ */
	// toString
	public String toString() {
		return time + "\n" + title + "\n@ " + location;
	}

	// Getters
	public String getTitle() {
		return title;
	}

	public String getTime() {
		return time;
	}

	public String getLocation() {
		return location;
	}

	public String getMon() {
		return mon;
	}

	public String getNN() {
		return nn;
	}

	public String getDay() {
		return day;
	}

	public String getCost() {
		return cost;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public String getDateShorthand() {
		return mon + "\n" + nn + "\n" + day + "\n";
	}

	public String getDescription() {
		return description;
	}

	public String getImage() {
		return imgURL;
	}

	// Modifiers
	public void toggleSave() {
		isSaved = !isSaved;
	}

	// Calculations
	public boolean hasPassed() {
		String today = getCurrentDate();
		// Find where the dash is
		// today = "MMM-dd"
		int dashIndex = -1;
		for (int i = 0; i < today.length(); i++) {
			if (today.substring(i, i + 1).equals("-")) {
				dashIndex = i;
				break;
			}
		}
		if (dashIndex == -1)
			if (Constants.LOGGING)
				Log.e(TAG, "Error in hasPassed, couldn't find dash for event: "
						+ getTitle());

		// Get variables about today and the event's date
		String curMon = today.substring(0, dashIndex);
		int curMonVal = getMonVal(curMon);

		String curDay = today.substring(dashIndex + 1, today.length());
		int curDayVal = Integer.valueOf(curDay);

		int eventMonVal = getMonVal(getMon());
		int eventDayVal = Integer.valueOf(getNN());

		// Decide if the event has passed
		if (eventMonVal == curMonVal) {
			if(eventDayVal == curDayVal)
				return false;
			else return(eventDayVal < curDayVal);
		} 
		return (eventMonVal < curMonVal);
	}

	public String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd");
		return sdf.format(cal.getTime());
	}

	public int getMonVal(String paramMon) {
		int retval;
		switch (paramMon) {
		case "Jan":
			retval = Constants.JAN;
			break;
		case "Feb":
			retval = Constants.FEB;
			break;
		case "Mar":
			retval = Constants.MAR;
			break;
		case "Apr":
			retval = Constants.APR;
			break;
		case "May":
			retval = Constants.MAY;
			break;
		case "Jun":
			retval = Constants.JUN;
			break;
		case "Jul":
			retval = Constants.JUL;
			break;
		case "Aug":
			retval = Constants.AUG;
			break;
		case "Sep":
			retval = Constants.SEP;
			break;
		case "Oct":
			retval = Constants.OCT;
			break;
		case "Nov":
			retval = Constants.NOV;
			break;
		case "Dec":
			retval = Constants.DEC;
			break;
		default:
			if (Constants.LOGGING)
				Log.e(TAG,
						"Error in hasPassed method, can't find month for event: "
								+ getTitle());
			retval = -1;
			break;
		}
		return retval;
	}

}
