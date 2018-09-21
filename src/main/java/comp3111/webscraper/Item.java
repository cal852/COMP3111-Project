package comp3111.webscraper;



public class Item {
	private String title ; 
	private double price ;
	private String url ;
	private String date;
	
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
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return date;
	}

}
