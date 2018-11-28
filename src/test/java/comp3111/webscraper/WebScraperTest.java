package comp3111.webscraper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class WebScraperTest {
    private WebScraper webScraper = new WebScraper();
    private WebClient client;
    private static String FILEDIR= new File("").getAbsolutePath();

    @BeforeClass
    public static void dirSetup() {
        if(!FILEDIR.contains("test"))
            FILEDIR+="/src/test";
        FILEDIR="file://"+FILEDIR+"/java/comp3111/webscraper/assets/";
    }
    @Before
    public void setup() throws Exception{
        client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
    }

    @After
    public void close() throws Exception{
        client.close();
    }

    @Test
    public void DCFeverMultipaginationTest() throws Exception{
        String url = FILEDIR+"dcfever-iphone7-page1.html";
        HtmlPage page = client.getPage(url);
        assertEquals(3, webScraper.getMaxPageDCFever(page));

        url = FILEDIR+"dcfever-samsung-page1.html";
        page = client.getPage(url);
        assertEquals(34, webScraper.getMaxPageDCFever(page));
    }

    @Test
    public void DCFeverNoResultTest() throws Exception{
        String url = FILEDIR+"dcfever-noresult.html";

        HtmlPage page = client.getPage(url);
        Vector<Item> result = new Vector<>();
        Vector<Item> expectedResult = new Vector<>();
        webScraper.retrieveItemDCFever(result,page);

        assertEquals(0, webScraper.getMaxPageDCFever(page));
        assertTrue(expectedResult.equals(result));

    }

    @Test
    public void DCFeverLocalScrapingTest() throws Exception{
        String url = FILEDIR+"dcfever-iphone7-page1.html";

        HtmlPage page = client.getPage(url);
        Vector<Item> result = new Vector<>();
        webScraper.retrieveItemDCFever(result,page);

        Item item1 = new Item();
        item1.setTitle("港行 iPhone 7 128gb 黑色 (4.7\"細機) iphone7 9成新");
        item1.setPrice(2400.0);
        item1.setDate(WebScraper.formatDCFeverDate("28/11 17:15"));
        item1.setUrl("https://www.dcfever.com/trading/https://www.dcfever.com/trading/view.php?itemID=6947781");
        item1.setWebsite("DCFever");

        Item item2 = new Item();
        item2.setTitle("95 % new iphone7 128g silver");
        item2.setPrice(2400.0);
        item2.setDate(WebScraper.formatDCFeverDate("19/11 08:32"));
        item2.setUrl("https://www.dcfever.com/trading/https://www.dcfever.com/trading/view.php?itemID=6923108");
        item2.setWebsite("DCFever");

        result.get(0).printItem();
        result.get(result.size()-1).printItem();

        assertEquals(result.get(0),item1);
        assertEquals(result.get(result.size()-1),item2);
    }

    @Test
    public void DCFeverWebScrapingTest() throws Exception{
        // keywrod: iphone7 - all results must contain the keyword in the title
        List<Item> result = webScraper.scrapeDCFever("iphone7");
        result.forEach(item->assertTrue(item.getTitle().toLowerCase().contains("iphone7")));
    }

    @Test
    public void craiglistLocalScrapingTest() throws Exception{
        String url = FILEDIR+"craiglist-iphone7.html";

        HtmlPage page = client.getPage(url);
        Vector<Item> result = new Vector<>();

        webScraper.retrieveItemCraiglist(result,page);

        Item item1 = new Item();
        item1.setTitle("256GB Apple iPhone7+ iPhone 7 iPhone 7 plus");
        item1.setPrice(2730.0);
        item1.setUrl("https://newyork.craigslist.org/fct/mob/d/256gb-apple-iphone7-iphone-7/6750455791.html");
        item1.setWebsite("Craiglist");
        item1.setDate(WebScraper.formatCraigslistDate("Nov 28"));

        Item item2 = new Item();
        item2.setTitle("Iphone7 Black");
        item2.setUrl("https://hudsonvalley.craigslist.org/mob/d/iphone7-black/6755284380.html");
        item2.setPrice(0.0);
        item2.setDate(WebScraper.formatCraigslistDate("Nov 22"));
        item2.setWebsite("Craiglist");

        result.get(0).printItem();
        result.get(result.size()-1).printItem();

        assertTrue(result.get(0).equals(item1));
        assertTrue(result.get(result.size()-1).equals(item2));
    }

    @Test
    public void CraiglistWebScrapingTest() throws Exception{
        // keyword: mac - at least one result should contain mac in the title
        List<Item> result=webScraper.scrapeCraiglist("mac");
        assertTrue(result.stream().anyMatch(item -> item.getTitle().toLowerCase().contains("mac")));

        // no result - will return null
        result=webScraper.scrapeCraiglist("blahblah-random-testing");
        assertTrue(result==null);
    }

    @Test
    public void WebScrapingTest() throws Exception{
        // samsung
        List<Item> result = webScraper.scrape("samsung");
        List<Item> craiglistResult = webScraper.scrapeCraiglist("samsung");
        List<Item> DCFeverResult = webScraper.scrapeDCFever("samsung");
        assertEquals(result.size(),craiglistResult.size()+DCFeverResult.size());

        // null result in craiglist : blahblah-random-testing
        result = webScraper.scrape("blahblah-random-testing");
        assertTrue(result==null);
    }
}
