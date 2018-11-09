/**
 * 
 */
package comp3111.webscraper;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;
import javafx.application.HostServices;


/**
 * 
 * @author kevinw, cal852, enochwong3111
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
	private MenuItem menuLastSearch;
    
    @FXML
    private TextField refineKeyword;
    
    @FXML
    private Label labelRefineWarning;
    
    @FXML
    private Button refineBtn;
    
    private WebScraper scraper;

    private String[] lastSearchTerm;
    
    private List<Item> result;
    
    private Timeline timeline = new Timeline(new KeyFrame(
	        Duration.millis(3000),
	        ae -> labelRefineWarning.setVisible(false)));

    private HostServices hostServices;

	/**
	 * @author cal852
	 * Passes and sets Host Services from WebScraperApplication for use in Controller
	 * @param hostServices
	 */
	public void setHostServices(HostServices hostServices) {
    	this.hostServices = hostServices;
	}

	/**
	 * @author cal852
	 * Gets the hostServices from Application for use in Controller
	 * @return hostServices
	 */
	public HostServices getHostServices() { return hostServices; }
        
    /**
     * Default controller
     */
    public Controller() {
    	scraper = new WebScraper();
    }

    /**
	 * @author cal852
     * Default initializer. Initialize the program
     */
    @FXML
    private void initialize() {
		System.out.println("Initialized the application and controller");
    	lastSearchTerm = new String[2];
    	lastSearchTerm[0] = "";
    	lastSearchTerm[1] = "";
    	disableRefine();
    	initializeTable();
    }

    /**
	 * @author kevinw, cal852, enochwong3111
     * Called when the search button is pressed.
     */
	@FXML
    private void actionSearch() {
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
      
    	result = scraper.scrape(textFieldKeyword.getText());

    	if(!result.isEmpty()) {
    		enableRefine();
    	} else {
    		disableRefine();
    	}
    	
    	updateConsoleAndTabs();

		if (lastSearchTerm[0] == "" && lastSearchTerm[1] == "") { // empty queue
			lastSearchTerm[0] = textFieldKeyword.getText();
		} else if (lastSearchTerm[0] != "" && lastSearchTerm[1] == "") {
			lastSearchTerm[1] = textFieldKeyword.getText();
		} else {
			lastSearchTerm[0] = lastSearchTerm[1];
			lastSearchTerm[1] = textFieldKeyword.getText();
		}

    	final ObservableList<Item> data = FXCollections.observableArrayList(result);
      
		tableView1.setItems(data);
		menuLastSearch.setDisable(false);
 	}
    
    /**
	 * @author kevinw, cal852
     * Called when the result need to be printed in the text area console and summary tabs.
     */
    @FXML
    private void updateConsoleAndTabs() {
    	String output = "";
      
      	int itemCount = 0; /* count items */
		double totalPrice = 0.0; /* total Price for average calculation */
		double minPrice = 0.0; /* minimum Price */
		int minPriceCount = 0; /* obtain first min price element with price > 0.0 */
		Date latestDate = new Date(0);
		Hyperlink minPriceUrl = new Hyperlink("");
		Hyperlink latestPostUrl = new Hyperlink("");

		// obtain first valid results with min price and latest post date for comparison
		if (!result.isEmpty()) {
			minPriceUrl = result.get(0).getLinkUrl();
			latestPostUrl = result.get(0).getLinkUrl();
			// in case of first price is 0, then find until you get price > 0.0
			for (; minPriceCount < result.size(); minPriceCount++) {
				if (result.get(minPriceCount).getPrice() > 0.0) {
					minPrice = result.get(minPriceCount).getPrice();
					break;
				}
			}
			latestDate = result.get(0).getDate();
		}

		for (Item item : result) {
			output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
        
        	totalPrice += item.getPrice();

        	// find minPrice listing
			// Compare prices - obtain new URL when price is newer
			if (result.get(itemCount).getPrice() < minPrice && result.get(itemCount).getPrice() > 0.0) {
				minPrice = result.get(itemCount).getPrice();
				minPriceUrl = result.get(itemCount).getLinkUrl();
			}
			// Compare dates - obtain new URL when date is newer
			if (minPriceCount <= itemCount && result.get(minPriceCount).getDate().compareTo(latestDate) > 0) {
				latestDate = result.get(minPriceCount).getDate();
				latestPostUrl = result.get(minPriceCount).getLinkUrl();
			}

			minPriceCount++;
			itemCount++;
		}


		if (itemCount > 0) {
			labelCount.setText(itemCount + " items");
			labelPrice.setText("$" + (totalPrice / itemCount));
			labelMin.setText(minPriceUrl.getText());
			labelMin.setDisable(false);
			labelMin.setUnderline(true);
			labelLatest.setText(latestPostUrl.getText());
			labelLatest.setDisable(false);
			labelLatest.setUnderline(true);
		} else {
			labelCount.setText("-");
			labelPrice.setText("-");
			labelMin.setText("-");
			labelMin.setDisable(true);
			labelMin.setUnderline(false);
			labelLatest.setText("-");
			labelLatest.setDisable(true);
			labelLatest.setUnderline(false);
		}

		textAreaConsole.setText(output);
    }

    
    /**
	 * @author enochwong3111
     * Called when there are results ( > 0) after searching.
	 * Fills in the table contents
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @FXML
    private void initializeTable() {
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
	 * @author enochwong3111
     * Called when the Refine button is pressed. Refines the search result
     */    
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
    		disableRefine();
    		refineContent(refineWords);
    	}
    }
    
    /**
	 * @author cal852
     * Able to be called when there are results ( > 0) after searching.
	 * Enable the Refine button and refine text field
     */
    @FXML
    private void actionLastSearch() {
    	System.out.println("actionLastSearch");
		if (lastSearchTerm[0] != "" && lastSearchTerm[1] != "") {
			menuLastSearch.setDisable(false);
			textFieldKeyword.setText(lastSearchTerm[0]);
			actionSearch();
		} else if (lastSearchTerm[0] != "") {
			menuLastSearch.setDisable(false);
			textFieldKeyword.setText(lastSearchTerm[0]);
			actionSearch();
		}
		menuLastSearch.setDisable(true);
    }

	/**
	 * Displays item with lowest price found on default browser
	 */
	@FXML
	private void actionOpenMinPrice() {
		System.out.println("actionOpenMinPrice");

		try {
			getHostServices().showDocument(labelMin.getText());
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author cal852
	 * Displays item with the latest date posted on default browser
	 */
	@FXML
	private void actionOpenLatest() {
		System.out.println("actionOpenLatest");
		try {
			getHostServices().showDocument(labelLatest.getText());
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author cal852
	 * Exits the application
	 */
	@FXML
	private void actionQuitApp() {
    	Platform.exit();
	}

	/**
	 * @author cal852, enochwong3111
	 * Closes the search
	 */
	@FXML
	private void actionCloseSearch() {
    	System.out.println("actionCloseSearch");
		lastSearchTerm[0] = textFieldKeyword.getText();
		lastSearchTerm[1] = "";
		labelLatest.setText("");
		labelMin.setText("");
		labelCount.setText("-");
		labelPrice.setText("-");
    	textFieldKeyword.setText("");
    	tableView1.setItems(FXCollections.emptyObservableList());
    	textAreaConsole.setText("");
    	disableRefine();
    	refineKeyword.setText("");
	}

	/**
	 * @author cal852
	 * Displays an alert dialog window with information regarding team
	 */
	@FXML
	private void actionAboutTeam() {
    	System.out.println("actionAboutTeam");
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("About Your Team");
		alert.setHeaderText("Mangkhut's COMP3111 Webscraper");
		alert.setContentText("Team Member Information\n\n" +
				"鈥� Name: CHENG Chee Hau Calvin \n   ITSC: chccheng \n   GitHub Account: cal852 \n\n" +
				"鈥� Name: HYUN Jeongseok \n   ITSC: jhyunaa \n   GitHub Account: HYUNJS \n\n" +
				"鈥� Name: WANG Yingran \n   ITSC: ywangdj \n   GitHub Account: enochwong3111");
		alert.showAndWait();
	}

	/**
	 * @author enochwong3111
	 * Enables Refine Button for interaction
	 */
    private void enableRefine() {
    	refineBtn.setDisable(false);
    	refineKeyword.setDisable(false);
    }
    

    /**
	 * @author enochwong3111
     * Called when initialize the program or there are results ( > 0) after searching.
	 * Disable the Refine button and refine text field
     */
    @FXML
    private void disableRefine() {
    	refineBtn.setDisable(true);
    	refineKeyword.setDisable(true);
		timeline.stop();
		labelRefineWarning.setVisible(false);
    }
    
    /**
	 * @author enochwong3111
     * Called when the refine keyword is valid and the refine button was clicked.
	 * Refine the search result with the str keyword
     */
    @FXML
    private void refineContent(String str) {
    	ArrayList<Integer> indexArr = new ArrayList<Integer>();
    	int i = 0;
    	for (Item item : result) {
    		//System.out.println(item.getTitle());
    		if(!item.getTitle().contains(str)) {
    			i++;
    			indexArr.add(0, result.indexOf(item));
    		}
    	}
    	//System.out.println("size: " + indexArr.size());
    	for(int j : indexArr) {
    		//System.out.print(j + "  ");
    		result.remove(j);
    	}
    	if(i > 0) {
    		//update the table tab's content
    		final ObservableList<Item> data = FXCollections.observableArrayList(result);
    		tableView1.setItems(data);
    		
    		//update the text area console
    		updateConsoleAndTabs();
    		
    		/*TODO*/
    		//update other tabs here
			//updating other tabs done in updateConsoleAndTabs()
    	}
    }
    
}

