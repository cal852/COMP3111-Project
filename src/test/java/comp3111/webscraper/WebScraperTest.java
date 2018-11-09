package comp3111.webscraper;

import com.gargoylesoftware.htmlunit.WebClient;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WebScraperTest {
    private WebScraper webScraper;

    @BeforeEach
    public void setUp() throws Exception{
        webScraper = new WebScraper();
    }

    @Test
    public void craiglistScrapingTest() throws Exception{
        List<Item> result = new Vector<Item>();
        result = webScraper.scrape("iphone5");

        List<Item> expectedResult = new ArrayList<Item>();

        Item item = new Item();
        item.setTitle("PowerSnapKit iPhone5 charger Duracell Powermat- Brand New");
        item.setPrice(25);
        item.setDate("2018-11-08 16:26");
        item.setUrl("https://newyork.craigslist.org/"+"https://newyork.craigslist.org/mnh/ele/d/powersnapkit-iphone5-charger/6732721226.html");
        item.setLinkUrl("https://newyork.craigslist.org/mnh/ele/d/powersnapkit-iphone5-charger/6732721226.html");
        expectedResult.add(item);

        // 2nd
        item.setTitle("iPhone Iphone5 For Sale very good condition - $125");
        item.setPrice(125);
        item.setDate("2018-11-06 15:06");
        item.setUrl("https://newyork.craigslist.org/"+"https://newyork.craigslist.org/brk/mob/d/iphone-iphone5-for-sale-very/6737356499.html");
        item.setLinkUrl("https://newyork.craigslist.org/brk/mob/d/iphone-iphone5-for-sale-very/6737356499.html");
        expectedResult.add(item);

        // 3rd
        item.setTitle("iPhone Iphone5 For Sale very good condition - $125");
        item.setPrice(125);
        item.setDate("2018-11-01 21:09");
        item.setUrl("https://newyork.craigslist.org/"+"https://newyork.craigslist.org/brk/mob/d/iphone-iphone5-for-sale-very/6728459071.html");
        item.setLinkUrl("https://newyork.craigslist.org/brk/mob/d/iphone-iphone5-for-sale-very/6728459071.html");
        expectedResult.add(item);

        assertEquals(expectedResult,result);

    }
}
