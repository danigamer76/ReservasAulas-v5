package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@SuppressWarnings("serial")
public class PermanenciaPorTramo extends Permanencia implements Serializable{
	private static final int PUNTOS = 10;
	private Tramo tramo;
	
	public PermanenciaPorTramo(LocalDate dia,Tramo tramo) {
		super(dia);
		setTramo(tramo);
	}

	public PermanenciaPorTramo(PermanenciaPorTramo permanenciaPorTramo) {
		super(permanenciaPorTramo);
		setTramo(permanenciaPorTramo.tramo);
	}
	
	public Tramo getTramo() {
		return tramo;
	}
	
	private void setTramo(Tramo tramo) {
		if(tramo == null) {
			throw new NullPointerException("ERROR: El tramo de una permanencia no puede ser nulo.");
		}else {
			this.tramo = tramo;		
			}
	}
	
	@Override
	public int getPuntos() {
		return PUNTOS;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tramo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
		return tramo == other.tramo;
	}

	@Override
	public String toString() {
		return super.toString() + ", tramo=" + tramo.toString();
	}	
}
