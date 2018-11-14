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
 * @author enochwong3111
 * Refine class that manage refine btn's relative functions for task 5.
 */
public class Refine {
	private Button RefineButton;
	private Label RefineWarning;
	private TextField Keyword;
	private Timeline timeline = null;
	
	/**
	 * @author enochwong3111
     * initializer
     */
	public Refine(Button refbtn, Label LabelRefineWarning, TextField RefineKeyword) {
		RefineButton = refbtn;
		RefineWarning = LabelRefineWarning;
		Keyword = RefineKeyword;
		timeline = new Timeline(new KeyFrame(Duration.millis(3000),ae -> {RefineWarning.setVisible(false);}));
		
    }

	
	/**
	 * @author enochwong3111
     * Called when the Refine button is pressed. Refines the search result
     */
	public Object[] refineSearch(List<Item> result) {
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
    		return refineContent(refineWords, result);
    	}
    }

	/**
	 * @author enochwong3111
	 * Enables Refine Button for interaction
	 */
    public void enableRefine() {
    	RefineButton.setDisable(false);
    	Keyword.setDisable(false);
    }
    

    /**
	 * @author enochwong3111
     * Called when initialize the program or there are results ( > 0) after searching.
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
	 * @author enochwong3111
     * Called when the refine keyword is valid and the refine button was clicked.
	 * Refine the search result with the str keyword
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
	 * @author enochwong3111
     * for automation test to check whether the refine button is clickable
     * @return 
     */
    @FXML
    public boolean checkRefineBtnIsDisable() {
    	return RefineButton.isDisable();
    }
    
    /**
	 * @author enochwong3111
     * for automation test to check whether the keyword text field is editable
     * @return 
     */
    @FXML
    public boolean checkKeywordIsDisable() {
    	return Keyword.isDisable();
    }
    
    /**
	 * @author enochwong3111
     * for automation test to check whether the RefineWarning label is visible
     * @return 
     */
    @FXML
    public boolean checkWarningIsVisible() {
    	return RefineWarning.isVisible();
    }
    
    /**
	 * @author enochwong3111
     * for automation test to check whether the RefineWarning label is visible
     * @return 
     */
    //@FXML
    public String checkTimelineStatus() {
    	return timeline.getStatus().toString();
    }
}
