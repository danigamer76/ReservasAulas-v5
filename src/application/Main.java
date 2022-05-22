package application;
	

import java.util.Arrays;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.Aulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.Profesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.Reservas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.mongodb.utilidades.MongoDB;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	static Aulas coleccionAulas = new Aulas();
	static Profesores coleccionProfesores = new Profesores();
	static Reservas coleccionReservas = new Reservas();
	private static MongoClient conexion = null;
	
	private static final String SERVIDOR = "localhost1";	
	private static final int PUERTO = 27017;
	private static final String BD = "reservasaulas";
	private static final String USUARIO = "reservasaulas";
	private static final String CONTRASENA = "reservasaulas-2020";
	private static MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());
	private static MongoClient mongo1 = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credenciales));
	static DB basedatosreserva=mongo1.getDB("reservasaulas");
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Menu.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Conectarse a la BD
			
			MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());
			MongoClient mongo1 = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credenciales));
			System.out.println("CONEXION ESTABLECIDA CORRECTAMENTE");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
