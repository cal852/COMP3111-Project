package comp3111.webscraper;

import org.junit.Test;
import org.junit.runner.RunWith;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static org.junit.Assert.*;

import org.junit.BeforeClass;

/**
 * @author enochwong3111
 * RefineTest class that do the unit test for task 5.
 */
public class RefineTest {
	public static class AsNonApp extends Application {
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        // noop
	    }
	}
	
	@BeforeClass
	public static void initJFX() {
	    Thread t = new Thread("JavaFX Init Thread") {
	        public void run() {
	            Application.launch(AsNonApp.class, new String[0]);
	        }
	    };
	    t.setDaemon(true);
	    t.start();
	}

	
	@Test
	public void testEnableRefine() {
	    
	    Button rBtn = new Button("Refine");
		Label rLabel = new Label("Warning");
		TextField rKeyword = new TextField();
		Refine refine = new Refine(rBtn, rLabel, rKeyword);
		
		refine.enableRefine();
		assertEquals(false, refine.checkRefineBtnIsDisable());
		assertEquals(false, refine.checkKeywordIsDisable());
	}
	
	@Test
	public void testDisableRefine() {
	    
	    Button rBtn = new Button("Refine");
		Label rLabel = new Label("Warning");
		TextField rKeyword = new TextField();
		Refine refine = new Refine(rBtn, rLabel, rKeyword);
		
		refine.disableRefine();
		assertEquals(true, refine.checkRefineBtnIsDisable());
		assertEquals(true, refine.checkKeywordIsDisable());
		assertEquals(false, refine.checkWarningIsVisible());
	}
}


