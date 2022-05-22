//import com.mongodb.MongoClientSettings;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoDatabase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.mongodb.utilidades.MongoDB;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

//import com.mongodb.*;

public class JavaMongo {
	
	private static MongoClient conexion = null;
	
	//private static final String SERVIDOR = "35.246.226.125";
		private static final String SERVIDOR = "localhost1";	
		private static final int PUERTO = 27017;
		private static final String BD = "reservasaulas";
		private static final String USUARIO = "reservasaulas";
		private static final String CONTRASENA = "reservasaulas-2020";
	public static void main(String[] args) {
//		 Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
//		    mongoLogger.setLevel(Level.SEVERE);
//			if (conexion == null) {
//				 MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());
//				conexion = MongoClients.create(
//				        MongoClientSettings.builder()
//		                .applyToClusterSettings(builder -> 
//		                        builder.hosts(Arrays.asList(new ServerAddress(SERVIDOR, PUERTO))))
//		                .credential(credenciales)
//		                .build());
//				System.out.println("Conexión a MongoDB realizada correctamente.");
//			}
			
		try {

			//Conectarse a la BD
			
			MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());
			MongoClient mongo1 = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credenciales));
			MongoDB.getBD();
			System.out.println("CONEXION ESTABLECIDA CORRECTAMENTE");
			
			//Crear Nueva DB
			DB db1=mongo1.getDB("reservasaulas");
			DBCollection colAulas = db1.createCollection("aulas", null);
			DBCollection colProfesores = db1.createCollection("profesores", null);
			DBCollection colReservas = db1.createCollection("reservas", null);
			
			//Insertar Datos en la Coleccion y Eliminar Datos			
			Aula pruebaaula = new Aula("prueba",20);
			Profesor pruebaprofesor = new Profesor("Alberto","alb@gmail.com");
			Reserva pruebareserva = new Reserva(pruebaprofesor,pruebaaula,new PermanenciaPorHora(LocalDate.now(), LocalTime.now().withMinute(0)));
			
			BasicDBObject d1aula = new BasicDBObject("Nombre_Aula",pruebaaula.getNombre()).append("Capacidad", pruebaaula.getPuestos());
			BasicDBObject d1profesor = new BasicDBObject("Nombre_Profesor",pruebaprofesor.getNombre()).append("Correo", pruebaprofesor.getCorreo()).append("Telefono", pruebaprofesor.getTelefono());
			BasicDBObject d1reserva = new BasicDBObject("Profesor",pruebareserva.getProfesor().getNombre()).append("Aula", pruebareserva.getAula().getNombre()).append("Permanencia", pruebareserva.getPermanencia().getDia().toString());

			//ELIMINAR
			colAulas.remove(d1aula);
			colProfesores.remove(d1profesor);
			colReservas.remove(d1reserva);
			
			//INSERTAR
			colAulas.insert(d1aula);
			colProfesores.insert(d1profesor);
			colReservas.insert(d1reserva);
			
			
			
			//Mostar en Consola
			
			System.out.println("BD AULAS");
			DBCursor cur1 = colAulas.find();
			
			while(cur1.hasNext()) {
				System.out.println(cur1.next());
			}
			
			System.out.println("DB PROFESORES");
			DBCursor cur2 = colProfesores.find();
			
			while(cur2.hasNext()) {
				System.out.println(cur2.next());
			}
			
			System.out.println("DB RESERVAS");
			DBCursor cur3 = colReservas.find();
			
			while(cur3.hasNext()) {
				System.out.println(cur3.next());
			}
			
		} catch (Exception e) {
			e.printStackTrace();		
		}

	}

}
