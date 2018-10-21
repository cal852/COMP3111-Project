/**
 * 
 */
package comp3111.webscraper;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * 
 * @author kevinw
 *
 *
 * Controller class that manage GUI interaction. Please see document about JavaFX for details.
 * 
 */
public class Controller {
	
	@FXML
    private TableView<Item> tableView1;

    @FXML 
    private Label labelCount; 

    @FXML 
    private Label labelPrice; 

    @FXML 
    private Hyperlink labelMin; 

    @FXML 
    private Hyperlink labelLatest; 

    @FXML
    private TextField textFieldKeyword;
    
    @FXML
    private TextArea textAreaConsole;
    
    @FXML
    private TextField refineKeyword;
    
    @FXML
    private Label labelRefineWarning;
    
    private WebScraper scraper;
        
    /**
     * Default controller
     */
    public Controller() {
    	scraper = new WebScraper();
    }

    /**
     * Default initializer. It is empty.
     */
    @FXML
    private void initialize() {
    	
    }

    /**
     * Called when the search button is pressed.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
    private void actionSearch() {
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
    	List<Item> result = scraper.scrape(textFieldKeyword.getText());
    	String output = "";
		TableColumn col1 = tableView1.getColumns().get(0);
    	TableColumn col2 = tableView1.getColumns().get(1);
    	TableColumn col3 = tableView1.getColumns().get(2);
    	TableColumn col4 = tableView1.getColumns().get(3);
    	col1.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
    	col2.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
    	col3.setCellValueFactory(new PropertyValueFactory<Item, Hyperlink>("linkUrl"));
    	col3.setCellFactory(new HyperlinkCell());
    	col4.setCellValueFactory(new PropertyValueFactory<Item, Date>("date"));
    	col4.setCellFactory(column ->{
    		TableCell<Item, Date> cell = new TableCell<Item, Date>() {
    	        private SimpleDateFormat format = new SimpleDateFormat("MMM dd");
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
    	for (Item item : result) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    	}
    	textAreaConsole.setText(output);

    	final ObservableList<Item> data = FXCollections.observableArrayList(result);
		tableView1.setItems(data);
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
     */
    @FXML
    private void actionNew() {
    	System.out.println("actionNew");
    }
    
    /**
     * Called when the Refine button is pressed. Refine the search result
     */
    @FXML
    void actionRefine() {
    	//
    }
    
    
}

