package application;
	

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.Aulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.Profesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.Reservas;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	static Aulas coleccionAulas = new Aulas();
	static Profesores coleccionProfesores = new Profesores();
	static Reservas coleccionReservas = new Reservas();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Menu.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
