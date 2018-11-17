package comp3111.webscraper;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Vector;

import org.junit.Test;


public class WebScraperTest {
    private WebScraper webScraper;

//    @BeforeEach
//    public void setUp() throws Exception{
//        webScraper = new WebScraper();
//    }

    @Test
    public void craiglistScrapingTest() throws Exception{
        webScraper = new WebScraper();
        List<Item> result = new Vector<Item>();
        result = webScraper.scrape("iphone5");

        List<Item> expectedResult = new Vector<Item>();

        Item item1 = new Item();
        Item item2 = new Item();
        Item item3 = new Item();
        Item item4 = new Item();

        // 1st
        item1.setTitle("PowerSnapKit iPhone5 charger Duracell Powermat- Brand New");
        item1.setPrice(25.0*7.8);
        item1.setDate(webScraper.formatCraigslistDate("Nov 8"));
        item1.setUrl("https://newyork.craigslist.org/mnh/ele/d/powersnapkit-iphone5-charger/6732721226.html");
        expectedResult.add(item1);

        // 2nd
        item2.setTitle("Like NEW NetGear R6220 Wireless Router (Comcast/Xfinity & Optimum");
        item2.setPrice(40.0*7.8);
        item2.setDate(webScraper.formatCraigslistDate("Nov 4"));
        item2.setUrl("https://cnj.craigslist.org/sys/d/like-new-netgear-r6220/6740967650.html");
        expectedResult.add(item2);

        // 3rd
        item3.setTitle("iPhone Iphone5 For Sale very good condition - $125");
        item3.setPrice(125.0*7.8);
        item3.setDate(webScraper.formatCraigslistDate("Nov 8"));
        item3.setUrl("https://newyork.craigslist.org/brk/mob/d/iphone-iphone5-for-sale-very/6737356499.html");
        expectedResult.add(item3);

        // 4th
        item4.setTitle("shinny almost new pink Iphone5 plus");
        item4.setPrice(125.0*7.8);
        item4.setDate(webScraper.formatCraigslistDate("Nov 4"));
        item4.setUrl("https://newjersey.craigslist.org/mob/d/shinny-almost-new-pink/6740587872.html");
        expectedResult.add(item4);

        for(int i=0; i<result.size(); i++) {
            expectedResult.get(i).printItem();
            result.get(i).printItem();
            assertEquals(expectedResult.get(i), result.get(i));
        }
    }
}
