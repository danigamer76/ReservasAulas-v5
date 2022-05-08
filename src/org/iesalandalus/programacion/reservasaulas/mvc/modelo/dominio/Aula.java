package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class Aula implements Serializable{
	private static final float PUNTOS_POR_PUESTO = 0.5f;
	private static final int MIN_PUESTOS = 10;
	private static final int MAX_PUESTOS = 100;
	
	private String nombre;
	private String puestos;

	public Aula(String nombre,String cantidad) {
		setNombre(nombre);
		setPuestos(cantidad);
	}
	
	public Aula(Aula aula) {
		if(aula == null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		}else {
			setNombre(aula.nombre);
			setPuestos(aula.puestos);
		}
	}

	private void setNombre(String nombre) {
		if(nombre == null) {
			throw new NullPointerException("ERROR: El nombre del aula no puede ser nulo.");
		}else {
			if(nombre.trim() == "") {
				throw new IllegalArgumentException("ERROR: El nombre del aula no puede estar vacío.");
			}else {
				this.nombre = nombre;
			}
		}
	}
	
	public String getPuestos() {
		return puestos;
	}
	
	private void setPuestos(String puestos) {
		if (Integer.parseInt(puestos) < MIN_PUESTOS) {
			throw new IllegalArgumentException("ERROR: El número de puestos no es correcto.");
		}else {
			if (Integer.parseInt(puestos)  > MAX_PUESTOS) {
				throw new IllegalArgumentException("ERROR: El número de puestos no es correcto.");
			}else {
				this.puestos = puestos;
			}
		}
		
	}

	public String getNombre() {
		return nombre;
	}

	public float getPuntos() {
		return Integer.parseInt(puestos) *PUNTOS_POR_PUESTO;
	}
	
	public static Aula getAulaFicticia(String nombreAula) {
		return new Aula(nombreAula,"50");
	}
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "nombre=" + getNombre() + ", puestos=" + getPuestos();
	}
	
	
	
}
