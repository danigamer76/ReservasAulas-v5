package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.*;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class ControladorPage11 implements Initializable{

    @FXML
    private Slider scr_capacidad;

    @FXML
    private Label lb_capacidad;

    @FXML
    private TextField tf_nombreAula;

    @FXML
    private Label lb_insertar;

    @FXML
    private Button btn_insertar;
    
    private int capacidad;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		scr_capacidad.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				capacidad = (int) scr_capacidad.getValue();
				lb_capacidad.setText(Integer.toString(capacidad));
				
			}
		});
	}
	
	 @FXML
	    void pulsarInsertar(ActionEvent event) throws OperationNotSupportedException {
		 	if(tf_nombreAula.getText().trim().isEmpty()) {
		 		System.out.println("AULA VACIA");
		 		lb_insertar.setText("AULA VACIA");
		 		lb_insertar.setVisible(true);
		 	}else {
		 		capacidad = (int) scr_capacidad.getValue();
		    	System.out.println(tf_nombreAula.getText() + " & " + capacidad);
		    	lb_insertar.setVisible(true);
		    	Aula insAula = new Aula(tf_nombreAula.getText(),capacidad);
		 	}
		 	tf_nombreAula.setText("");
		 	scr_capacidad.setValue(10);
			
		 	System.out.println();
	    	
	    }
	 
}
