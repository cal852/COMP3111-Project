package comp3111.webscraper;


import org.junit.Test;
import junit.framework.Assert;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class ItemTest {

	@Test
	public void testSetTitle() {
		Item i = new Item();
		i.setTitle("ABCDE");
		assertEquals(i.getTitle(), "ABCDE");
	}
	
	@Test
	public void testSetPrice() {
		Item i = new Item();
		i.setPrice(50.0);
		assertEquals(i.getPrice(), 50.0, 0);
	}
	
	@Test
	public void testSetUrl() {
		Item i = new Item();
		i.setUrl("https://course.cse.ust.hk/comp3111/");
		assertEquals(i.getUrl(), "https://course.cse.ust.hk/comp3111/");
	}
	
	@Test
	public void testSetDate() throws ParseException {
		Item i = new Item();
		i.setDate("Nov 11");
		SimpleDateFormat format = new SimpleDateFormat("MMM dd", Locale.US);
		assertEquals(format.format(i.getDate()), "Nov 11");
	}
}
