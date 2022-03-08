package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class Controlador implements IControlador{

	private IModelo modelo;
	private IVista vista;

	public Controlador(IModelo modelo,IVista vista) {
		this.modelo = modelo;
		this.vista = vista;
		vista.setControlador(this);
	}

	public void comenzar() {
		modelo.comenzar();
		vista.comenzar();
	}

	public void terminar() {
		modelo.terminar();
		System.out.println("Adios");
		
	}

	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		modelo.insertarAula(aula);
	}

	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.insertarProfesor(profesor);
	}
	
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		modelo.borrarAula(aula);
	}

	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.borrarProfesor(profesor);
	}
	
	public Aula buscarAula(Aula aula) {
		return modelo.buscarAula(aula);
		
	}
	
	public Profesor buscarProfesor(Profesor profesor) {
		return modelo.buscarProfesor(profesor);
		
	}
	
	public List<String> representarAulas() {
		return modelo.representaAulas();
		
	}
	
	public List<String> representarProfesores() {
		return modelo.representaProfesores();
		
	}
	
	public List<String> representarReservas() {
		return modelo.representaReservas();
		
	}
	
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.realizarReserva(reserva);
	}
	
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.anularReserva(reserva);
	}
	
	public List<Reserva> getReservasAula(Aula aula) {
		return modelo.getReservasAula(aula);
		
	}
	
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return modelo.getReservaProfesor(profesor);
		
	}
	
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return modelo.getReservaPermanencia(permanencia);
		
	}
	
	public boolean consultarDisponibilidad(Aula aula,Permanencia permanencia) {
		return modelo.consultarDisponibilidad(aula, permanencia);
		
	}
}
