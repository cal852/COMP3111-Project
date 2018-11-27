package comp3111.webscraper;


import org.junit.Test;

import static comp3111.webscraper.WebScraper.formatCraigslistDate;
import static org.junit.Assert.*;


public class ItemTest {

	@Test
	public void equalityTest() throws Exception{
		Item item1 = new Item();
		item1.setTitle("ABCDE");
		item1.setDate(formatCraigslistDate("Nov 11"));
		item1.setPrice(1.1);
		item1.setUrl("abc");
		item1.setWebsite("Craiglist");

		Item item2 = new Item();
		item2.setTitle("ABCDE");
		item2.setDate(formatCraigslistDate("Nov 11"));
		item2.setPrice(1.1);
		item2.setUrl("abc");
		item2.setWebsite("Craiglist");

		item1.printItem();
		item2.printItem();

		assertEquals(item1,item2);
	}
}
