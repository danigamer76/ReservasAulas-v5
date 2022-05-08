package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.BorderPane;

public class ControladorPage1 implements Initializable{
	
	//AULAS

    @FXML
    private TableColumn<Aula, String> tc_nombreaula;

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
    private TableView<Aula> tw_aulas;

    @FXML
    private Label lb_avisos;

    @FXML
    private TableColumn<Aula, String> tc_capacidad;

    @FXML
    private Label lb_cantidad;

    @FXML
    private BorderPane bp;
    
    ObservableList<Aula> aulas;
    
    private int posicionAulaEnTabla;
    
	
    
    //FUNCION PARA EL BOTON AÑADIR
	@FXML
    void aniadir(ActionEvent event) throws OperationNotSupportedException {
		String nombreAula = tf_nombreaula.getText();
		String cantidad = String.valueOf((int)sld_cantidad.getValue());
		Aula aula = new Aula(nombreAula,cantidad);
		if(Main.coleccionAulas.getAulas().contains(aula)) {
			lb_avisos.setText("ERROR: Ya existe un aula con ese nombre.");
			lb_avisos.setVisible(true);
		}else {
			aulas.add(aula);
			Main.coleccionAulas.insertar(aula);
			System.out.println(Main.coleccionAulas.representar());	
		}	
    }
    
    //FUNCION PARA EL BOTON ELIMINAR
    @FXML
    void eliminar(ActionEvent event) throws OperationNotSupportedException {
    	String nombreAula = tf_nombreaula.getText();
		String cantidad = String.valueOf((int)sld_cantidad.getValue());
		Aula aula = new Aula(nombreAula,cantidad);
    	Main.coleccionAulas.borrar(aula);
    	aulas.remove(posicionAulaEnTabla);
    }

    //FUNCION PARA EL BOTON NUEVO
    @FXML
    void nuevo(ActionEvent event) {
    	tf_nombreaula.setText("");
    	sld_cantidad.setValue(10);
    	btn_aniadir.setDisable(false);
    	btn_eliminar.setDisable(true);
    }
    
    //LISTENER DE LA TABLA
    private final ListChangeListener<? super Aula> selectorTablaAulas =
            new ListChangeListener<Aula>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Aula> c) {
                    ponerAulaSeleccionada();
                }
            };
    
    //PARA SELECCIONAR UNA CELADA DE LA TABLA
    public Aula getTablaAulasSeleccionada() {
    	        if (tw_aulas != null) {
    	            List<Aula> tabla = tw_aulas.getSelectionModel().getSelectedItems();
    	            if (tabla.size() == 1) {
    	                final Aula competicionSeleccionada = tabla.get(0);
    	                return competicionSeleccionada;
    	            }
    	        }
    	        return null;
    	    }
    
    //METODO PARA PONER EN LOS TEXTFIELD LOS CAMPOS SELECCIONADOS
    private void ponerAulaSeleccionada() {
    	final Aula aula = getTablaAulasSeleccionada();
    	posicionAulaEnTabla = aulas.indexOf(aula);
    	
    	if(aula != null) {
    		//PONGO LOS TEXTFIELD CON LOS DATOS CORRESPONDIENTES
    		tf_nombreaula.setText(aula.getNombre());
    		sld_cantidad.setValue(Double.parseDouble(aula.getPuestos()));
    		
    		//PONGO LOS BOTONES EN SU ESTADO CORRESPONDIENTE
    		btn_eliminar.setDisable(false);
    		btn_aniadir.setDisable(true);
    		lb_avisos.setVisible(false);
    	}
    }
    	
    //METODO PARA INICIALIZAR LA TABLA
    private void inicializarTablaAulas() {
    	 tc_nombreaula.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getNombre()));
         tc_capacidad.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getPuestos()));
    	
         aulas = FXCollections.observableArrayList();
         
         tw_aulas.setItems(aulas);
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	//INICIALIZAMOS LA TABLA
    	this.inicializarTablaAulas();
    	
    	//PONEMOS ESTOS DOS BOTONES PARA QUE NO SE PUEDAN SELECCIONAR
    	btn_eliminar.setDisable(true);
    	
    	//SELECCIONAR LAS CELDAS DE LA TABLA
    	final ObservableList<Aula> tablaPersonaSel = tw_aulas.getSelectionModel().getSelectedItems();
        tablaPersonaSel.addListener(selectorTablaAulas);
    	
		//PARA REGULAR EL SLIDER EN TIEMPO REAL
		sld_cantidad.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				lb_cantidad.setText(Integer.toString((int) sld_cantidad.getValue()));
				
			}
		});
		
		//IMPORTANTE
		//ENCARGADO DE MANTENER LA INFORMACION EN LA TABLA
		for (int i = 0; i < Main.coleccionAulas.getAulas().size(); i++) {
			aulas.add(Main.coleccionAulas.getAulas().get(i));
		}
		
		//INICIALIZAMOS LA TABLA CON DATOS ALEATORIOS PARA TESTEAR
//		for (int i = 0; i < Main.coleccionAulas.getNumAulas(); i++) {
//			aulas.add(((List<Aula>) Main.coleccionAulas).get(i));
//		}
//			for (int i = 0; i < 20; i++) {
//            Aula p1 = new Aula(("Nombre " + i),("10"));
//            p1.setNombre("Nombre " + i);
//            p1.setCantidadAula("1"+i);
//            aulas.add(p2);
//        }
	}

}
