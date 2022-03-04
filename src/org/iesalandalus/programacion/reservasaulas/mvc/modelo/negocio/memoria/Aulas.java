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

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas{
	
	private static final String NOMBRE_FICHERO_AULAS = "ficheros/aulas.dat";
	
	private List<Aula> coleccionAulas;

	public Aulas() {
		coleccionAulas = new ArrayList<>();
	}
	
	public Aulas(Aulas aulas) {
		setAulas(aulas);
	}
	
	private void setAulas(Aulas aulas) {
		if(aulas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}else {
			coleccionAulas = copiaProfundaAulas(aulas.coleccionAulas);
		}
	}

	public List<Aula> getAulas() {
		return copiaProfundaAulas(coleccionAulas);

	}

	private List<Aula> copiaProfundaAulas(List<Aula> aulas) {
		List<Aula> copiaAulas = new ArrayList<>();
		Iterator<Aula> listIterator = aulas.listIterator();
		while (listIterator.hasNext()) {
			copiaAulas.add(new Aula(listIterator.next()));
		}
		return copiaAulas;
	}

	public int getNumAulas() {
		return coleccionAulas.size();
	}

	public void insertar(Aula aula) throws OperationNotSupportedException{
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}else {
			if(coleccionAulas.contains(aula)) {
				throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
			}else {
				coleccionAulas.add(new Aula(aula));
			}
		}
	}

	public Aula buscar(Aula aula) {
		int indice = coleccionAulas.indexOf(aula);
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}else {
			if(indice == -1) {
				return null;
			}else {
				return new Aula(coleccionAulas.get(indice));
			}
		}
	}

	public void borrar(Aula aula) throws OperationNotSupportedException {
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}else {
			if(!coleccionAulas.remove(aula)) {
				throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
			}
		}
	}

	public List<String> representar() {
		List<String> representaAulas = new ArrayList<>();
		Iterator<Aula> listIterator = coleccionAulas.listIterator();
		while (listIterator.hasNext()) {
			representaAulas.add(listIterator.next().toString());
		}
		return representaAulas;
	}
	
	public void leer() {
		File ficheroAulas = new File(NOMBRE_FICHERO_AULAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroAulas))) {
			Aula aula = null;
			do {
				aula = (Aula) entrada.readObject();
				insertar(aula);
			} while (aula != null);
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
		File ficheroAulas = new File(NOMBRE_FICHERO_AULAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroAulas))){
			for (Aula aula : coleccionAulas)
				salida.writeObject(aula);
			System.out.println("Fichero clientes escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de clientes");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}




}
