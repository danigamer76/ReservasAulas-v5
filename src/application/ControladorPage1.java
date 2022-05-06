package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class ControladorPage1 implements Initializable{

    @FXML
    private TableColumn<aulaPrueba, String> tc_nombreaula;

    @FXML
    private Slider sld_cantidad;

    @FXML
    private Button btn_nuevoi;

    @FXML
    private Button btn_aniadir;

    @FXML
    private TextField tf_nombreaula;

    @FXML
    private Button btn_eliminar;

    @FXML
    private TableView<aulaPrueba> tw_aulas;

    @FXML
    private Button btn_modificar;

    @FXML
    private TableColumn<aulaPrueba, Integer> tc_capacidad;

    @FXML
    private Label lb_cantidad;

    @FXML
    private BorderPane bp;
    
    ObservableList<aulaPrueba> aulas;
    
    private int posicionAulaEnTabla;

	@FXML
    void aniadir(ActionEvent event) {
		aulaPrueba aula = new aulaPrueba();
		aula.setNombreAula(tf_nombreaula.getText());
		aula.setCantidadAula((int) sld_cantidad.getValue());
    	aulas.add(aula);
    }

    @FXML
    void modificar(ActionEvent event) {
    	aulaPrueba aula = new aulaPrueba();
		aula.setNombreAula(tf_nombreaula.getText());
		aula.setCantidadAula((int) sld_cantidad.getValue());
    	aulas.add(aula);
    	aulas.set(posicionAulaEnTabla, aula);
    }

    @FXML
    void eliminar(ActionEvent event) {
    	aulas.remove(posicionAulaEnTabla);
    }

    @FXML
    void nuevo(ActionEvent event) {
    	tf_nombreaula.setText("");
    	sld_cantidad.setValue(10);
    	btn_aniadir.setDisable(false);
    	btn_eliminar.setDisable(true);
    	btn_modificar.setDisable(true);
    }
    
    private final ListChangeListener<aulaPrueba> selectorTablaPersonas =
            new ListChangeListener<aulaPrueba>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends aulaPrueba> c) {
                    ponerAulaSeleccionada();
                }
            };
    
    public aulaPrueba getTablaAulasSeleccionada() {
    	        if (tw_aulas != null) {
    	            List<aulaPrueba> tabla = tw_aulas.getSelectionModel().getSelectedItems();
    	            if (tabla.size() == 1) {
    	                final aulaPrueba competicionSeleccionada = tabla.get(0);
    	                return competicionSeleccionada;
    	            }
    	        }
    	        return null;
    	    }
    
    private void ponerAulaSeleccionada() {
    	final aulaPrueba aula = getTablaAulasSeleccionada();
    	posicionAulaEnTabla = aulas.indexOf(aula);
    	
    	if(aula != null) {
    		tf_nombreaula.setText(aula.getNombreAula());
    		sld_cantidad.setValue(aula.getCantidadAula());
    		
    		btn_modificar.setDisable(false);
    		btn_eliminar.setDisable(false);
    		btn_aniadir.setDisable(true);
    	}
    }
    	
    private void inicializarTablaPersonas() {
    	 tc_nombreaula.setCellValueFactory(new PropertyValueFactory<aulaPrueba, String>("NOMBRE AULA"));
         tc_capacidad.setCellValueFactory(new PropertyValueFactory<aulaPrueba, Integer>("CANTIDAD"));
    	
    	aulas = FXCollections.observableArrayList();
    	tw_aulas.setItems(aulas);
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	this.inicializarTablaPersonas();
    	
    	btn_modificar.setDisable(true);
    	btn_eliminar.setDisable(true);
    	
    	final ObservableList<aulaPrueba> tablaPersonaSel = tw_aulas.getSelectionModel().getSelectedItems();
        tablaPersonaSel.addListener(selectorTablaPersonas);
    	
		
		sld_cantidad.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				lb_cantidad.setText(Integer.toString((int) sld_cantidad.getValue()));
				
			}
		});
		
		// Inicializamos la tabla con algunos datos aleatorios
			for (int i = 0; i < 20; i++) {
            aulaPrueba p1 = new aulaPrueba();
            p1.setNombreAula("Nombre " + i);
            p1.setCantidadAula(20+i);
            aulas.add(p1);
        }
	}

}
