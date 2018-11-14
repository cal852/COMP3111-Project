package comp3111.webscraper;

import org.junit.Test;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static org.junit.Assert.*;

import java.util.Vector;

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
	
	@Test
	public void testRrefineSearchWithEmptyKeyWord() {
	    
	    Button rBtn = new Button("Refine");
		Label rLabel = new Label("Warning");
		TextField rKeyword = new TextField(" ");
		Refine refine = new Refine(rBtn, rLabel, rKeyword);
		rKeyword.setText("");
		
		Object[] arr = refine.refineSearch(null);
		assertEquals(false, (Boolean)arr[0]);
		assertEquals(null, arr[1]);
	}
	
	@Test
	public void testRrefineSearchWithInvalidKeyWord() {
	    
	    Button rBtn = new Button("Refine");
		Label rLabel = new Label("Warning");
		TextField rKeyword = new TextField(" ");
		Refine refine = new Refine(rBtn, rLabel, rKeyword);
		rKeyword.setText("  ");
		
		Object[] arr = refine.refineSearch(null);
		assertEquals(false, (Boolean)arr[0]);
		assertEquals(null, arr[1]);
	}
	
	@Test
	public void testRrefineSearchWithValidKeyWord_ResultChange() {
	    
	    Button rBtn = new Button("Refine");
		Label rLabel = new Label("Warning");
		TextField rKeyword = new TextField(" ");
		Refine refine = new Refine(rBtn, rLabel, rKeyword);
		rKeyword.setText("ab");
		Vector<Item> input = new Vector<Item>();
		Vector<Item> result = new Vector<Item>();
		
		//add item 1
		Item item = new Item();
		item.setTitle("save the world");
		item.setDate("Nov 11");
		item.setPrice(1.1);
		item.setUrl("abc");
		input.add(item);
		
		//add item 2
		item = new Item();
		item.setTitle("about physics");
		item.setDate("Nov 11");
		item.setPrice(1.1);
		item.setUrl("abc");
		input.add(item);
		result.add(item);
		
		//add item 3
		item = new Item();
		item.setTitle("about CS");
		item.setDate("Nov 11");
		item.setPrice(1.1);
		item.setUrl("abc");
		input.add(item);
		result.add(item);
		
		//add item 4
		item = new Item();
		item.setTitle("angel Lee");
		item.setDate("Nov 11");
		item.setUrl("abc");
		item.setPrice(1.1);
		input.add(item);
		
		Object[] arr = refine.refineSearch(input);
		assertEquals(true, (Boolean)arr[0]);
		assertEquals(result, arr[1]);
	}
	
	@Test
	public void testRrefineSearchWithValidKeyWord_ResultUnchange() {
	    
	    Button rBtn = new Button("Refine");
		Label rLabel = new Label("Warning");
		TextField rKeyword = new TextField(" ");
		Refine refine = new Refine(rBtn, rLabel, rKeyword);
		rKeyword.setText("ab");
		Vector<Item> input = new Vector<Item>();
		
		//add item 1
		Item item = new Item();
		item.setTitle("about physics");
		input.add(item);
		
		//add item 2
		item = new Item();
		item.setTitle("about CS");
		input.add(item);
		
		Object[] arr = refine.refineSearch(input);
		assertEquals(false, (Boolean)arr[0]);
		assertEquals(null, arr[1]);
	}
	
	@Test
	public void testRrefineWarning() {
	    
	    Button rBtn = new Button("Refine");
		Label rLabel = new Label("Warning");
		TextField rKeyword = new TextField(" ");
		Refine refine = new Refine(rBtn, rLabel, rKeyword);
		refine.disableRefine();
		refine.enableRefine();
		assertEquals(false, refine.checkWarningIsVisible());
		rKeyword.setText("  ");		
		String result = refine.checkTimelineStatus();
		assertEquals(true, result.contains("STOPPED"));
		refine.refineSearch(null);
		result = refine.checkTimelineStatus();
		assertEquals(true, result.contains("RUNNING"));
		assertEquals(true, refine.checkWarningIsVisible());
		try {
			Thread.sleep(3100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(false, refine.checkWarningIsVisible());
	}
}


