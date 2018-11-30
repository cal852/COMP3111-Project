package comp3111.webscraper;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Vector;
import java.util.List;

/**
 * @author cal852
 * ControllerTest class that does unit testing for Task 1
 */
public class ControllerTest {

    private List<Item> result;

    private Controller controller;

    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // no-op
        }
    }

    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
          public void run() { Application.launch(AsNonApp.class, new String[0]); }
        };
        t.setDaemon(true);
        t.start();
    }

    @Before
    public void setup() throws ParseException {
        result = new Vector<Item>();
        controller = new Controller();

        Item item1 = new Item();
        item1.setTitle("A");
        item1.setDate(WebScraper.formatCraigslistDate("Nov 17"));
        item1.setPrice(28.1);
        item1.setUrl("aaa");
        item1.setWebsite("Craiglist");
        result.add(item1);

        Item item2 = new Item();
        item2.setTitle("B");
        item2.setDate(WebScraper.formatCraigslistDate("Nov 11"));
        item2.setPrice(39.4);
        item2.setUrl("bbb");
        item2.setWebsite("Craiglist");
        result.add(item2);

        Item item3 = new Item();
        item3.setTitle("C");
        item3.setDate(WebScraper.formatCraigslistDate("Nov 5"));
        item3.setPrice(39.1);
        item3.setUrl("ccc");
        item3.setWebsite("Craiglist");
        result.add(item3);

        Item item4 = new Item();
        item4.setTitle("D");
        item4.setDate(WebScraper.formatCraigslistDate("Nov 15"));
        item4.setPrice(28.1);
        item4.setUrl("ddd");
        item4.setWebsite("Craiglist");
        result.add(item4);

        Item item5 = new Item();
        item5.setTitle("E");
        item5.setDate(WebScraper.formatCraigslistDate("Nov 10"));
        item5.setPrice(87.2);
        item5.setUrl("eee");
        item5.setWebsite("Craiglist");
        result.add(item5);

        Item item6 = new Item();
        item6.setTitle("F");
        item6.setDate(WebScraper.formatCraigslistDate("Nov 14"));
        item6.setPrice(101.1);
        item6.setUrl("fff");
        item6.setWebsite("Craiglist");
        result.add(item6);

        Item item7 = new Item();
        item7.setTitle("G");
        item7.setDate(WebScraper.formatCraigslistDate("Nov 20"));
        item7.setPrice(31.1);
        item7.setUrl("ggg");
        item7.setWebsite("DCWV");
        result.add(item7);


    }

    @Test
    public void testGetConsoleTextData() {
        Object[] expected = new Object[5];
        expected[0] = (String)("A\t28.1\taaa\n" +
                      "B\t39.4\tbbb\n" +
                      "C\t39.1\tccc\n" +
                      "D\t28.1\tddd\n" +
                      "E\t87.2\teee\n" +
                      "F\t101.1\tfff\n" +
                      "G\t31.1\tggg\n");
        expected[1] = (int)7;
        expected[2] = (double)354.1;
        expected[3] = (int)0;
        expected[4] = (int)6;

        Object[] test = controller.getConsoleTextAndData(result);
        assertEquals(expected[0], test[0]);
        assertEquals(expected[1], test[1]);
        assertEquals(expected[2], test[2]);
        assertEquals(expected[3], test[3]);
        assertEquals(expected[4], test[4]);
    }



}
