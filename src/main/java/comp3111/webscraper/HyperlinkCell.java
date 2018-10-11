package comp3111.webscraper;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/*abv
 * reference from: http://www.superglobals.net/create-hyperlink-cell-in-javafx-tableview/
 * */
public class HyperlinkCell implements  Callback<TableColumn<Item, Hyperlink>, TableCell<Item, Hyperlink>> {
	 
    @Override
    public TableCell<Item, Hyperlink> call(TableColumn<Item, Hyperlink> arg) {
        TableCell<Item, Hyperlink> cell = new TableCell<Item, Hyperlink>() {
            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                setGraphic(item);
            }
        };
        
        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                 //System.out.println("cell clicked! " + cell.getChildrenUnmodifiable().get(0).toString());
                 String url = cell.getChildrenUnmodifiable().get(0).toString();
                 if(url.matches(".*hyperlink.*")) {
                	 url = url.substring(url.indexOf("]'") + 2, url.length()-1);
                	 System.out.println("enter into: " + url);
                	 try {
 						Desktop.getDesktop().browse(new URI(url));
 					} catch (IOException e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					} catch (URISyntaxException e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					}
                 }
            }
        });

        return cell;
    }
}
