package comp3111.webscraper;

import java.util.Date;
import java.util.Objects;

/**
 * Item class that stores the data for the item searched in the website.
 * @author HYUN, Jeongseok
 */
public class Item implements Comparable<Item> {
	/**
	 * Default Title initialized empty String
	 */
	private String title="";

	/**
	 * Default price initialized zero
	 */
	private double price=0;

	/**
	 * Default url initialized empty String
	 */
	private String url="";

	/**
	 * Default date
	 */
	private Date date;

	/**
	 * Default name of website initialized empty String
	 */
	private String website="";

	/**
	 * Returns the title of this item
	 * @return the title of this item
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Replace the title of the item by the given title
	 * @param title - title to be stored in this item
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the price of this item
	 * @return the price of this item
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Replace the price of the item by the given price
	 * @param price - price to be stored in this item
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Returns the url of this item
	 * @return the url of this item
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Replace the url of the item by the given url
	 * @param url - url to be stored in this item
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns the date of this item
	 * @return the date of this item
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Replace the date of the item by the given date
	 * @param date - date to be stored in this item
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Returns the name of website of this item
	 * @return the name of website of this item
	 */
	public String getWebsite() {
		return this.website;
	}

	/**
	 * Replace the website of the item by the given website
	 * @param website - website to be stored in this item
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 *  Print the information of the item following the certain format
	 */
	public void printItem() {
		System.out.println("Item Title: " + title);
		System.out.println("Price: " + price + " Date: " + date.toString() + " URL: " + url);
	}

	/**
	 * Compares the given item with the current item by the price
	 * @param _item - Given item to be compared with the current item
	 * @return a negative integer,zero,or a positive integer as this item is less than, equal to, or greater than the specified item.
	 */
	@Override
	public int compareTo(Item _item) {
		return Double.valueOf(price).compareTo(_item.getPrice());
	}

	/**
	 * Compares the given object with the current object
	 * @param o - Given object to be compared with the current object
	 * @return true if it has same title, price, date, url and website. Otherwise, return false
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Item) {
			Item i = (Item) o;
			boolean title = this.title.equals(i.getTitle());
			boolean price = ((Double) this.getPrice()).equals((Double) i.getPrice());
			boolean date = this.date.equals(i.getDate());
			boolean url = this.url.equals(i.getUrl());
			boolean website = this.website.equals(i.getWebsite());

			return title && price && date && url && website;
		}
		return false;
	}

	/**
	 * Returns a hash code value for the object.
	 * @return hash code value for the object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(title, price, url, date);
	}
}

