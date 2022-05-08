package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class ControladorPage2 implements Initializable{

	@FXML
	private TextField tf_telefono;

	@FXML
	private TableColumn<Profesor, String> tc_telefono;

	@FXML
	private Button btn_nuevoi;

	@FXML
	private TextField tf_nombreprofesor;

	@FXML
	private Button btn_aniadir;

	@FXML
	private Label lb_avisos;

	@FXML
	private TableView<Profesor> tw_profesores;

	@FXML
	private Button btn_eliminar;

	@FXML
	private TextField tf_correo;

	@FXML
	private TableColumn<Profesor, String> tc_nombreprofesor;

	@FXML
	private BorderPane bp;

	@FXML
	private TableColumn<Profesor, String> tc_correo;

	ObservableList<Profesor> profesores;

	private int posicionProfesorEnTabla;

	private static final String ER_TELEFONO = "[6,9]\\d{8}";
	private static final String ER_CORREO = "\\w+(?:\\.\\w+)*@\\w+\\.\\w{2,5}";

	//FUNCION PARA EL BOTON AÑADIR
	@FXML
	void aniadir(ActionEvent event) throws OperationNotSupportedException {
		Profesor profesor = null;
		String nombreProfesor = tf_nombreprofesor.getText();
		String correo = tf_correo.getText();
		String telefono = tf_telefono.getText();
		if(nombreProfesor.trim() == "") {
			lb_avisos.setText("ERROR: El nombre del profesor no puede estar vacío.");
			lb_avisos.setVisible(true);
		}else {
			if(correo.matches(ER_CORREO) == false) {
				lb_avisos.setText("ERROR: El correo del profesor no es válido.");
				lb_avisos.setVisible(true);
			}else {
				
					if(telefono == "") {
						profesor = new Profesor(nombreProfesor,correo);
					}else {
						if(telefono.matches(ER_TELEFONO) == false) {
							lb_avisos.setText("ERROR: El teléfono del profesor no es válido.");
							lb_avisos.setVisible(true);
						}else {
							profesor = new Profesor(nombreProfesor,correo,telefono);
						}
					}
					if(Main.coleccionProfesores.getProfesores().contains(profesor)) {
						lb_avisos.setText("ERROR: Ya existe un profesor con ese nombre.");
						lb_avisos.setVisible(true);
					}else {
						profesores.add(profesor);
						Main.coleccionProfesores.insertar(profesor);
						System.out.println(Main.coleccionProfesores.representar());	  			
					}
				}
			}
		}

//FUNCION PARA EL BOTON ELIMINAR
@FXML
void eliminar(ActionEvent event) throws OperationNotSupportedException {
	Profesor profesor;
	String nombreProfesor = tf_nombreprofesor.getText();
	String correo = tf_correo.getText();
	String telefono = tf_telefono.getText();
	if(telefono == "") {
		profesor = new Profesor(nombreProfesor,correo);

	}else {
		profesor = new Profesor(nombreProfesor,correo,telefono);
	}
	Main.coleccionProfesores.borrar(profesor);
	profesores.remove(posicionProfesorEnTabla);
}

//FUNCION PARA EL BOTON NUEVO
@FXML
void nuevo(ActionEvent event) {
	tf_nombreprofesor.setText("");
	tf_correo.setText("");
	tf_telefono.setText("");
	btn_aniadir.setDisable(false);
	btn_eliminar.setDisable(true);
}

//LISTENER DE LA TABLA
private final ListChangeListener<? super Profesor> selectorTablaProfesores =
new ListChangeListener<Profesor>() {
	@Override
	public void onChanged(ListChangeListener.Change<? extends Profesor> c) {
		ponerProfesorSeleccionada();
	}
};

//PARA SELECCIONAR UNA CELADA DE LA TABLA
public Profesor getTablaProfesoresSeleccionada() {
	if (tw_profesores != null) {
		List<Profesor> tabla = tw_profesores.getSelectionModel().getSelectedItems();
		if (tabla.size() == 1) {
			final Profesor competicionSeleccionada = tabla.get(0);
			return competicionSeleccionada;
		}
	}
	return null;
}

//METODO PARA PONER EN LOS TEXTFIELD LOS CAMPOS SELECCIONADOS
private void ponerProfesorSeleccionada() {
	final Profesor profesor = getTablaProfesoresSeleccionada();
	posicionProfesorEnTabla = profesores.indexOf(profesor);

	if(profesor != null) {
		//PONGO LOS TEXTFIELD CON LOS DATOS CORRESPONDIENTES
		tf_nombreprofesor.setText(profesor.getNombre());
		tf_correo.setText(profesor.getCorreo());
		tf_telefono.setText(profesor.getTelefono());

		//PONGO LOS BOTONES EN SU ESTADO CORRESPONDIENTE
		btn_eliminar.setDisable(false);
		btn_aniadir.setDisable(true);
		lb_avisos.setVisible(false);
	}
}

//METODO PARA INICIALIZAR LA TABLA
private void inicializarTablaProfesores() {
	tc_nombreprofesor.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
	tc_correo.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
	tc_telefono.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getTelefono()));

	profesores = FXCollections.observableArrayList();

	tw_profesores.setItems(profesores);
}

@Override
public void initialize(URL arg0, ResourceBundle arg1) {

	//INICIALIZAMOS LA TABLA
	this.inicializarTablaProfesores();

	//PONEMOS ESTE PARA QUE NO SE PUEDA SELECCIONAR
	btn_eliminar.setDisable(true);

	//SELECCIONAR LAS CELDAS DE LA TABLA
	final ObservableList<Profesor> tablaPersonaSel = tw_profesores.getSelectionModel().getSelectedItems();
	tablaPersonaSel.addListener(selectorTablaProfesores);


	//IMPORTANTE
	//ENCARGADO DE MANTENER LA INFORMACION EN LA TABLA
	for (int i = 0; i < Main.coleccionProfesores.getProfesores().size(); i++) {
		profesores.add(Main.coleccionProfesores.getProfesores().get(i));
	}
}
}
