package Resultados;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.management.remote.TargetedNotification;
import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import Comun.FuncionesComunes;

public class Minterms extends Thread {

	private static HashMap<String, String[]> datos;
	private static HashMap<String, String[]> datosMaxterms;
	private static HashMap<String, String[]> datosNONI;
	private static HashMap<Integer, HashMap<Integer, ArrayList<String>>> valores;
	private static HashMap<String, String[]> utilizados;
	private static HashMap<String, String[]> implicantes;
	private static int num_terminos;
	private static int debug;
	
	
	
	public Minterms(int debug) {
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
		
		String funcion = FuncionesComunes.pedirFuncionMinterms();
		String funcionNoNi = FuncionesComunes.pedirTerminosNONI();
		
		terminos = FuncionesComunes.obtenerTerminos(funcion).clone();
		num_terminos = terminos.length;

		datos = (HashMap<String, String[]>) FuncionesComunes.establecerBinarios(funcion, 0);
		datosMaxterms = FuncionesComunes.terminosOpuestos(datos, terminos,0);
		
		if (!funcionNoNi.isEmpty()) { //Si hay datosNONI los añado a datos
			datosNONI = FuncionesComunes.establecerBinariosNONI(funcion, funcionNoNi, 0);
			
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
		
		
		boolean minterms = true;
		
		datos_iniciales = (HashMap<String, String[]>) datos.clone();
		
		utilizados = (HashMap<String, String[]>) FuncionesComunes.emparejar(datos, valores, num_terminos, debug);
		utilizados = FuncionesComunes.eliminarDuplicados(utilizados, debug);
		resultado = FuncionesComunes.primerosImplicantes(datos_iniciales, datosNONI, utilizados, debug); 

		if (debug == 0) {
			System.out.println("\nTERMINOS MINTERMS\n");
			FuncionesComunes.mostrarValores(valores);
		}
		FuncionesComunes.mostrarTablaPrimerosImplicantes(terminos, datos, datos_iniciales, datosNONI, resultado);
		String resultado_min = FuncionesComunes.imprimirResultados(terminos, datos, resultado,minterms);

		
		
		
//		MAXTERMS1
		datos_iniciales = (HashMap<String, String[]>) datosMaxterms.clone();

		utilizados = (HashMap<String, String[]>) FuncionesComunes.emparejar(datosMaxterms, valores, num_terminos, debug);
		utilizados = FuncionesComunes.eliminarDuplicados(utilizados, debug);
		resultado = FuncionesComunes.primerosImplicantes(datos_iniciales, datosNONI, utilizados, debug); 

		if (debug == 0) {
			System.out.println("\nTERMINOS MAXTERMS\n");
			FuncionesComunes.mostrarValores(valores);
		}
		
		FuncionesComunes.mostrarTablaPrimerosImplicantes(terminos, datosMaxterms, datos_iniciales, datosNONI, resultado);
		
		minterms = false;
		String resultado_max = FuncionesComunes.imprimirResultados(terminos, datosMaxterms, resultado,minterms);
		
		
		FuncionesComunes.comparacionMinMax(terminos, resultado_min, resultado_max);
	}
	
	

}
