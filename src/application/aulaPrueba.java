package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class aulaPrueba {
	
	public String nombreAula = new String();
	public Integer cantidadAula = new Integer(0);
	
	public String getNombreAula() {
		return nombreAula;
	}
	public void setNombreAula(String nombreAula) {
		this.nombreAula = nombreAula;
	}
	public int getCantidadAula() {
		return cantidadAula;
	}
	public void setCantidadAula(int cantidadAula) {
		this.cantidadAula = cantidadAula;
	}
	
	
}
