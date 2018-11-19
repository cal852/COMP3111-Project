package comp3111.webscraper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.application.HostServices;
import javafx.collections.ObservableList;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Service class that manages table view's relative functions for task 4.
 * 
 * @author enochwong3111
 */
public class Table {
	private TableView<Item> tableView;
	private static HostServices hostServices;
	
	/**
     * Constructor
     * @param table - the TableView element of the scraper  
     */
	public Table(TableView<Item> table) {
		tableView = table;
	}
	
	/**
	 * Passes and sets Host Services from WebScraperApplication for use in Controller
	 * @param hostServices - the hostServices of the scraper
	 */
	public static void setHostServices(HostServices hostServices) {
		Table.hostServices = hostServices ;
    }
	
	/**
	 * Gets the hostServices from Application for use in Controller
	 * @return hostServices - the hostServices of the scraper
	 */
	public HostServices getHostServices() { return hostServices; }
	
	/**
     * Called when the controller be initialized.
	 * Format the table's columns add the hyper link with click event
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize() {
		// - Task 4.iii
		tableView.setEditable(false);
		TableColumn col1 = tableView.getColumns().get(0);
    	TableColumn col2 = tableView.getColumns().get(1);
    	TableColumn col3 = tableView.getColumns().get(2);
    	TableColumn col4 = tableView.getColumns().get(3);
    	col1.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
    	col2.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
    	col3.setCellValueFactory(new PropertyValueFactory<Item, String>("url"));
    	col4.setCellValueFactory(new PropertyValueFactory<Item, Date>("date"));
    	col4.setCellFactory(column ->{
    		TableCell<Item, Date> cell = new TableCell<Item, Date>() {
    	        private SimpleDateFormat format = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
    	        @Override
    	        protected void updateItem(Date date, boolean empty) {
    	            super.updateItem(date, empty);
    	            if(empty) {
    	                setText(null);
    	            }
    	            else {
    	                setText(format.format(date));
    	            }
    	        }
    	    };
    	    return cell;
    	});
    	col3.setCellFactory(column ->{
    		 TableCell<Item, String> cell = new TableCell<Item, String>() {
    	        	private final Hyperlink hyperlink = new Hyperlink();

    	            {
    	                hyperlink.setOnAction(event -> {
    	                	String url = getItem();                    
    	                    try {
    	                    	// - Task 4.iv
    	                    	getHostServices().showDocument(url);
    	            		} catch(Exception e) {
    	            			e.printStackTrace();
    	            		}
    	                });
    	            }
    	            
    	            protected void updateItem(String url, boolean empty) {
    	                super.updateItem(url, empty);
    	                if (empty) {
    	                    setGraphic(null);
    	                } else {
    	                	hyperlink.setText(url);
    	                    setGraphic(hyperlink);
    	                }
    	            }
    	        };
    	    return cell;
    	});
	}
	
	/**
     * Called when there are results after searching.
     * Refresh the table on another search, fills in the table contents. - Task 4.i and 4.v
	 * @param data ObservableList<Item> - the list of the data to be filled into the table
     */
	public void setItems(ObservableList<Item> data) {
		tableView.setItems(data);
	}
	
	/**
     * Use for unit test.
	 * Get the tables' contents
	 * @return ObservableList<Item> - the list of the table rows' data
     */
	public ObservableList<Item> getItems() {
		return tableView.getItems();
	}
}
