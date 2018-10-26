/**
 * 
 */
package comp3111.webscraper;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;


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
    
    @FXML
    private Button refineBtn;
    
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
    	disableRefine();
    }

    /**
     * Called when the search button is pressed.
     */
    
	@FXML
    private void actionSearch() {
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
    	List<Item> result = scraper.scrape(textFieldKeyword.getText());
    	if(!result.isEmpty()) {
    		enableRefine();
    	}else {
    		disableRefine();
    	}
    	String output = "";
    	for (Item item : result) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    	}
    	textAreaConsole.setText(output);
    	fillTable();

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
     * Called when there are results ( > 0) after searching. Fill in the table content
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @FXML
    private void fillTable() {
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
    }
    
    /**
     * Called when the Refine button is pressed. Refine the search result
     */
    Timeline timeline = new Timeline(new KeyFrame(
	        Duration.millis(1000),
	        ae -> labelRefineWarning.setVisible(false)));
    @FXML
    private void actionRefine() {
    	String refineWords = refineKeyword.getText();
    	System.out.println("refineKeyword: " + refineWords + ", length: " + refineWords.length());
    	int emptyNum = 0;
    	for(int i = 0; i < refineWords.length(); i++) {
    		if(refineWords.charAt(i) == ' ') {
    			emptyNum++;
    		}
    	}
    	if(refineWords.length() == 0 || emptyNum == refineWords.length()) {
    		labelRefineWarning.setVisible(true);
    		timeline.stop();
    		timeline.play();
    	}else {
    		labelRefineWarning.setVisible(false);
    		disableRefine();
    	}
    }
    
    /**
     * Called when there are results ( > 0) after searching. Enable the Refine button and refine text field
     */
    @FXML
    private void enableRefine() {
    	refineBtn.setDisable(false);
    	refineKeyword.setDisable(false);
    }
    

    /**
     * Called when initialize the program or there are results ( > 0) after searching. Disable the Refine button and refine text field
     */
    @FXML
    private void disableRefine() {
    	refineBtn.setDisable(true);
    	refineKeyword.setDisable(true);
    }
    
}

