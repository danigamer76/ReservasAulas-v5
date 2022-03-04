package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Modelo implements IModelo{

	private IProfesores profesores;
	private IAulas aulas;
	private IReservas reservas;

	public Modelo(IFuenteDatos iFuenteDatos) {
		profesores = iFuenteDatos.crearProfesores();
		aulas = iFuenteDatos.crearAulas();
		reservas = iFuenteDatos.crearReservas();
	};

	public List<Aula> getAulas() {
		return aulas.getAulas();
	}

	public int getNumAulas() {
		return aulas.getNumAulas();

	}

	public List<String> representaAulas() {
		return aulas.representar();

	}

	public Aula buscarAula(Aula aula) {
		return aulas.buscar(aula);

	}

	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		aulas.insertar(aula);
	}

	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		aulas.borrar(aula);
	}
	
	public List<Profesor> getProfesores() {
		return profesores.getProfesores();

	}

	public int getNumProfesores() {
		return profesores.getNumProfesores();

	}

	public List<String> representaProfesores() {
		return profesores.representar();

	}

	public Profesor buscarProfesor(Profesor profesor) {
		return profesores.buscar(profesor);

	}

	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		profesores.insertar(profesor);
	}

	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		profesores.borrar(profesor);
	}
	
	public List<Reserva> getReservas() {
		return reservas.getReservas();

	}

	public int getNumReservas() {
		return reservas.getNumReservas();

	}

	public List<String> representaReservas() {
		return reservas.representar();

	}

	public Reserva buscarReserva(Reserva Reserva) {
		return reservas.buscar(Reserva);

	}
	
	public void realizarReserva(Reserva Reserva) throws OperationNotSupportedException {
		reservas.insertar(Reserva);
	}

	public void anularReserva(Reserva Reserva) throws OperationNotSupportedException {
		reservas.borrar(Reserva);
	}
	
	public List<Reserva> getReservasAula(Aula aula) {
		return reservas.getReservasAula(aula);
	}
	
	public List<Reserva> getReservaProfesor(Profesor profesor) {
		return reservas.getReservasProfesor(profesor);
	}
	
	public List<Reserva> getReservaPermanencia(Permanencia permanencia) {
		return reservas.getReservasPermanencia(permanencia);
	}
	
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		return reservas.consultarDisponibilidad(aula, permanencia);
	}
	
}
