package comp3111.webscraper;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 * Service class that manages refine btn's relative functions for task 5.
 * 
 * @author enochwong3111
 */
public class Refine {
	private Button RefineButton;
	private Label RefineWarning;
	private TextField Keyword;
	private Timeline timeline = null;

	/**
	 * Constructor
	 * @param refbtn - the button control the refine function
	 * @param LabelRefineWarning - the label show refine warning
	 * @param RefineKeyword - the text field for input refine keyword
	 */
	public Refine(Button refbtn, Label LabelRefineWarning, TextField RefineKeyword) {
		RefineButton = refbtn;
		RefineWarning = LabelRefineWarning;
		Keyword = RefineKeyword;
		timeline = new Timeline(new KeyFrame(Duration.millis(3000),ae -> {RefineWarning.setVisible(false);}));
		
    }


	/**
	 * Called when the Refine button is pressed. Refines the search result
	 * @param input - the data you want to refine
	 * @return Object[boolean, refineResult] -boolean: the data has any change or not; -refineResult: the data after refine, 
	 * which will be null if not changes for the data
	 */
	public Object[] refineSearch(List<Item> input) {
		Object[] arr = new Object[2];
    	String refineWords = Keyword.getText();
    	//System.out.println("refineKeyword: " + refineWords + ", length: " + refineWords.length());
    	int emptyNum = 0;
    	for(int i = 0; i < refineWords.length(); i++) {
    		if(refineWords.charAt(i) == ' ') {
    			emptyNum++;
    		}
    	}
    	if(refineWords.length() == 0 || emptyNum == refineWords.length()) {
    		RefineWarning.setVisible(true);
    		timeline.stop();
    		timeline.play();
    		arr[0] = new Boolean(false);
    		arr[1] = null;
    		return arr;
    	}else {
    		disableRefine();
    		return refineContent(refineWords, input);
    	}
    }

	/**
	 * Enables the Refine Button for interaction
	 */
    @FXML
    public void enableRefine() {
    	RefineButton.setDisable(false);
    	Keyword.setDisable(false);
    }
    

    /**
     * Called when initialize the program or there are results (size bigger than zero) after searching.
	 * Disable the Refine button and refine text field
     */
    @FXML
    public void disableRefine() {
    	RefineButton.setDisable(true);
    	Keyword.setDisable(true);
		timeline.stop();
		RefineWarning.setVisible(false);
    }

    /**
     * Called when the refine keyword is valid and the refine button was clicked.
	 * Refine the search result with the str keyword
     * @param str - the refine keyword
     * @param result - the search result data
     * @return Object[boolean, refineResult] -boolean: the data has any change or not; -refineResult: the data after refine, 
	 * which will be null if not changes for the data
     */
    @FXML
    private Object[] refineContent(String str, List<Item> result) {
    	ArrayList<Integer> indexArr = new ArrayList<Integer>();
    	Object[] arr = new Object[2];
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
    		arr[0] = new Boolean(true);
    		arr[1] = FXCollections.observableArrayList(result);
    	}else {
    		arr[0] = new Boolean(false);
    		arr[1] = null;
    	}
		return arr;
    }
    
    /**
     * for automation test to check whether the refine button is clickable
     * @return boolean - the value of the property disable
     */
    @FXML
    public boolean checkRefineBtnIsDisable() {
    	return RefineButton.isDisable();
    }
    
    /**
     * for automation test to check whether the keyword text field is editable
     * @return boolean - the value of the property disable
     */
    @FXML
    public boolean checkKeywordIsDisable() {
    	return Keyword.isDisable();
    }
    
    /**
     * for automation test to check whether the RefineWarning label is visible
     * @return boolean  - the value of the property visible
     */
    @FXML
    public boolean checkWarningIsVisible() {
    	return RefineWarning.isVisible();
    }
    
    /**
     * for automation test to check the status of the Timeline
     * @return String - the status of the Timeline
     */
    public String checkTimelineStatus() {
    	return timeline.getStatus().toString();
    }
}
