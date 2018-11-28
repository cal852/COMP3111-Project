/**
 * 
 */
package comp3111.webscraper;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


/**
 * Controller class that manage GUI interaction. Please see document about JavaFX for details.
 * @author kevinw, cal852, enochwong3111
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
    
    private Refine refine = null;

    private HostServices hostServices;
    
    private Table table;

	/**
	 * Passes and sets Host Services from WebScraperApplication for use in Controller
	 * @author cal852
	 * @param hostServices (description)
	 */
	public void setHostServices(HostServices hostServices) {
    	this.hostServices = hostServices;
	}

	/**
	 * Gets the hostServices from Application for use in Controller
	 * @author cal852
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
     * Default initializer. Initialize the program
	 * @author cal852, enochwong3111
     */
    @FXML
    private void initialize() {
		System.out.println("Initialized the application and controller");
    	refine = new Refine(refineBtn, labelRefineWarning, refineKeyword);
    	lastSearchTerm = new String[2];
    	lastSearchTerm[0] = "";
    	lastSearchTerm[1] = "";
    	refine.disableRefine();
    	table = new Table(tableView1);
    	table.initialize();
    }

    /**
     * Called when the search button is pressed.
	 * @author kevinw, cal852, enochwong3111
     * @throws ParseException 
     */
	@FXML
    private void actionSearch() throws ParseException {
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
      
    	result = scraper.scrape(textFieldKeyword.getText());

    	if(result != null && !result.isEmpty()) {
    		refine.enableRefine();
    	} else {
    		refine.disableRefine();
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

		ObservableList<Item> data = null;
		if(result != null) {
			data = FXCollections.observableArrayList(result);
		}
      
    	//fill up table content
    	table.setItems(data);
		menuLastSearch.setDisable(false);
 	}
    
    /**
     * Called when the result need to be printed in the text area console and summary tabs.
	 * @author kevinw, cal852
     * @throws ParseException 
     */
    @FXML
    private void updateConsoleAndTabs() throws ParseException {
    	String output = "";
      
      	int itemCount = 0; /* count items */
		double totalPrice = 0.0; /* total Price for average calculation */
		double minPrice = 0.0; /* minimum Price */
		int minPriceCount = 0; /* obtain first min price element with price > 0.0 */
		Date latestDate = new Date(0);
		Hyperlink minPriceUrl = new Hyperlink("");
		Hyperlink latestPostUrl = new Hyperlink("");

		if(result != null) {
			// obtain first valid results with min price and latest post date for comparison
			if (!result.isEmpty()) {
				minPriceUrl = new Hyperlink(result.get(0).getUrl());
				latestPostUrl = new Hyperlink(result.get(0).getUrl());
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
					minPriceUrl = new Hyperlink(result.get(itemCount).getUrl());
				}
				// Compare dates - obtain new URL when date is newer
				if (minPriceCount <= itemCount && result.get(minPriceCount).getDate().compareTo(latestDate) > 0) {
					latestDate = result.get(minPriceCount).getDate();
					latestPostUrl = new Hyperlink(result.get(minPriceCount).getUrl());
				}

				minPriceCount++;
				itemCount++;
			}
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
     * Task 5.ii.a    
     * Called when the Refine button is pressed. Refines the search result and update other tabs
     * @author enochwong3111
     * @throws ParseException 
     */
    @SuppressWarnings("unchecked")
	@FXML
    private void actionRefine() throws ParseException {
    	Object[] resultObj = refine.refineSearch(result);
    	if((Boolean)resultObj[0]) {
    		final ObservableList<Item> data = (ObservableList<Item>)resultObj[1];
        	//update the table tab's content
    		table.setItems(data);
    		
    		//update the text area console
    		updateConsoleAndTabs();
    	}
		
    }
    
    /**
     * Able to be called when there are results ( > 0) after searching.
	 * Enable the Refine button and refine text field
	 * @author cal852
     * @throws ParseException 
     */
    @FXML
    private void actionLastSearch() throws ParseException {
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
	 * Displays item with the latest date posted on default browser
	 * @author cal852
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
	 * Exits the application
	 * @author cal852
	 */
	@FXML
	private void actionQuitApp() {
    	Platform.exit();
	}

	/**
	 * Closes the search
	 * @author cal852, enochwong3111
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
    	table.setItems(FXCollections.emptyObservableList());
    	textAreaConsole.setText("");
    	refine.disableRefine();
    	refineKeyword.setText("");
	}

	/**
	 * Displays an alert dialog window with information regarding team
	 * @author cal852
	 */
	@FXML
	private void actionAboutTeam() {
    	System.out.println("actionAboutTeam");
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("About Your Team");
		alert.setHeaderText("Mangkhut's COMP3111 Webscraper");
		alert.setContentText("Team Member Information\n\n" +
				" Name: CHENG Chee Hau Calvin \n   ITSC: chccheng \n   GitHub Account: cal852 \n\n" +
				" Name: HYUN Jeongseok \n   ITSC: jhyunaa \n   GitHub Account: HYUNJS \n\n" +
				" Name: WANG Yingran \n   ITSC: ywangdj \n   GitHub Account: enochwong3111");
		alert.showAndWait();
	}

	
    
}

