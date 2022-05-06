package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ControladorPage3 {

    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

    @FXML
    void home(ActionEvent event) {
    	bp.setCenter(ap);
    }

    @FXML
    void page1(ActionEvent event) {
    	loadPage("page1");
    }

    @FXML
    void page2(ActionEvent event) {
    	loadPage("page2");
    }

    @FXML
    void page3(ActionEvent event) {
    	loadPage("page3");
    }
    
    private void loadPage(String page) {
    	Parent root = null;
    	
    	try {
			root = FXMLLoader.load(getClass().getResource(page+".fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	bp.setCenter(root);
    	
    }

}
