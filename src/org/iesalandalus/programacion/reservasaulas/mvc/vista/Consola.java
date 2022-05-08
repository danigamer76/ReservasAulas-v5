package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private Consola() {};

	public static void mostrarMenu() {
		mostrarCabecera("Gestión de clientes");
		for (Opcion opcion: Opcion.values()) {
			System.out.println(opcion);
		}
	}

	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String cadena = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(cadena, 0).replace("0", "-"));
	}
	
	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;

	}
	
	public static Aula leerAula() {
		return new Aula(leerNombreAula(),String.valueOf(leerNumeroPuestos()));
	}
	
	public static int leerNumeroPuestos() {
		System.out.println("Numero Puestos del aula?");
		return Entrada.entero();
	}
	
	public static Aula leerAulaFicticia() {
		return Aula.getAulaFicticia(leerNombreAula());
		
	}
		
	public static String leerNombreAula() {
		System.out.println("Nombre del Aula:");
		String nombreAula = Entrada.cadena();
		return nombreAula;

	}
	public static Profesor leerProfesor() {
		System.out.println("Correo del Profesor:");
		String correoProfesor = Entrada.cadena();
		System.out.println("Telefono del Profesor (Si no quieres insertar, pulse ENTER)");
		String telefonoProfesor = Entrada.cadena();
		return new Profesor(leerNombreProfesor(), correoProfesor, telefonoProfesor);

	}
	
	public static String leerNombreProfesor() {
		System.out.println("Nombre del Profesor:");
		String nombreProfesor = Entrada.cadena();
		return nombreProfesor;


	}
	
	public static Profesor leerProfesorFicticion() {
		System.out.println("Correo del Profesor:");
		String correoProfesor = Entrada.cadena();
		return Profesor.getProfesorFicticio(correoProfesor);
		
	}
	public static Tramo leerTramo() {
		System.out.println("Que tramo diario: \n1-Mañana \n2-Tarde");
		int indice = Entrada.entero();
		switch (indice) {
		case 1:
			return Tramo.MANANA;

		case 2:
			return Tramo.TARDE;

		default:
			return null;
		}
	}
	public static LocalDate leerDia() {
		System.out.println("DIA:");
		int dia = Entrada.entero();
		System.out.println("MES:");
		int mes = Entrada.entero();	
		System.out.println("ANIO:");
		int anio = Entrada.entero();			
		return LocalDate.of(anio, mes, dia);
	}
	public static int elegirPermanencia() {
		int opcion = 0;
		do {
			System.out.println("1 - Permanencia Por Tramo\n2 - Permanencia Por Hora");
			opcion = Entrada.entero();
		}while(opcion != 1 || opcion != 2);
		return 0;
		
	}
	
	public static Permanencia leerPermanencia() {
		int opcion = elegirPermanencia();
		switch(opcion) {
		case 1:
			return new PermanenciaPorTramo(leerDia(), leerTramo());
		case 2:
			return new PermanenciaPorHora(leerDia(), leerHora());
		}
		return null;
	}
	
	private static LocalTime leerHora() {
		System.out.println("HORA:");
		int hora = Entrada.entero();
		System.out.println("MINUTO:");
		int minuto = Entrada.entero();
		return LocalTime.of(hora, minuto);
	}
	
	public static Reserva leerReserva() {
		return new Reserva(leerProfesor(), leerAula(), leerPermanencia());
	}
	
	public static Reserva leerReservaFicticia() {
		return Reserva.getReservaFicticia(leerAulaFicticia(), leerPermanencia());
	}
}
