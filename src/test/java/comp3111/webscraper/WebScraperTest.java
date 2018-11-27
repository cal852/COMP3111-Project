package comp3111.webscraper;

import static comp3111.webscraper.WebScraper.formatCraigslistDate;
import static comp3111.webscraper.WebScraper.formatDCFeverDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class WebScraperTest {
    private WebScraper webScraper = new WebScraper();
    private WebClient client;

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
        File file = new File("java/comp3111/webscraper/assets/"+"dcfever-iphone7-page1"+".html");
        String url = "file://"+file.getAbsolutePath();
        HtmlPage page = client.getPage(url);
        assertEquals(3, webScraper.getMaxPageDCFever(page));

        file = new File("java/comp3111/webscraper/assets/dcfever-samsung-page1.html");
        url = "file://"+file.getAbsolutePath();
        page = client.getPage(url);
        assertEquals(34, webScraper.getMaxPageDCFever(page));
    }

    @Test
    public void DCFeverNoResultTest() throws Exception{
        File file = new File("java/comp3111/webscraper/assets/"+"dcfever-noresult"+".html");
        String url = "file://"+file.getAbsolutePath();

        HtmlPage page = client.getPage(url);
        Vector<Item> result = new Vector<>();
        Vector<Item> expectedResult = new Vector<>();
        webScraper.retrieveItemDCFever(result,page);

        assertEquals(0, webScraper.getMaxPageDCFever(page));
        assertTrue(expectedResult.equals(result));

    }

    @Test
    public void DCFeverLocalScrapingTest() throws Exception{
        File file = new File("java/comp3111/webscraper/assets/"+"dcfever-iphone7-page1"+".html");
        String url = "file://"+file.getAbsolutePath();

        HtmlPage page = client.getPage(url);
        Vector<Item> result = new Vector<>();
        webScraper.retrieveItemDCFever(result,page);

        Item item1 = new Item();
        item1.setTitle("iphone7plus128GB啞黑");
        item1.setPrice(3000.0);
        item1.setDate(formatDCFeverDate("27/11 01:07"));
        item1.setUrl("https://www.dcfever.com/trading/https://www.dcfever.com/trading/view.php?itemID=6943728");
        item1.setWebsite("DCFever");

        Item item2 = new Item();
        item2.setTitle("Iphone7 plus吉盒冇手机冇配件，有3種色(玫瑰金色,銀灰色,金色)。");
        item2.setPrice(30.0);
        item2.setDate(formatDCFeverDate("16/11 22:46"));
        item2.setUrl("https://www.dcfever.com/trading/https://www.dcfever.com/trading/view.php?itemID=6917498");
        item2.setWebsite("DCFever");

        assertEquals(result.get(0),item1);
        assertEquals(result.get(result.size()-1),item2);
    }

    @Test
    public void DCFeverWebScrapingTest() throws Exception{
        List<Item> result = webScraper.scrapeDCFever("iphone7");
        result.forEach(item->assertTrue(item.getTitle().toLowerCase().contains("iphone7")));
    }

    @Test
    public void craiglistLocalScrapingTest() throws Exception{
        File file = new File("java/comp3111/webscraper/assets/craiglist-iphone7.html");
        String url = "file://"+file.getAbsolutePath();

        HtmlPage page = client.getPage(url);
        Vector<Item> result = new Vector<>();

        webScraper.retrieveItemCraiglist(result,page);

        Item item1 = new Item();
        item1.setTitle("256GB Apple iPhone7+ iPhone 7 iPhone 7 plus");
        item1.setPrice(2730.0);
        item1.setUrl("https://newyork.craigslist.org/fct/mob/d/256gb-apple-iphone7-iphone-7/6750455791.html");
        item1.setWebsite("Craiglist");
        item1.setDate(formatCraigslistDate("Nov 25"));

        Item item2 = new Item();
        item2.setTitle("Iphone7 Black");
        item2.setUrl("https://hudsonvalley.craigslist.org/mob/d/iphone7-black/6755284380.html");
        item2.setPrice(0.0);
        item2.setDate(formatCraigslistDate("Nov 22"));
        item2.setWebsite("Craiglist");

        assertTrue(result.get(0).equals(item1));
        assertTrue(result.get(result.size()-1).equals(item2));
    }

    @Test
    public void CraiglistWebScrapingTest() throws Exception{
        List<Item> result=webScraper.scrapeCraiglist("iphone7");

        assertTrue(result.stream().anyMatch(item -> item.getTitle().toLowerCase().contains("iphone7")));
    }

    @Test
    public void WebScrapingTest() throws Exception{
        List<Item> result = webScraper.scrape("iphone7");

        List<Item> craiglistResult = webScraper.scrapeCraiglist("iphone7");
        List<Item> DCFeverResult = webScraper.scrapeDCFever("iphone7");

        assertEquals(result.size(),craiglistResult.size()+DCFeverResult.size());
    }
}
