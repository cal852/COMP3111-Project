/**
 * 
 */
package comp3111.webscraper;


import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
	private MenuItem menuLastSearch;
    
    private WebScraper scraper;

    private String[] lastSearchTerm;
        
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
    	lastSearchTerm = new String[2];
    	lastSearchTerm[0] = "";
    	lastSearchTerm[1] = "";
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
		int itemCount = 0; /* count items */
		double totalPrice = 0.0; /* total Price for average calculation */
		double minPrice = 0.0; /* minimum Price */
		Date latestDate = new Date(0);
		Hyperlink minPriceUrl = new Hyperlink("");
		Hyperlink latestPostUrl = new Hyperlink("");
		if (!result.isEmpty()) {
			minPriceUrl = result.get(0).getLinkUrl();
			latestPostUrl = result.get(0).getLinkUrl();
			minPrice = result.get(0).getPrice();
			latestDate = result.get(0).getDate();
		}

		TableColumn col1 = tableView1.getColumns().get(0);
		TableColumn col2 = tableView1.getColumns().get(1);
		TableColumn col3 = tableView1.getColumns().get(2);
		TableColumn col4 = tableView1.getColumns().get(3);
		col1.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
		col2.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
		col3.setCellValueFactory(new PropertyValueFactory<Item, Hyperlink>("linkUrl"));
		col3.setCellFactory(new HyperlinkCell());
		col4.setCellValueFactory(new PropertyValueFactory<Item, Date>("date"));
		col4.setCellFactory(column -> {
			TableCell<Item, Date> cell = new TableCell<Item, Date>() {
				private SimpleDateFormat format = new SimpleDateFormat("MMM dd");

				@Override
				protected void updateItem(Date date, boolean empty) {
					super.updateItem(date, empty);
					if (empty) {
						setText(null);
					} else {
						setText(format.format(date));
					}
				}
			};
			return cell;
		});

		for (Item item : result) {
			output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
			totalPrice += item.getPrice();

			// find minPrice listing
			if (itemCount > 0) {
				// Compare prices - obtain new URL when price is newer
				if (result.get(itemCount).getPrice() < minPrice && result.get(itemCount).getPrice() > 0.0) {
					minPrice = result.get(itemCount).getPrice();
					minPriceUrl = result.get(itemCount).getLinkUrl();
				}
				// Compare dates - obtain new URL when date is newer
				if (result.get(itemCount).getDate().compareTo(latestDate) > 0) {
					latestDate = result.get(itemCount).getDate();
					latestPostUrl = result.get(itemCount).getLinkUrl();
				}
			}

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

		final ObservableList<Item> data = FXCollections.observableArrayList(result);
		tableView1.setItems(data);


		if (lastSearchTerm[0] == "" && lastSearchTerm[1] == "") { // empty queue
			lastSearchTerm[0] = textFieldKeyword.getText();
		} else if (lastSearchTerm[0] != "" && lastSearchTerm[1] == "") {
			lastSearchTerm[1] = textFieldKeyword.getText();
		} else {
			lastSearchTerm[0] = lastSearchTerm[1];
			lastSearchTerm[1] = textFieldKeyword.getText();
		}
		menuLastSearch.setDisable(false);
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
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

    @FXML
	private void actionOpenMinPrice() {
		System.out.println("actionOpenMinPrice");
    	if (Desktop.isDesktopSupported()) {
    		try {
    			Desktop.getDesktop().browse(new URI(labelMin.getText()));
			} catch (IOException ev) {
				ev.printStackTrace();
			} catch (URISyntaxException ev) {
				ev.printStackTrace();
			}
		}
	}

	@FXML
	private void actionOpenLatest() {
		System.out.println("actionOpenLatest");
    	if (Desktop.isDesktopSupported()) {
    		try {
    			Desktop.getDesktop().browse(new URI(labelLatest.getText()));
			} catch (IOException ev) {
    			ev.printStackTrace();
			} catch (URISyntaxException ev) {
    			ev.printStackTrace();
			}
		}
	}

	@FXML
	private void actionQuitApp() {
    	Platform.exit();
	}

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
	}

	@FXML
	private void actionAboutTeam() {
    	System.out.println("actionAboutTeam");
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("About Your Team");
		alert.setHeaderText("Mangkhut's COMP3111 Webscraper");
		alert.setContentText("Team Member Information\n\n" +
				"1. Name: CHENG Chee Hau Calvin \n    ITSC: chccheng \n    GitHub Account: cal852 \n\n" +
				"2. Name: HYUN Jeongseok \n    ITSC: jhyunaa \n    GitHub Account: HYUNJS \n\n" +
				"3. Name: WANG Yingran \n    ITSC: ywangdj \n    GitHub Account: enochwong3111");
		alert.showAndWait();
	}
}

