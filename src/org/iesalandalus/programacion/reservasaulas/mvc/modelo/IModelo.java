package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public interface IModelo {
	
	void comenzar();
	
	void terminar();

	List<Aula> getAulas();
	
	List<String> representaAulas();
	
	Aula buscarAula(Aula aula);
	
	void insertarAula(Aula aula) throws OperationNotSupportedException;

	void borrarAula(Aula aula) throws OperationNotSupportedException;
	
	List<Profesor> getProfesores();

	List<String> representaProfesores();

	Profesor buscarProfesor(Profesor profesor);

	void insertarProfesor(Profesor profesor) throws OperationNotSupportedException;

	void borrarProfesor(Profesor profesor) throws OperationNotSupportedException;
	
	List<Reserva> getReservas();

	int getNumReservas();

	List<String> representaReservas();

	Reserva buscarReserva(Reserva Reserva);
	
	void realizarReserva(Reserva Reserva) throws OperationNotSupportedException;

	void anularReserva(Reserva Reserva) throws OperationNotSupportedException;
	
	List<Reserva> getReservasAula(Aula aula);
	
	List<Reserva> getReservaProfesor(Profesor profesor);
	
	List<Reserva> getReservaPermanencia(Permanencia permanencia);
	
	boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);

}
