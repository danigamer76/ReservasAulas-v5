package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

public class Reserva {
	private Profesor profesor;
	private Aula aula;
	private Permanencia permanencia;
	
	public Reserva(Profesor profesor, Aula aula, Permanencia permanencia) {
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);
	}

	public Reserva(Reserva reserva) {
		if(reserva == null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		}else {
		setProfesor(reserva.profesor);
		setAula(reserva.aula);
		setPermanencia(reserva.permanencia);
		}
	}

	private void setProfesor(Profesor profesor) {
		if(profesor == null) {
			throw new NullPointerException("ERROR: La reserva debe estar a nombre de un profesor.");
		}else {
			this.profesor = new Profesor(profesor);
		}
	}

	public Profesor getProfesor() {
		return profesor;
	}

	private void setAula(Aula aula) {
		if(aula == null) {
			throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
		}else {
			this.aula = new Aula(aula);
		}
	}

	public Aula getAula() {
		return aula;
	}

	private void setPermanencia(Permanencia permanencia) {
		if(permanencia == null) {
			throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
		}else {
				if(permanencia.getPuntos() == 3) {
					this.permanencia = new PermanenciaPorHora((PermanenciaPorHora) permanencia);
				}else {
					this.permanencia = new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
			}
		}
	}

	public Permanencia getPermanencia() {
		return permanencia;
	}
	
	public static Reserva getReservaFicticia(Aula aula,Permanencia permanencia) {
		return new Reserva(Profesor.getProfesorFicticio("profesorFicticio@gmail.com"), aula, permanencia);
		
	}
	
	public float getPuntos() {
		return aula.getPuntos()+permanencia.getPuntos() ;
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(aula, permanencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(aula, other.aula) && Objects.equals(permanencia, other.permanencia);
	}

	@Override
	public String toString() {
		return profesor + ", " + aula + ", " + permanencia + ", puntos=" + getPuntos();
	}








}
