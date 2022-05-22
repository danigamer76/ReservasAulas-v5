package application;

import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ControladorPage3 implements Initializable{

    @FXML
    private TableView<Reserva> tw_reservas;

    @FXML
    private TableColumn<Reserva, String> tc_aula;

    @FXML
    private Button btn_nuevoi;

    @FXML
    private Button btn_aniadir;

    @FXML
    private Label lb_avisos;

    @FXML
    private TableColumn<Reserva, String> tc_profesor;

    @FXML
    private Button btn_eliminar;

    @FXML
    private TableColumn<Reserva, String> tc_permanencia;
    
    @FXML
    private DatePicker date_picker;

    @FXML
    private ChoiceBox<Aula> cb_aulas;

    @FXML
    private BorderPane bp;
    
    @FXML
    private ChoiceBox<Integer> cb_hora;

    @FXML
    private ChoiceBox<Profesor> cb_profesores;
    
    ObservableList<Reserva> reservas;

	private int posicionReservaEnTabla;
	
	DBCollection colReservas = Main.basedatosreserva.createCollection("reservas", null);

    @FXML
    void aniadir(ActionEvent event) throws OperationNotSupportedException {
    	Reserva reserva = null;
    	Aula aula = cb_aulas.getValue();
    	Profesor profesor = cb_profesores.getValue();
    	Permanencia permanencia = new PermanenciaPorHora(date_picker.getValue(), LocalTime.of(cb_hora.getValue(), 0));
    	reserva = new Reserva(profesor, aula, permanencia);
    	reservas.add(reserva);
    	Main.coleccionReservas.insertar(reserva);
    	System.out.println(Main.coleccionReservas.representar());
		BasicDBObject d1reserva = new BasicDBObject("Profesor",reserva.getProfesor().getNombre()).append("Aula", reserva.getAula().getNombre()).append("Permanencia", reserva.getPermanencia().getDia().toString());
		colReservas.insert(d1reserva);
    }

    @FXML
    void eliminar(ActionEvent event) throws OperationNotSupportedException {
    	Reserva reserva = null;
    	Aula aula = cb_aulas.getValue();
    	Profesor profesor = cb_profesores.getValue();
    	Permanencia permanencia = new PermanenciaPorHora(date_picker.getValue(), LocalTime.of(cb_hora.getValue(), 0));
    	reserva = new Reserva(profesor, aula, permanencia);
    	reservas.remove(posicionReservaEnTabla);
    	Main.coleccionReservas.borrar(reserva);
		BasicDBObject d1reserva = new BasicDBObject("Profesor",reserva.getProfesor().getNombre()).append("Aula", reserva.getAula().getNombre()).append("Permanencia", reserva.getPermanencia().getDia().toString());
		colReservas.remove(d1reserva);
    }

    @FXML
    void nuevo(ActionEvent event) {
    	cb_aulas.setValue(null);
    	cb_profesores.setValue(null);
    	btn_aniadir.setDisable(false);
    	btn_eliminar.setDisable(true);
    }

    //METODO PARA INCIALIZAR LA TABLA RESERVAS
    public void inicializarTablaReservas() {
    	tc_aula.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().toString()));
		tc_profesor.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().toString()));
		tc_permanencia.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
		
		reservas = FXCollections.observableArrayList();
		
		tw_reservas.setItems(reservas);
    }
  //LISTENER DE LA TABLA
    private final ListChangeListener<? super Reserva> selectorTablaReservas =
    new ListChangeListener<Reserva>() {
    	@Override
    	public void onChanged(ListChangeListener.Change<? extends Reserva> c) {
    		ponerReservaSeleccionada();
    	}
    };
    
  //PARA SELECCIONAR UNA CELADA DE LA TABLA
    public Reserva getTablaReservasSeleccionada() {
    	if (tw_reservas != null) {
    		List<Reserva> tabla = tw_reservas.getSelectionModel().getSelectedItems();
    		if (tabla.size() == 1) {
    			final Reserva competicionSeleccionada = tabla.get(0);
    			return competicionSeleccionada;
    		}
    	}
    	return null;
    }
    
  //METODO PARA PONER EN LOS TEXTFIELD LOS CAMPOS SELECCIONADOS
    private void ponerReservaSeleccionada() {
    	final Reserva reserva = getTablaReservasSeleccionada();
    	posicionReservaEnTabla = reservas.indexOf(reserva);

    	if(reserva != null) {
    		//PONGO LOS TEXTFIELD CON LOS DATOS CORRESPONDIENTES
    		cb_aulas.setValue(reserva.getAula());
    		cb_profesores.setValue(reserva.getProfesor());
    		date_picker.setValue(reserva.getPermanencia().getDia());

    		//PONGO LOS BOTONES EN SU ESTADO CORRESPONDIENTE
    		btn_eliminar.setDisable(false);
    		btn_aniadir.setDisable(true);
    		lb_avisos.setVisible(false);
    	}
    }
    
    //METODO PARA ESTABLECER LAS HORAS
    public void inicializarHoras() {
    	int hora = 8;
    	for (int i = 0; i < 15; i++) {
			cb_hora.getItems().add(hora);
			hora++;
		}
    cb_hora.setValue(8);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//INICIALIZAMOS LA TABLA
		this.inicializarTablaReservas();
		
		//INICIAMOS LAS HORAS
		this.inicializarHoras();
		
		//PONEMOS ESTE PARA QUE NO SE PUEDA SELECCIONAR
		btn_eliminar.setDisable(true);
		
		//EXPORTAR INFORMACION DE LAS OTRAS TABLAS A RESERVAS
		for (int i = 0; i < Main.coleccionProfesores.getProfesores().size(); i++) {
			cb_profesores.getItems().add(Main.coleccionProfesores.getProfesores().get(i));
		}
		for (int i = 0; i < Main.coleccionAulas.getAulas().size(); i++) {
			cb_aulas.getItems().add(new Aula(Main.coleccionAulas.getAulas().get(i)));
		}
		
		//SELECCIONAR LAS CELDAS DE LA TABLA
		final ObservableList<Reserva> tablaPersonaSel = tw_reservas.getSelectionModel().getSelectedItems();
		tablaPersonaSel.addListener(selectorTablaReservas);
		
		//IMPORTANTE
		//ENCARGADO DE MANTENER LA INFORMACION EN LA TABLA
		for (int i = 0; i < Main.coleccionReservas.getReservas().size(); i++) {
			reservas.add(Main.coleccionReservas.getReservas().get(i));
		}

	}

}
