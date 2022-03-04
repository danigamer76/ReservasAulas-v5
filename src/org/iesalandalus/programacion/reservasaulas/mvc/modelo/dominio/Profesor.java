package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.util.Objects;

public class Profesor {
	private static final String ER_TELEFONO = "[6,9]\\d{8}";
	private static final String ER_CORREO = "\\w+(?:\\.\\w+)*@\\w+\\.\\w{2,5}";
	
	private String nombre;
	private String correo;
	private String telefono;
	
	public Profesor(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
	}
	
	public Profesor(String nombre, String correo, String telefono) {
		if(telefono == null) {
			setNombre(nombre);
			setCorreo(correo);
		}else {
			setNombre(nombre);
			setCorreo(correo);
			setTelefono(telefono);
		}
	}
	
	public Profesor(Profesor profesor) {
		if(profesor == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		}else {
			if(profesor.getTelefono() == null) {
				setNombre(profesor.nombre);
				setCorreo(profesor.correo);
			}else {
				setNombre(profesor.nombre);
				setCorreo(profesor.correo);
				setTelefono(profesor.telefono);
			}
			
		}

	}
	
	private void setNombre(String nombre) {
		if(nombre == null) {
			throw new NullPointerException("ERROR: El nombre del profesor no puede ser nulo.");
		}else {
			if(nombre.trim() == "") {
				throw new IllegalArgumentException("ERROR: El nombre del profesor no puede estar vacío.");
			}else {
				this.nombre = formateaNombre(nombre);
			}
		}

	}
	
	private String formateaNombre(String nombre) {
		//SE ENCARGA DE ELIMINAR TODOS LOS ESPACIOS SOBRANTES Y LOS PONE EN MINUSCULA.
		nombre = nombre.replaceAll("\\s+"," ").trim().toLowerCase();
		String nuevonombre = "";
		//SE ENCARGA DE PONER EN MAYUSCULA LA PRIMERA LETRA DE CADA PALABRA
		nuevonombre += nombre.substring(0,1).toUpperCase();
		for (int i = 1; i < nombre.length(); i++) {
			if(nombre.charAt(i-1) == ' ') {
				nuevonombre += nombre.substring(i,i+1).toUpperCase();
			}else {
				nuevonombre += nombre.substring(i,i+1);
			}
		}
		return nuevonombre;
	}

	public void setCorreo(String correo) {
		if(correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		}else {
			if(correo.matches(ER_CORREO) == false) {
				throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
			}else {
				this.correo = correo;
			}

		}

	}

	public void setTelefono(String telefono) {
			if(telefono.matches(ER_TELEFONO) == false) {
				throw new IllegalArgumentException("ERROR: El teléfono del profesor no es válido.");
			}else {
				this.telefono = telefono;
			}
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public static Profesor getProfesorFicticio(String correoProfesor) {
		return new Profesor("Daniel", correoProfesor);
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(correo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(correo, other.correo);
	}

	@Override
	public String toString() {
		if(this.telefono == null) {
			return "nombre=" + nombre + ", correo=" + correo;
		}else {
			return "nombre=" + nombre + ", correo=" + correo + ", teléfono=" + telefono;
		}
		
	}



}
