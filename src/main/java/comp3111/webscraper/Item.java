package comp3111.webscraper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.scene.control.Hyperlink;

public class Item {
	private String title ;
	private double price ;
	private String url ;
	private String date;
	private String linkUrl;

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
//		DateFormat format = new SimpleDateFormat("MMM d", Locale.ENGLISH);
//		Date date = format.parse(stringdate);
		this.date = stringdate;
	}

//	public Date getDate() {
	public String getDate() {
		return date;
	}

//	public Hyperlink getLinkUrl() {
	public String getLinkUrl() {
        return linkUrl;
    }


    public void setLinkUrl(String websiteUrl) {
		this.linkUrl=websiteUrl;
//        this.linkUrl = new Hyperlink(websiteUrl);
    }

    public void printItem(){
		System.out.println("Item Title: " + title);
		System.out.println("Price: " +price + " Date: " + date + " URL: " + url);
	}
//    @Override
//	public Item clone(){
//		try{
//			return (Item) super.clone();
//		}catch(CloneNotSupportedException e){
//			return null;
//		}
//	}
//
//	@Override
//	public int compareTo(@NotNull Item _item){
//		return Double.valueOf(price).compareTo(_item.price);
//	}
//
//	public void printItem(Item[] items){
//
//	}
//	public static void main(String[] args) {
//		Item[] itemArray = new Item[5];
//
//	}
}

