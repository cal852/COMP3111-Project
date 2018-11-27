package comp3111.webscraper;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


/**
 * WebScraper provide a sample code that scrape web content. After it is constructed, you can call the method scrape with a keyword, 
 * the client will go to the default url(Craiglist) and DCFever chosen by Jeongseok then, parse the page by looking at the HTML DOM.
 * <br/>
 * In this particular sample code, it access to craigslist.org. You can directly search on an entry by typing the URL
 * <br/>
 * https://newyork.craigslist.org/search/sss?sort=rel&amp;query=KEYWORD
 *  <br/>
 * where KEYWORD is the keyword you want to search.
 * <br/>
 * Assume you are working on Chrome, paste the url into your browser and press F12 to load the source code of the HTML. You might be freak
 * out if you have never seen a HTML source code before. Keep calm and move on. Press Ctrl-Shift-C (or CMD-Shift-C if you got a mac) and move your
 * mouse cursor around, different part of the HTML code and the corresponding the HTML objects will be highlighted. Explore your HTML page from
 * body &rarr; section class="page-container" &rarr; form id="searchform" &rarr; div class="content" &rarr; ul class="rows" &rarr; any one of the multiple 
 * li class="result-row" &rarr; p class="result-info". You might see something like this:
 * <br/>
 * <pre>
 * {@code
 *    <p class="result-info">
 *        <span class="icon icon-star" role="button" title="save this post in your favorites list">
 *           <span class="screen-reader-text">favorite this post</span>
 *       </span>
 *       <time class="result-date" datetime="2018-06-21 01:58" title="Thu 21 Jun 01:58:44 AM">Jun 21</time>
 *       <a href="https://newyork.craigslist.org/que/clt/d/green-star-polyp-gsp-on-rock/6596253604.html" data-id="6596253604" class="result-title hdrlnk">Green Star Polyp GSP on a rock frag</a>
 *       <span class="result-meta">
 *               <span class="result-price">$15</span>
 *               <span class="result-tags">
 *                   pic
 *                   <span class="maptag" data-pid="6596253604">map</span>
 *               </span>
 *               <span class="banish icon icon-trash" role="button">
 *                   <span class="screen-reader-text">hide this posting</span>
 *               </span>
 *           <span class="unbanish icon icon-trash red" role="button" aria-hidden="true"></span>
 *           <a href="#" class="restore-link">
 *               <span class="restore-narrow-text">restore</span>
 *               <span class="restore-wide-text">restore this posting</span>
 *           </a>
 *       </span>
 *   </p>
 *}
 *</pre>
 * <br/>
 * The code 
 * <pre>
 * {@code
 * List<?> items = (List<?>) page.getByXPath("//li[@class='result-row']");
 * }
 * </pre>
 * extracts all result-row and stores the corresponding HTML elements to a list called items. Later in the loop it extracts the anchor tag 
 * &lsaquo; a &rsaquo; to retrieve the display text (by .asText()) and the link (by .getHrefAttribute()). It also extracts  
 * 
 * @author HYUN, Jeongseok
 */
public class WebScraper {
	/**
	 * URL of craiglist portal - craiglist is a default search portal
	 */
	private static final String DEFAULT_URL = "https://newyork.craigslist.org/";
	/**
	 * URL of DCFever portal - DCFever is my chosen search portal
	 */
	private static final String DCFever = "https://www.dcfever.com/trading/";
	/**
	 * Webclient null instance
	 */
	private WebClient client;

	/**
	 * Default Constructor
	 * Construct an webscraper instance with the webclient set to be disabled CSS and Javascript
	 */
	public WebScraper() {
		client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
	}

	/**
	 * Removes the last character '/' in the URL
	 * @param s the String of URL which ends with '/'
	 * @return the String wihout the last character from the input
	 */
	private static String removeLastChar(String s) {
		return Optional.ofNullable(s)
				.filter(str -> str.length() != 0)
				.map(str -> str.substring(0, str.length() - 1))
				.orElse(s);
	}

