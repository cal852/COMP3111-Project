package comp3111.webscraper;

import javafx.application.HostServices;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/*
 * reference from: https://stackoverflow.com/questions/50622704/javafx-display-hyperlink-in-table-cell
 * */
public class HyperlinkCell implements  Callback<TableColumn<Item, Hyperlink>, TableCell<Item, Hyperlink>> {
	 
	private static HostServices hostServices ;
    
    public static void setHostServices(HostServices hostServices) {
        HyperlinkCell.hostServices = hostServices ;
    }

    public static HostServices getHostServices() {
        return hostServices;
    }
    
    @Override
    public TableCell<Item, Hyperlink> call(TableColumn<Item, Hyperlink> arg) {
        TableCell<Item, Hyperlink> cell = new TableCell<Item, Hyperlink>() {
        	
        	private final Hyperlink hyperlink = new Hyperlink();

            {
                hyperlink.setOnAction(event -> {
                    Hyperlink url = getItem();
                    String url2 = url.toString();
                    url2 = url2.substring(url2.indexOf("]'") + 2, url2.length()-1);
                    System.out.println("cell clicked! " + url2);
                    getHostServices().showDocument(url2);
                });
            }
            
            protected void updateItem(Hyperlink url, boolean empty) {
                super.updateItem(url, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                	String url2 = url.toString();
                    url2 = url2.substring(url2.indexOf("]'") + 2, url2.length()-1);
                	hyperlink.setText(url2);
                    setGraphic(hyperlink);
                }
            }
        };
           
        return cell;
    }
}
