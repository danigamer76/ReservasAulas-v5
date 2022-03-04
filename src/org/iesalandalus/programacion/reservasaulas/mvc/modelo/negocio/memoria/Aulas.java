package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas{
	
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




}
