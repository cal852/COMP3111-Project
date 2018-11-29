package comp3111.webscraper;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class FindElementTest {
    private List<Item> result;
    private FindElement findElement;

    @Before
    public void setup() throws Exception {
        result = new Vector<Item>();
        findElement = new FindElement();

        Item item1 = new Item();
        item1.setTitle("A");
        item1.setDate(WebScraper.formatCraigslistDate("Nov 17"));
        item1.setPrice(28.1);
        item1.setUrl("aaa");
        item1.setWebsite("Craiglist");
        result.add(item1);

        Item item2 = new Item();
        item2.setTitle("B");
        item2.setDate(WebScraper.formatCraigslistDate("Nov 11"));
        item2.setPrice(39.4);
        item2.setUrl("bbb");
        item2.setWebsite("Craiglist");
        result.add(item2);

        Item item3 = new Item();
        item3.setTitle("C");
        item3.setDate(WebScraper.formatCraigslistDate("Nov 5"));
        item3.setPrice(39.1);
        item3.setUrl("ccc");
        item3.setWebsite("Craiglist");
        result.add(item3);

        Item item4 = new Item();
        item4.setTitle("D");
        item4.setDate(WebScraper.formatCraigslistDate("Nov 15"));
        item4.setPrice(28.1);
        item4.setUrl("ddd");
        item4.setWebsite("Craiglist");
        result.add(item4);

        Item item5 = new Item();
        item5.setTitle("E");
        item5.setDate(WebScraper.formatCraigslistDate("Nov 10"));
        item5.setPrice(87.2);
        item5.setUrl("eee");
        item5.setWebsite("Craiglist");
        result.add(item5);

        Item item6 = new Item();
        item6.setTitle("F");
        item6.setDate(WebScraper.formatCraigslistDate("Nov 14"));
        item6.setPrice(101.1);
        item6.setUrl("fff");
        item6.setWebsite("Craiglist");
        result.add(item6);

        Item item7 = new Item();
        item7.setTitle("G");
        item7.setDate(WebScraper.formatCraigslistDate("Nov 15"));
        item7.setPrice(31.1);
        item7.setUrl("ggg");
        item7.setWebsite("DCWV");
        result.add(item7);
    }

    @Test
    public void testMinPrice_ResultNull() {
        assertEquals(-2, findElement.findMin(null));
    }

    @Test
    public void testMinPrice_EmptyList() {
        assertEquals(-1, findElement.findMin(Collections.emptyList()));
    }

    @Test
    public void testMinPrice_firstIsMin() throws ParseException {
        assertEquals( 0, findElement.findMin(result));
    }

    @Test
    public void testMinPrice_Min() throws ParseException {
        Item item8 = new Item();
        item8.setTitle("H");
        item8.setDate(WebScraper.formatCraigslistDate("Nov 15"));
        item8.setPrice(10.1);
        item8.setUrl("ggg");
        item8.setWebsite("DCWV");
        result.add(item8);

        assertEquals( 7, findElement.findMin(result));
    }

    @Test
    public void testFindLatestPost_ResultNull() {
        assertEquals(-2, findElement.findLatest(null));
    }

    @Test
    public void testFindLatestPost_EmptyList() {
        assertEquals(-1, findElement.findLatest(Collections.emptyList()));
    }

    @Test
    public void testFindLatestPost_firstIsLatest() {
        assertEquals(0, findElement.findLatest(result));
    }

    @Test
    public void testFindLatestPost_Latest() throws ParseException{
        Item item8 = new Item();
        item8.setTitle("H");
        item8.setDate(WebScraper.formatCraigslistDate("Nov 20"));
        item8.setPrice(10.1);
        item8.setUrl("ggg");
        item8.setWebsite("DCWV");
        result.add(item8);

        assertEquals(7, findElement.findLatest(result));
    }
}
