package comp3111.webscraper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Item implements Comparable<Item> {
	private String title="";
	private double price;
	private String url="";
	private Date date;
	private String website="";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void printItem() {
		System.out.println("Item Title: " + title);
		System.out.println("Price: " + price + " Date: " + date.toString() + " URL: " + url);
	}

	@Override
	public int compareTo(Item _item) {
		return Double.valueOf(price).compareTo(_item.getPrice());
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(title, price, url, date);
	}
}

