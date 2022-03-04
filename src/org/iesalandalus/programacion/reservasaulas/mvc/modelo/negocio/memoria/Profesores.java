package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores{
	
	private static final String NOMBRE_FICHERO_PROFESORES = "ficheros/profesores.dat";
	
	private List<Profesor> coleccionProfesores;

	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}
	
	public Profesores(Profesores profesores) {
		setProfesores(profesores);
	}
	
	private void setProfesores(Profesores profesores) {
		if(profesores == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		}else {
			coleccionProfesores = copiaProfundaProfesores(profesores.coleccionProfesores);
		}
	}

	private List<Profesor> copiaProfundaProfesores(List<Profesor> profesores) {
		List<Profesor> copiaProfesor = new ArrayList<>();
		Iterator<Profesor> listIterator = profesores.listIterator();
		while (listIterator.hasNext()) {
			copiaProfesor.add(new Profesor(listIterator.next()));
		}
		return copiaProfesor;
	}

	public List<Profesor> getProfesores(){
		return copiaProfundaProfesores(coleccionProfesores);
	}
	
	public int getNumProfesores() {
		return coleccionProfesores.size();
	}
	
	public void insertar(Profesor profesor) throws OperationNotSupportedException{
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}else {
			if(coleccionProfesores.contains(profesor)) {
				throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese nombre.");
			}else {
				coleccionProfesores.add(new Profesor(profesor));
			}
		}
	}


	public Profesor buscar(Profesor profesor) {
		int indice = coleccionProfesores.indexOf(profesor);
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}else {
			if(indice == -1) {
				return null;
			}else {
				return new Profesor(coleccionProfesores.get(indice));
			}
		}


	}

	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		}else {
			if(!coleccionProfesores.remove(profesor)) {
				throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese nombre.");
			}
		}
	}

	public List<String> representar() {
		List<String> representaProfesores = new ArrayList<>();
		Iterator<Profesor> listIterator = coleccionProfesores.listIterator();
		while (listIterator.hasNext()) {
			representaProfesores.add(listIterator.next().toString());
		}
		return representaProfesores;
	}
	
	public void leer() {
		File ficheroProfesores = new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroProfesores))) {
			Profesor profesor = null;
			do {
				profesor = (Profesor) entrada.readObject();
				insertar(profesor);
			} while (profesor != null);
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
	
	public void escribir() {
		File ficheroProfesores = new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroProfesores))){
			for (Profesor profesor : coleccionProfesores)
				salida.writeObject(profesor);
			System.out.println("Fichero clientes escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de clientes");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}
}
