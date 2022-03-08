package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.FactoriaFuenteDatosMemoria;

public enum FactoriaFuenteDatos {

	MEMORIA {
		@Override
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosMemoria();
		}
	},
	FICHEROS{
		@Override
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosMemoria();
		}
		
	};
	
	public abstract IFuenteDatos crear();
}
