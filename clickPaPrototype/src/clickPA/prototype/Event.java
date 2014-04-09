package clickPA.prototype;

import java.io.Serializable;

/*
 * Basic prototype of how to store an event into an object.
 * This class subject to change/overhaul once we figure out how to pull data from Wordpress.
 */

public class Event implements Serializable {
	private static final long serialVersionUID = 7105009895060374075L;

	private String title;
	private String when;
	private boolean repeats;
	private String repeatDates;
	private String where;
	private String cost;

	/* ------------ Constructors ------------ */
	// Non Repeating Event
	public Event(String title, String when, boolean repeats, String where,
			String cost) {
		this.title = title;

		this.when = when;

		// If no repeating dates are given, then repeats should always be false
		if (repeats)
			throw new IllegalArgumentException();
		this.repeats = repeats;
		repeatDates = "Not Repeating";

		this.where = where;

		this.cost = cost;
		if (cost.equals("0"))
			cost = "Free";
	}

	// Repeating Event
	public Event(String title, String when, boolean repeats,
			String repeatDates, String where, String cost) {
		this.title = title;

		this.when = when;

		// If repeat dates are given, then repeats should always be true
		if (!repeats)
			throw new IllegalArgumentException();
		this.repeats = repeats;
		this.repeatDates = repeatDates;

		this.where = where;

		this.cost = cost;
		if (cost.equals("0"))
			cost = "Free";
	}

	/* ------------ Methods ------------ */
	// toString
	public String toString() {
		return when + "\n" + title + "\n@ " + where;
	}

	// Getters
	public String getTitle() {
		return title;
	}

	public String getWhen() {
		return when;
	}

	public boolean isRepeat() {
		return repeats;
	}

	public String getRepeatDates() {
		return repeatDates;
	}

	public String getWhere() {
		return where;
	}

	public String getCost() {
		return cost;
	}
}
