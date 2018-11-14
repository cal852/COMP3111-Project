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
	public void testItem() {
		Item exp1 = new Item();
		exp1.setTitle("ABCDE");
		exp1.setDate("Nov");
		exp1.setPrice(1.1);
		exp1.setUrl("abc");

		Item act1 = new Item();
		act1.setTitle("ABCDE");
		act1.setDate("Nov");
		act1.setPrice(1.1);
		act1.setUrl("abc");

		assertEquals(exp1, act1);
	}
}
