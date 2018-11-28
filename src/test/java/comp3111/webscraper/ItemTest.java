package comp3111.webscraper;

import org.junit.Test;
import static org.junit.Assert.*;


public class ItemTest {

	@Test
	public void equalityTest() throws Exception{
		Item item1 = new Item();
		item1.setTitle("ABCDE");
		item1.setDate(WebScraper.formatCraigslistDate("Nov 11"));
		item1.setPrice(1.1);
		item1.setUrl("abc");
		item1.setWebsite("Craiglist");

		Item item2 = new Item();
		item2.setTitle("ABCDE");
		item2.setDate(WebScraper.formatCraigslistDate("Nov 11"));
		item2.setPrice(1.1);
		item2.setUrl("abc");
		item2.setWebsite("Craiglist");

		Item item3 = new Item();
		item3.setTitle("DDD");
		item3.setDate(WebScraper.formatCraigslistDate("Nov 11"));
		item3.setPrice(1.1);
		item3.setUrl("abc");
		item3.setWebsite("Craiglist");

		Item item4 = new Item();
		item4.setTitle("ABCDE");
		item4.setDate(WebScraper.formatCraigslistDate("Nov 15"));
		item4.setPrice(1.1);
		item4.setUrl("abc");
		item4.setWebsite("Craiglist");

		Item item5 = new Item();
		item5.setTitle("ABCDE");
		item5.setDate(WebScraper.formatCraigslistDate("Nov 11"));
		item5.setPrice(3.3);
		item5.setUrl("abc");
		item5.setWebsite("Craiglist");

		Item item6 = new Item();
		item6.setTitle("ABCDE");
		item6.setDate(WebScraper.formatCraigslistDate("Nov 11"));
		item6.setPrice(1.1);
		item6.setUrl("ggggg");
		item6.setWebsite("Craiglist");

		Item item7 = new Item();
		item7.setTitle("ABCDE");
		item7.setDate(WebScraper.formatCraigslistDate("Nov 11"));
		item7.setPrice(1.1);
		item7.setUrl("abc");
		item7.setWebsite("DCWV");

		Object obj = new Object();

		item1.printItem();

		assertTrue(item1.equals(item2));
		assertFalse(item1.equals(item3));
		assertFalse(item1.equals(item4));
		assertFalse(item1.equals(item5));
		assertFalse(item1.equals(item6));
		assertFalse(item1.equals(item7));
		assertFalse(item1.equals(obj));
	}
}