	/**
	 * Removes the non-digital chracters in the price
	 * @param s the String of price which may include non-digital characters.
	 * @return the String has only digital characters from the input string
	 */
	private static String filterNumber(String s){
		String filtered = s.replaceAll("[^0-9]","");
		if(filtered.isEmpty())
			return "0";
		return filtered;
	}

	/**
	 * Date Format function for Craiglist
	 * @param dateString the String of date from the craiglist webpage
	 * @return the Date type corresponding to the input String
	 * @throws ParseException - if the input dateString does not satisfy the given format
	 */
	public static Date formatCraigslistDate(String dateString) throws ParseException {
		DateFormat format = new SimpleDateFormat("MMM d", Locale.ENGLISH);
		return format.parse(dateString);
	}

	/**
	 * Date Format function for DCFever
	 * @param dateString the String of date from the DCFever webpage
	 * @return the Date type corresponding to the input String
	 * @throws ParseException - if the input dateString does not satisfy the given format
	 */
	public static Date formatDCFeverDate(String dateString) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd/MM HH:mm", Locale.ENGLISH);
		return format.parse(dateString);
	}

	/**
	 * To scrape web content from the craigslist and DCFever
	 * Assume 7.8HKD = 1 USD
	 * @param keyword - the keyword you want to search
	 * @return A list of Item that has found. Items are sorted by price in ascending order and item from craiglist goes first if the price of items is same. A zero size list is return if nothing is found. Null if any exception (e.g. no connectivity)
	 */
	public List<Item> scrape(String keyword){
		List<Item> craiglists = scrapeCraiglist(keyword);
		List<Item> DCFevers = scrapeDCFever(keyword);

		if(craiglists == null || DCFevers == null)
			return null;

		List<Item> result = new Vector<Item>();
		result.addAll(craiglists);
		result.addAll(DCFevers);
		Collections.sort(result);

		return result;
	}

	/**
	 * To scrape web contents given by keyword from all pages in the craigslist
	 * @param keyword - the keyword you want to search
	 * @return A list of Item that has found sorted by price in ascending order. A zero size list is return if nothing is found. Null if any exception (e.g. no connectivity)
	 */
	public List<Item> scrapeCraiglist(String keyword) {
		Vector<Item> result = new Vector<Item>();
		try {
			String searchUrl = DEFAULT_URL + "search/sss?sort=rel&query=" + URLEncoder.encode(keyword, "UTF-8");

			int pageCount=0;
			String nextUrl;
			HtmlAnchor nextPage;
			do {
				HtmlPage page = client.getPage(searchUrl);
				System.out.println("In Craiglist, Retrieving page " + (1+pageCount++));
				System.out.println(searchUrl);
				retrieveItemCraiglist(result,page);

				nextPage = page.getFirstByXPath(".//a[@class='button next']");
				nextUrl = nextPage.getHrefAttribute();
				searchUrl = removeLastChar(DEFAULT_URL)+nextUrl;
			}while(nextUrl!=null && !nextUrl.isEmpty());
			client.close();
			Collections.sort(result);
		} catch (Exception e) { return null; }
		return result;
	}

	/**
	 * To retrieve item information from the given page of Craiglist and input to the Vector result
	 * Assume 7.8HKD = 1 USD
	 * @param result - Vector Container that the each item information will be stored
	 * @param page - Target page to be retrieved
	 */
	public void retrieveItemCraiglist(Vector<Item> result, HtmlPage page){
		List<?> items = (List<?>) page.getByXPath("//li[@class='result-row']");
		for (int i = 0; i < items.size(); i++) {
			HtmlElement htmlItem = (HtmlElement) items.get(i);
			HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath(".//p[@class='result-info']/a"));
			HtmlElement spanPrice = ((HtmlElement) htmlItem.getFirstByXPath(".//a/span[@class='result-price']"));
			HtmlElement spanDate = ((HtmlElement) htmlItem.getFirstByXPath(".//p/time[@class='result-date']"));

			String itemPrice = spanPrice == null ? "0.0" : spanPrice.asText();

			try{
				Item item = new Item();
				item.setTitle(itemAnchor.asText());
				item.setUrl(itemAnchor.getHrefAttribute());
				item.setPrice((new Double(itemPrice.replace("$", "")))*7.8);
				item.setDate(formatCraigslistDate(spanDate.asText()));
				item.setWebsite("Craiglist");
				result.add(item);
			}catch (ParseException e){ }
		}
	}

	/**
	 * To scrape web contents given by keyword from all pages in the DCFever
	 * @param keyword - the keyword you want to search
	 * @return A list of Item that has found sorted by price in ascending order. A zero size list is return if nothing is found. Null if any exception (e.g. no connectivity)
	 */
	public List<Item> scrapeDCFever(String keyword) {
		Vector<Item> result = new Vector<Item>();
		try {
			String searchUrl = DCFever + "search.php?keyword=" + URLEncoder.encode(keyword, "UTF-8")
					+"&token=qeeqppqqpeqry&cat=all&type=all&min_price=&max_price=&page=";
			HtmlPage page = client.getPage(searchUrl+1);

			int pageCount=0;
			int maxPage = getMaxPageDCFever(page);
			if(maxPage==0) return result;
			System.out.println("In DCFever, result page numbers:" + maxPage);

			while(pageCount<maxPage) {
				System.out.println("Retrieving Page "+ (1+pageCount++));
				retrieveItemDCFever(result,page);
				System.out.println(searchUrl+pageCount);
				if(pageCount<maxPage)
					page = client.getPage(searchUrl+(pageCount+1));
			}

			client.close();
			Collections.sort(result);
		} catch (Exception e) { return null; }

		return result;
	}

	/**
	 * To retrieve item information from the given page of DCFever and input to the Vector result
	 * Assume the prices from DCFever are all HKD
	 * @param result - Vector Container that the each item information will be stored
	 * @param page - Target page to be retrieved
	 */
	public void retrieveItemDCFever(Vector<Item> result, HtmlPage page){
		try{
			List<?> items = (List<?>) page.getByXPath("//table[@class='trade_listing']/tbody/tr");
			// Skip the first item because it is the table's title
			for (int i = 1; i < items.size() - 1; i++) {
				HtmlElement htmlItem = (HtmlElement) items.get(i);
				HtmlAnchor itemAnchor = ((HtmlAnchor) htmlItem.getFirstByXPath("./td[3]/a"));
				HtmlElement spanPrice = ((HtmlElement) htmlItem.getFirstByXPath("./td[4]"));
				HtmlElement spanDate = ((HtmlElement) htmlItem.getFirstByXPath("./td[6]"));

				if (itemAnchor == null || spanPrice == null || spanDate == null)
					continue;

				Item item = new Item();
				item.setTitle(itemAnchor.asText());
				item.setUrl(DCFever + itemAnchor.getHrefAttribute());
				item.setPrice(new Double(filterNumber(spanPrice.asText())));
				item.setDate(formatDCFeverDate(spanDate.asText()));
				item.setWebsite("DCFever");

				result.add(item);
			}
		}catch (ParseException e){ }
	}

	/**
	 * To enable multipagination of searching in DCFever, it returns the number of pages of the result
	 * @param page - Target page to extract pagination information
	 * @return number of pages of the result
	 */
	public int getMaxPageDCFever(HtmlPage page){
		List<?> pagination =(List<?>) page.getByXPath("//div[@class='pagination']/a");
		HtmlElement paginationDiv;
		if(pagination.isEmpty()) // empty page - return zero size result
			return 0;
		else if(pagination.size()>10)
			paginationDiv = (HtmlElement) pagination.get(pagination.size()-2);
		else
			paginationDiv = (HtmlElement) pagination.get(pagination.size()-1);

		return Integer.parseInt(filterNumber(paginationDiv.asText()));
	}
}