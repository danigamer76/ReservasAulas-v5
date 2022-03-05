package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Reservas implements IReservas{
	
	private static final String NOMBRE_FICHERO_RESERVAS = "ficheros/clientes.dat";

	private List<Reserva> coleccionReservas;

	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}
	
	public Reservas(Reservas reservas) {
		setReservas(reservas);
	}
	
	public void comenzar() {
		leer();
	}
	
	private void leer() {
		File ficheroReservas = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroReservas))) {
			Reserva reserva = null;
			do {
				reserva = (Reserva) entrada.readObject();
				insertar(reserva);
			} while (reserva != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de clientes.");
		} catch (EOFException e) {
			System.out.println("Fichero clientes leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void terminar() {
		escribir();
	}
	
	private void escribir() {
		File ficheroReservas = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroReservas))){
			for (Reserva reserva : coleccionReservas)
				salida.writeObject(reserva);
			System.out.println("Fichero clientes escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de clientes");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}

	private void setReservas(Reservas reservas) {
		if(reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		}else {
			coleccionReservas = copiaProfundaReservas(reservas.coleccionReservas);
		}
	}

	private List<Reserva> copiaProfundaReservas(List<Reserva> reservas) {
		List<Reserva> copiaReserva = new ArrayList<>();
		Iterator<Reserva> listIterator = reservas.listIterator();
		while (listIterator.hasNext()) {
			copiaReserva.add(new Reserva(listIterator.next()));
		}
		return copiaReserva;
	}
	
	public List<Reserva> getReservas(){
		return copiaProfundaReservas(coleccionReservas);
	}
	
	public int getNumReservas() {
		return coleccionReservas.size();
	}

	public void insertar(Reserva reserva) throws OperationNotSupportedException{
		if(reserva == null) {
			throw new NullPointerException("ERROR: No se puede realizar una reserva nula.");
		}else {
			if(coleccionReservas.contains(reserva)) {
				throw new OperationNotSupportedException("ERROR: La reserva ya existe.");
			}else {
				coleccionReservas.add(new Reserva(reserva));
			}
		}

	}
	
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		if(reserva.getPermanencia().getDia().getMonthValue() <= LocalDate.now().getMonthValue() &&
				reserva.getPermanencia().getDia().getYear() < LocalDate.now().getYear()) {
			return false;
		}else {
			return true;
		}
	}
	
	private float getPuntosGastadosReserva(Reserva reserva) {
		return reserva.getPuntos();
	}
	
	private List<Reserva> getReservasProfesorMes(Profesor profesor,LocalDate localdate){
		List<Reserva> reservaProfesorMes = copiaProfundaReservas(coleccionReservas);
		Iterator<Reserva> listIterator = reservaProfesorMes.listIterator();
		while (listIterator.hasNext()) {
			Reserva myobj = listIterator.next();
			if (myobj.getProfesor() != profesor) {
				if(myobj.getPermanencia().getDia() != localdate) {
					listIterator.remove();
				}
			}
		}
		return reservaProfesorMes ;
	}
	
	private Reserva getReservaAulaDia(Aula aula, LocalDate localdate) {
		List<Reserva> reservaAula = copiaProfundaReservas(coleccionReservas);
		Iterator<Reserva> listIterator = reservaAula.listIterator();
		while (listIterator.hasNext()) {
			Reserva myobj = listIterator.next();
			if (myobj.getAula() == aula) {
				if(myobj.getPermanencia().getDia() == localdate) {
					return myobj;
				}
			}
		}
		return null;	
	}

	public Reserva buscar(Reserva reserva) {
		int indice = coleccionReservas.indexOf(reserva);
		if(reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar un reserva nula.");
		}else {
			if(indice == -1) {
				return null;
			}else {
				return new Reserva(coleccionReservas.get(indice));
			}
		}


	}

	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if(reserva == null) {
			throw new NullPointerException("ERROR: No se puede anular una reserva nula.");
		}else {
			if(!coleccionReservas.remove(reserva)) {
				throw new OperationNotSupportedException("ERROR: La reserva a anular no existe.");
			}
		}
	}

	public List<String> representar() {
		List<String> representaReservas = new ArrayList<>();
		Iterator<Reserva> listIterator = coleccionReservas.listIterator();
		while (listIterator.hasNext()) {
			representaReservas.add(listIterator.next().toString());
		}
		return representaReservas;
	}

	public List<Reserva> getReservasProfesor(Profesor profesor) {
		List<Reserva> reservaProfesor = copiaProfundaReservas(coleccionReservas);
		Iterator<Reserva> listIterator = reservaProfesor.listIterator();
		while (listIterator.hasNext()) {
			if (listIterator.next().getProfesor() != profesor) {
				listIterator.remove();;
			}
		}
		return reservaProfesor ;
	}

	public List<Reserva> getReservasAula(Aula aula) {
		List<Reserva> reservaAula = copiaProfundaReservas(coleccionReservas);
		Iterator<Reserva> listIterator = reservaAula.listIterator();
		while (listIterator.hasNext()) {
			if (listIterator.next().getAula() != aula) {
				listIterator.remove();
			}
		}
		return reservaAula ;
	}

	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		List<Reserva> reservaPermanencia = copiaProfundaReservas(coleccionReservas);
		Iterator<Reserva> listIterator = reservaPermanencia.listIterator();
		while (listIterator.hasNext()) {
			if (listIterator.next().getPermanencia() != permanencia) {
				listIterator.remove();
			}
		}
		return reservaPermanencia ;
	}
	
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		}
		if(permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
		List<Reserva> consultarDisponibilidad = copiaProfundaReservas(coleccionReservas);
		Iterator<Reserva> listIterator = consultarDisponibilidad.listIterator();
		while (listIterator.hasNext()) {
			Reserva myobj = listIterator.next();
			if (myobj.getAula() == aula && myobj.getPermanencia() == permanencia) {
					return false;
			}
		}
		return true;
	}
}
