package comp3111.webscraper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Item {
	private String title ; 
	private double price ;
	private String url ;
	private Date date;
	
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
	public void setDate(String stringdate) throws ParseException {
		DateFormat format = new SimpleDateFormat("MMM d", Locale.ENGLISH);
		Date date = format.parse(stringdate);
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}

}
