package Resultados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Comun.FuncionesComunes;

public class Maxterms extends Thread {


	private static HashMap<String, String[]> datos;
	private static HashMap<String, String[]> datosMinterms;
	private static HashMap<String, String[]> datosNONI;
	private static HashMap<Integer, HashMap<Integer, ArrayList<String>>> valores;
	private static HashMap<String, String[]> utilizados;
	private static HashMap<String, String[]> implicantes;
	private static int num_terminos;
	private static int debug;
	
	
	public Maxterms(int debug) {
		this.debug = debug;
	}
	
	
	public void run(){
		HashMap<String, String[]> datos_iniciales = new HashMap<>();
		ArrayList<String> resultado = new ArrayList<>();
		valores = new HashMap<Integer, HashMap<Integer, ArrayList<String>>>();
		datos = new HashMap<>();
		datosNONI = new HashMap<>();
		String[] terminos;
		String[] terminosNONI;
		
		System.out.println("\n");
		
		
		String funcion = FuncionesComunes.pedirFuncionMaxterms();
		String funcionNoNi = FuncionesComunes.pedirTerminosNONImaxterms();
		
		terminos = FuncionesComunes.obtenerTerminos(funcion).clone(); //devuelve el numero de términos que tiene ej: F(A,B,C) => terminos[] = [A, B, C]
		num_terminos = terminos.length;

		datos = (HashMap<String, String[]>) FuncionesComunes.establecerBinariosMaxterms(funcion, 0);
		datosMinterms = FuncionesComunes.terminosOpuestos(datos, terminos,0);
		
		if (!funcionNoNi.isEmpty()) { //Si hay datosNONI los añado a datos
			datosNONI = FuncionesComunes.establecerBinariosNONImaxterms(funcion, funcionNoNi, 0);
			
			for (Entry<String, String[]> quitar_de_noni : datos.entrySet()) { //Evitamos confundir noni iguales que los normales
				if (datosNONI.containsKey(quitar_de_noni.getKey())) {
					datosNONI.remove(quitar_de_noni.getKey());
				}
			}
			
			if (datosNONI.size() > 1) {
				terminosNONI = funcionNoNi.split("\\+");
			} else {
				terminosNONI = new String[1];
				terminosNONI[0] = funcionNoNi;
			}
			
			for (Entry<String, String[]> dato_noni : datosNONI.entrySet()) {
				datos.put(dato_noni.getKey(), dato_noni.getValue());
			}
			
		}
		
		boolean minterms = false;

		datos_iniciales = (HashMap<String, String[]>) datos.clone(); 

		utilizados = (HashMap<String, String[]>) FuncionesComunes.emparejar(datos, valores, num_terminos, debug);
		utilizados = FuncionesComunes.eliminarDuplicados(utilizados, debug);
		resultado = FuncionesComunes.primerosImplicantes(datos_iniciales, datosNONI, utilizados, debug); 

		if (debug == 0) {
			System.out.println("\nTERMINOS MAXTERMS\n");
			FuncionesComunes.mostrarValores(valores);
		}

		FuncionesComunes.mostrarTablaPrimerosImplicantes(terminos, datos, datos_iniciales, datosNONI, resultado);
		String resultado_max = FuncionesComunes.imprimirResultados(terminos, datos, resultado, minterms);


//		MINTERMS
		datos_iniciales = (HashMap<String, String[]>) datosMinterms.clone();

		utilizados = (HashMap<String, String[]>) FuncionesComunes.emparejar(datosMinterms, valores, num_terminos, debug);
		utilizados = FuncionesComunes.eliminarDuplicados(utilizados, debug);
		resultado = FuncionesComunes.primerosImplicantes(datos_iniciales, datosNONI, utilizados, debug); 

		if (debug == 0) {
			System.out.println("\nTERMINOS MAXTERMS\n");
			FuncionesComunes.mostrarValores(valores);
		}
		
		FuncionesComunes.mostrarTablaPrimerosImplicantes(terminos, datosMinterms, datos_iniciales, datosNONI, resultado);
		
		minterms = true;
		String resultado_min = FuncionesComunes.imprimirResultados(terminos, datosMinterms, resultado,minterms);


		FuncionesComunes.comparacionMinMax(terminos, resultado_min, resultado_max);

	}
	
	
}
