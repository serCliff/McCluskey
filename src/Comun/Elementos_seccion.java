package Comun;

import java.util.ArrayList;

public class Elementos_seccion {
	
	public ArrayList<String> elementos_seccion;
	
	public Elementos_seccion(ArrayList<String> elementos_seccion) {
		this.elementos_seccion = new ArrayList<String>();
		this.elementos_seccion = (ArrayList<String>) elementos_seccion.clone();
	}
}
