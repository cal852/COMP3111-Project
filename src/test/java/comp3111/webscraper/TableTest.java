package comp3111.webscraper;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableTest {
	
	@Test
	public void testSetItems() throws ParseException {
		TableView<Item> tableView1 = new TableView<Item>();
		List<Item> result = new Vector<Item>();
		WebScraper webScraper = new WebScraper();
		TableColumn<Item, String> col1 = new TableColumn<Item, String>();
		TableColumn<Item, Double> col2 = new TableColumn<Item, Double>();
		TableColumn<Item, String> col3 = new TableColumn<Item, String>();
		TableColumn<Item, Date> col4 = new TableColumn<Item, Date>();
		tableView1.getColumns().add(col1);
		tableView1.getColumns().add(col2);
		tableView1.getColumns().add(col3);
		tableView1.getColumns().add(col4);
		Table table = new Table(tableView1);
		table.initialize();
		Item item1 = new Item();
        Item item2 = new Item();
        Item item3 = new Item();
        Item item4 = new Item();

        // 1st
        item1.setTitle("PowerSnapKit iPhone5 charger Duracell Powermat- Brand New");
        item1.setPrice(25.0*7.8);
        item1.setDate(webScraper.formatCraigslistDate("Nov 8"));
        item1.setUrl("https://newyork.craigslist.org/mnh/ele/d/powersnapkit-iphone5-charger/6732721226.html");
        result.add(item1);

        // 2nd
        item2.setTitle("Like NEW NetGear R6220 Wireless Router (Comcast/Xfinity & Optimum");
        item2.setPrice(40.0*7.8);
        item2.setDate(webScraper.formatCraigslistDate("Nov 4"));
        item2.setUrl("https://cnj.craigslist.org/sys/d/like-new-netgear-r6220/6740967650.html");
        result.add(item2);

        // 3rd
        item3.setTitle("iPhone Iphone5 For Sale very good condition - $125");
        item3.setPrice(125.0*7.8);
        item3.setDate(webScraper.formatCraigslistDate("Nov 8"));
        item3.setUrl("https://newyork.craigslist.org/brk/mob/d/iphone-iphone5-for-sale-very/6737356499.html");
        result.add(item3);

        // 4th
        item4.setTitle("shinny almost new pink Iphone5 plus");
        item4.setPrice(125.0*7.8);
        item4.setDate(webScraper.formatCraigslistDate("Nov 4"));
        item4.setUrl("https://newjersey.craigslist.org/mob/d/shinny-almost-new-pink/6740587872.html");
        result.add(item4);
        
        ObservableList<Item> data = FXCollections.observableArrayList(result);
        table.setItems(data);
        ObservableList<Item> tableData = table.getItems();
        assertEquals(data, tableData);
	}
	
	@Test
	public void testHostServices() {
	    
		TableView<Item> tableView1 = new TableView<Item>();
		TableColumn<Item, String> col1 = new TableColumn<Item, String>();
		TableColumn<Item, Double> col2 = new TableColumn<Item, Double>();
		TableColumn<Item, String> col3 = new TableColumn<Item, String>();
		TableColumn<Item, Date> col4 = new TableColumn<Item, Date>();
		tableView1.getColumns().add(col1);
		tableView1.getColumns().add(col2);
		tableView1.getColumns().add(col3);
		tableView1.getColumns().add(col4);
		Table table = new Table(tableView1);
		Table.setHostServices(null);		
		assertEquals(null, table.getHostServices());
	}
	
}
