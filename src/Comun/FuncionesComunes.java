package Comun;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Map.Entry;


import org.w3c.dom.css.ElementCSSInlineStyle;

public class FuncionesComunes {
	
	
	
	
	
	public static String titular() {
		
		
				
				
		System.out.println(" __  __           ____ _           _              \n"
						  +"|  \\/  | ___     / ___| |_   _ ___| | _____ _   _ \n"
						  +"| |\\/| |/ __|   | |   | | | | / __| |/ / _ \\ | | |\n"
						  +"| |  | | (__    | |___| | |_| \\__ \\   <  __/ |_| |\n"
						  +"|_|  |_|\\___|    \\____|_|\\__,_|___/_|\\_\\___|\\__, |\n"
						  +"                                            |___/ ");
	
		System.out.print("\nElegir modo de introducción de la función:\n\n\t1) MAXTERMS\n\t2) MINTERMS\n\n\t3) SALIR\n=> ");

		String return_value = "";
		do {
			
			return_value = new Scanner(System.in).nextLine();
			
			if (return_value.isEmpty()) {
				return_value = "2";
			}
			return_value = return_value.toLowerCase();
			if (return_value.equals("maxterms") || return_value.equals("1")) {
				return_value = "1";
			} else if (return_value.equals("minterms") || return_value.equals("2")) {
				return_value = "2";
			}else if (return_value.equals("salir") || return_value.equals("3")) {
				return_value = "3";
			} else {
				System.out.println("Error al introducir el texto, recuerda:\n\n\t1) MAXTERMS\n\t2) MINTERMS\n\n\t3) SALIR\n=> ");
				return_value = "0";
			}
						
		} while (!return_value.equals("1") && !return_value.equals("2") && !return_value.equals("3"));
		
		if (return_value.equals("1")) {
			System.out.println("\nMAXTERMS");
		}else if (return_value.equals("2")){
			System.out.println("\nMINTERMS");
		}else {
			System.out.println("\nSALIENDO...");
		}
		
		
		return return_value;
		
	}
	
	
	public static String pedirFuncionMinterms() {
		System.out.print("Introduce la funcion a evaluar [f(A,B,C,D,E) = aBC + Ade + ABCd]: ");

		String funcion = "";

		funcion = new Scanner(System.in).nextLine();
		
		if (funcion.isEmpty()) {
			funcion = "f(A,B,C,D,E) = aBC + Ade + ABCd";
		}
		
		return funcion;

	}
	public static String[] obtenerTerminos(String funcion) {

		String[] values = null;
		
		funcion = funcion.replaceAll("\\s+",""); // borra espacios en blanco
		funcion = funcion.toLowerCase();
		
		String temp = funcion.split("\\(")[1];
		temp = temp.split("\\)")[0];
		values = temp.split(",");

		return values;

	}
	
	public static String pedirFuncionMaxterms() {
		System.out.print("Introduce la funcion a evaluar [f(A,B,C,D,E) = (a + B + C)(A + d + e)(A + B + C + d)]: ");

		String funcion = "";

		funcion = new Scanner(System.in).nextLine();
		
		if (funcion.isEmpty()) {
			funcion = "f(A,B,C,D,E) = (a + B + C)(A + d + e)(A + B + C + d)";
		}
		
		return funcion;

	}
	
	
	
	public static HashMap<String, String[]> establecerBinarios(String funcion, int debug) {
		
		HashMap<String, String[]> resultados = new HashMap<String, String[]>();
		
		String[] terminos = FuncionesComunes.obtenerTerminos(funcion);
		funcion = funcion.replaceAll("\\s+",""); // borra espacios en blanco
		String f_minterms = funcion.split("=")[1];
		String[] minterms = f_minterms.split("\\+");
		
		ArrayList<String> valores_correspondientes = new ArrayList<>();
		
		
		for (int i = 0; i < minterms.length; i++) {
			
			String new_val = "";
			String[] valSplited = new String[terminos.length];
			for (int j = 0; j < terminos.length; j++) {
				
				if (minterms[i].indexOf(terminos[j]) != -1 ) {
					valSplited[j] = "1";
					new_val=new_val+"1";
				}else if (minterms[i].indexOf(terminos[j].toUpperCase()) != -1 ){
					valSplited[j] = "0";
					new_val=new_val+"0";
				}
				else{
					valSplited[j] = "-";
					new_val=new_val+"-";
				}
			}
			
			valores_correspondientes = formatearValores(valSplited);
			
			for (String correspondiente : valores_correspondientes) {
				String binario = ""+Integer.parseInt(correspondiente, 2);
				
				resultados.put(binario, correspondiente.split("").clone());
			}
			
			
			
		}
		
		
		if (debug == 0) {
			System.out.println("\nTÉRMINOS UTILIZADOS");
			for(Entry<String, String[]> entry : resultados.entrySet()) {
			    String key = entry.getKey();
			    String[] value = entry.getValue();

			    System.out.println("Valor: "+key+"\t=>\t"+Arrays.toString(value));
			}
		}
		if (debug == 2) {
			System.out.println("\nTÉRMINOS NONI");
			for(Entry<String, String[]> entry : resultados.entrySet()) {
			    String key = entry.getKey();
			    String[] value = entry.getValue();

			    System.out.println("Valor: "+key+"\t=>\t"+Arrays.toString(value));
			}
		}
		
		return resultados;
	}
	
	public static ArrayList<String> formatearValores(String[] inicial) {
		
		ArrayList<String> finales = new ArrayList<String>();
		Queue<String[]> cola_finales = new LinkedList<String[]>();
		
		cola_finales.add(inicial);
		
		int indice = 0;
		do {
			String[] ultimo = cola_finales.poll();
			
			indice = ultimo.length+1;
			for (int i = 0; i < ultimo.length; i++) {
				if (ultimo[i].equals("-")) {
					indice = i;
					continue;
				}
			}
			
			if (indice < ultimo.length+1) {
				String[] nuevo = ultimo;
				
				nuevo[indice] = "0"; 
				cola_finales.add(nuevo.clone());
				nuevo[indice] = "1"; 
				cola_finales.add(nuevo.clone());
				
			}else {
				String nuevo = new String();
				for (String valor : ultimo) {
					nuevo+=valor;
				}
				
				finales.add(nuevo);
			}
			
		} while (!cola_finales.isEmpty());
		
		
		return finales;
	}
	

	
	public static HashMap<String, String[]> establecerBinariosMaxterms(String funcion, int debug) {
		
		HashMap<String, String[]> resultados = new HashMap<String, String[]>();
		
		String[] terminos = FuncionesComunes.obtenerTerminos(funcion);
		funcion = funcion.replaceAll("\\s+",""); // borra espacios en blanco
		String f_minterms = funcion.split("=")[1];
		f_minterms = f_minterms.replaceAll("\\(", "");
		f_minterms = f_minterms.replaceAll("\\+", "");
		String[] minterms = f_minterms.split("\\)");
				
		
		for (int i = 0; i < minterms.length; i++) {
			
			String new_val = "";
			String[] valSplited = new String[terminos.length];
			for (int j = 0; j < terminos.length; j++) {
				
				if (minterms[i].indexOf(terminos[j]) != -1 ) {
					valSplited[j] = "0";
					new_val=new_val+"0";
				}else{
					valSplited[j] = "1";
					new_val=new_val+"1";
				}
			}
			
			String binario = ""+Integer.parseInt(new_val, 2);
			resultados.put(binario, valSplited);
			
		}
		
		if (debug == 0) {
			System.out.println("\nTÉRMINOS UTILIZADOS");
			for(Entry<String, String[]> entry : resultados.entrySet()) {
			    String key = entry.getKey();
			    String[] value = entry.getValue();

			    System.out.println("Valor: "+key+"\t=>\t"+Arrays.toString(value));
			}
		}
		if (debug == 2) {
			System.out.println("\nTÉRMINOS NONI");
			for(Entry<String, String[]> entry : resultados.entrySet()) {
			    String key = entry.getKey();
			    String[] value = entry.getValue();

			    System.out.println("Valor: "+key+"\t=>\t"+Arrays.toString(value));
			}
		}
		
		return resultados;
	}
	
	
	
	
	
	
	public static HashMap<String, String[]> terminosOpuestos(HashMap<String, String[]> datos, String[] terminos, int debug) {
		HashMap<String, String[]> datos_opuestos = new HashMap<>();
		
		 
		for (int i = 0; i < Math.pow(2,terminos.length); i++) {
			
			if (!datos.containsKey(Integer.toString(i))) {
				String[] binarios = new String[terminos.length];
				int index = terminos.length-1;
				
				for (int j = 0; j < binarios.length; j++) {
					binarios[j] = "0";
				}
				for (String value : convert(i,2).split("")) {
					binarios[index] = value;
					index--;
				}
				datos_opuestos.put(Integer.toString(i), binarios);
			}
		}
		
		if (debug == 0) {
			System.out.println("OPUESTOS");
			for (Entry<String, String[]> val : datos_opuestos.entrySet()) {
				System.out.println(val.getKey() + " - " + Arrays.toString(val.getValue()));
			}
		}
		
		
//		String enter = new Scanner(System.in).nextLine();
		
		return datos_opuestos;
	}
	
	
	
	public static String convert(int decimal , int base)
    {
        int result = 0;
        int multiplier = 1;

          while(decimal > 0)
            {
              int residue = decimal % base;
              decimal     = decimal / base;
              result      = result + residue * multiplier;
              multiplier  = multiplier * 10;
            }

//            System.out.println ("binary....." + result);
            return ""+result;
    }
	
	
public static String pedirTerminosNONI() {
		
		String terminosNoNi = "";
		
		System.out.print("\n¿Desea introducir términos NO/NI? [Y/n]: ");
		
		terminosNoNi = new Scanner(System.in).nextLine();

		ArrayList<String> terminosNONI = new ArrayList<>();

		if (terminosNoNi.isEmpty()) {
			terminosNoNi = "Y";
		}
		
		if (terminosNoNi.toUpperCase().equals("N")) {
			terminosNoNi = "N";
		} else if (terminosNoNi.toUpperCase().equals("Y")) {
			System.out.print("Introduce el primer témino NO/NI [aBd]: ");
			
			terminosNoNi = new Scanner(System.in).nextLine();
			if (terminosNoNi.isEmpty()) {
				terminosNoNi = "aBd";
			}

			terminosNONI.add(terminosNoNi);
			
			terminosNoNi = "Y";
		}
		
		
		while (terminosNoNi.compareTo("N") != 0){
			System.out.print("\n¿Desea introducir más términos NO/NI? [y/N]: ");

			terminosNoNi = new Scanner(System.in).nextLine();



			if (terminosNoNi.isEmpty()) {
				terminosNoNi = "N";
			}else {
				if (terminosNoNi.toUpperCase().equals("N")) {
					terminosNoNi = "N";
				} else if (terminosNoNi.toUpperCase().equals("Y")) {
					System.out.print("Introduce el siguiente témino NO/NI [BCd]: ");

					
					terminosNoNi = new Scanner(System.in).nextLine();
					
					if (terminosNoNi.isEmpty()) {
						terminosNoNi = "BCd";
					}

					terminosNONI.add(terminosNoNi);
					
					terminosNoNi = "Y";
				}
				
			}
		}
		
		terminosNoNi = "";
		for (String elem : terminosNONI) {
			if (terminosNoNi.compareTo("") == 0) {
				terminosNoNi = elem;
			}else {
				terminosNoNi= terminosNoNi + "+" + elem;
			}
		}
		
		return terminosNoNi;
	}



public static String pedirTerminosNONImaxterms() {
		
		String terminosNoNi = "";
		
		System.out.print("\n¿Desea introducir términos NO/NI? [Y/n]: ");
		
		terminosNoNi = new Scanner(System.in).nextLine();

		ArrayList<String> terminosNONI = new ArrayList<>();

		if (terminosNoNi.isEmpty()) {
			terminosNoNi = "Y";
		}
		
		if (terminosNoNi.toUpperCase().equals("N")) {
			terminosNoNi = "N";
		} else if (terminosNoNi.toUpperCase().equals("Y")) {
			System.out.print("Introduce el primer témino NO/NI [(a + B + d)]: ");
			
			terminosNoNi = new Scanner(System.in).nextLine();
			if (terminosNoNi.isEmpty()) {
				terminosNoNi = "(a + B + d)";
			}

			terminosNONI.add(terminosNoNi);
			
			terminosNoNi = "Y";
		}
		
		
		while (terminosNoNi.compareTo("N") != 0){
			System.out.print("\n¿Desea introducir más términos NO/NI? [y/N]: ");

			terminosNoNi = new Scanner(System.in).nextLine();



			if (terminosNoNi.isEmpty()) {
				terminosNoNi = "N";
			}else {
				if (terminosNoNi.toUpperCase().equals("N")) {
					terminosNoNi = "N";
				} else if (terminosNoNi.toUpperCase().equals("Y")) {
					System.out.print("Introduce el siguiente témino NO/NI [(B + C + d)]: ");

					
					terminosNoNi = new Scanner(System.in).nextLine();
					
					if (terminosNoNi.isEmpty()) {
						terminosNoNi = "(B + C + d)";
					}

					terminosNONI.add(terminosNoNi);
					
					terminosNoNi = "Y";
				}
				
			}
		}
		
		terminosNoNi = "";
		for (String elem : terminosNONI) {
			if (terminosNoNi.compareTo("") == 0) {
				terminosNoNi = elem;
			}else {
				terminosNoNi= terminosNoNi+elem;
			}
		}
		
		return terminosNoNi;
	}



	
	public static HashMap<String, String[]>  establecerBinariosNONI(String funcion, String terminosNoNi, int debug) {
		
		String[] temp_funcion = funcion.split("=");
			
		if (debug==0) {
			return FuncionesComunes.establecerBinarios(temp_funcion[0]+"="+terminosNoNi, 2);
		} else {
			return FuncionesComunes.establecerBinarios(temp_funcion[0]+"="+terminosNoNi, 0);
		}
		
		

	}


	
	public static HashMap<String, String[]>  establecerBinariosNONImaxterms(String funcion, String terminosNoNi, int debug) {
		
		String[] temp_funcion = funcion.split("=");
			
		if (debug==0) {
			return FuncionesComunes.establecerBinariosMaxterms(temp_funcion[0]+"="+terminosNoNi, 2);
		} else {
			return FuncionesComunes.establecerBinariosMaxterms(temp_funcion[0]+"="+terminosNoNi, 0);
		}
		
		

	}
	
	
	
	
	
	public static HashMap<String, String[]> emparejar(HashMap<String, String[]> datos,
						HashMap<Integer, HashMap<Integer, ArrayList<String>>> valores,
																	 int num_terminos, 
																	  		int debug) {


		ArrayList<String> elementos_seccion = new ArrayList<String>(); //IDENTIFICADORES de los elementos de una seccion con un numero de 1os concreto dado por 'i'
		HashMap<Integer, ArrayList<String>> secciones = new HashMap<>();  // nº 1os,elementos //Con un 1, array de valores, con dos 1os, array de valores //Se va sobreescribiendo //BORRAR LOS VISITADOS ANTES DE PASARLO AL HASHMAP secciones
		//valores // id seccion,hashmap valores de la seccion completa //Elementos de una seccion, en la seccion 0, con un 1, array de valores
		ArrayList<String> vistos = new ArrayList<>(); // ids de cada uno de los valores utilizados para la siguiente seccion, estos los borraremos de secciones
		// HashMap<String, String[]> datos;// Obtenido por referencia, id,
		// binarios de cada valor

		// RONDA INICIAL //CALCULAMOS LA SECCION 0
		// Recorremos para encontrar desde 0 (1os) hasta el máximo (5 -> 1os)
		int numero_seccion = 0;
		for (int i = 0; i <= num_terminos;) {

			for (Entry<String, String[]> entry : datos.entrySet()) { // La seccion 0 la creamos con los datos obtenidos
				String key = entry.getKey();
				String[] value = entry.getValue();

				int encontrados = 0;
				for (int j = 0; j < num_terminos; j++){
					if (value[j].equals("1"))
						encontrados++;
				}

				if (encontrados == i) {
					elementos_seccion.add(key);
				}
			}
			
			// imprimimos los valores de cada seccion
			if (!elementos_seccion.isEmpty()) {

				ArrayList<String> nuevo = new ArrayList<String>();
				nuevo = (ArrayList<String>) elementos_seccion.clone();
				secciones.put(i, nuevo);

				elementos_seccion.clear();
			}

			i++;
		} // fin for seccion 0
		
		
		if (debug == 0) {
			System.out.println("\nRESULTADOS PRIMERA TABLA (Seccion 0)\n");
			System.out.println("Nºs\t\tVALORES");
			for (Entry<Integer, ArrayList<String>> entry : secciones.entrySet()) { // La seccion 0 la creamos con los datos obtenidos
				Integer key = entry.getKey();
				ArrayList<String> value = entry.getValue();

				System.out.println(key + "\t->\t" + value);
			}
		}
		

		numero_seccion = 0;
		valores.put(numero_seccion, secciones); // Añadimos con numero de seccion 0 todos los valores obtenidos aqui

		HashMap<Integer, ArrayList<String>> seccion = (HashMap<Integer, ArrayList<String>>) valores.get(numero_seccion).clone(); // comenzamos con la seccion0

		HashMap<Integer, ArrayList<String>> nueva_seccion;
		int intentos = 1;
		int indice_seccion = 1;
		boolean seguirProbando;
		do {
			nueva_seccion = new HashMap<>();
			seguirProbando = false;

			int seccion_index = 0;
			ArrayList<String> seccion0 = null;

			if (seccion.size() > 1) {
				// Creo la siguiente seccion completa y voy almacenando cada una
				// de las secciones parciales (elementos con un 1, elementos con
				// 2 unos) finalmente la añado a valores
				for (Entry<Integer, ArrayList<String>> seccion1 : seccion.entrySet()) {

					if (seccion_index == 0) {
						seccion_index++;
						seccion0 = new ArrayList<>();
						seccion0 = (ArrayList<String>) seccion1.getValue().clone();
					} else {

						HashMap<Integer, ArrayList<String>> incluir = comprobarSimilitudes(datos, seccion0, seccion1.getValue(), vistos, debug);

						for (Entry<Integer, ArrayList<String>> entrada : incluir.entrySet()) {
							if (nueva_seccion.get(entrada.getKey()) == null) {
								nueva_seccion.put(entrada.getKey(), new ArrayList<>());
							}
							for (String el : entrada.getValue()) {
								nueva_seccion.get(entrada.getKey()).add(el);
							}

						}
						seccion0 = new ArrayList<>();
						seccion0 = (ArrayList<String>) seccion1.getValue().clone();
						seccion_index++;
					}
					
				}
				
				// añadir la sección completa nueva a valores
				numero_seccion++;
				
				if (nueva_seccion.size() != 0) {
					valores.put(numero_seccion, (HashMap<Integer, ArrayList<String>>) nueva_seccion.clone());
				}
				

				
			}

			seccion.clear();
			if (nueva_seccion.size() != 0) {
				seguirProbando = true;
				nueva_seccion.clear();
			}
			
			if (seguirProbando == true) { // podríamos comprobar si la nueva seccion tiene tamaño diferente de 0 para seguir sacandole valores
				try {
					seccion = (HashMap<Integer, ArrayList<String>>) valores.get(numero_seccion).clone(); // vamos iterando en cada nivel de iteración hasta que no haya más

					
					if (seccion.size() <= 1) {
						if (debug == 0) {
							System.out.println("\n\nTenemos " + seccion.size() + " columnas y no creamos nueva seccion");
						}
						seguirProbando = false;
					} else {
						if (debug == 0) {
							System.out.println("Continuamos creando secciones porque tenemos " + seccion.size() + " columnas");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} while (seguirProbando == true);
		
		HashMap<String, String[]> utilizados = new HashMap<String, String[]>();
		
		utilizados = (HashMap<String, String[]>) datos.clone();
		
		if (debug == 0) {
			
			System.out.println("VISTOS (Eliminamos los que ya hemos checkeado para dejar solo los que utilizaremos para la tabla de primeros integrantes)");
			for (String elemento_visto : vistos) {
//				System.out.println(elemento_visto);
				utilizados.remove(elemento_visto);
			}
			
			System.out.println("UTILIZADOS PARA TABLA DE PRIMEROS INTEGRANTES");
			for (Entry<String, String[]> util : utilizados.entrySet()) {
				System.out.println(util.getKey());
			}
			
		} else {
			for (String elemento_visto : vistos) {
				utilizados.remove(elemento_visto);
			}
		}
		
		return utilizados;
		
		
	}


	
	
	
	
	
	
	public static HashMap<Integer, ArrayList<String>> comprobarSimilitudes(HashMap<String, String[]> datos,
																				ArrayList<String> seccion0,
																				ArrayList<String> seccion1,
																				  ArrayList<String> vistos,
																								int debug) {

		if (debug == 0) {
			System.out.println("\nComprobamos similitudes.");
		}
		
		
		HashMap<Integer, ArrayList<String>> nueva_seccion = new HashMap<>(); // Creada a partir de los valores con similitudes
		HashMap<String, String[]> nuevo_dato; // identificador, binarios //Datos que serán introducidos en el array general de datos
		Integer indice_seccion = 0; // numero de "-" que nos guiará para saber la sección en la que estamos

		int coincidencias = 0;
		int indice_coincidencia = 0;
		int numero_de_unos = 0;
		ArrayList<String> nuevo_elemento_seccion = new ArrayList<String>();

		if (debug == 0) {
			System.out.println("Elementos en seccion0: " + seccion0);
		}

		for (String s0 : seccion0) {
			if (debug == 0) {
				System.out.println(s0 + " - " + Arrays.toString(datos.get(s0)));
			}
			indice_seccion = 0;

			String[] elemento_s0 = datos.get(s0);
			coincidencias = elemento_s0.length;
			indice_coincidencia = 0;

			for (String s1 : seccion1) {
				String[] elemento_s1 = datos.get(s1);

				coincidencias = elemento_s0.length;
				indice_coincidencia = 0;
				numero_de_unos = 0;

				for (int i = 0; i < elemento_s1.length; i++) {

					if (elemento_s0[i].equals("1")) {
						numero_de_unos++;
					}

					if (elemento_s0[i].equals("0") && elemento_s1[i].equals("0")) {
						coincidencias--;
					} else if (elemento_s0[i].equals("1") && elemento_s1[i].equals("1")) {
						coincidencias--;

					} else if (elemento_s0[i].equals("-") && elemento_s1[i].equals("-")) {
						indice_seccion++;
						coincidencias--;
					} else if (elemento_s0[i].equals("0") && elemento_s1[i].equals("1")) {
						indice_coincidencia = i;
					}

				} // fin for elemento a elemento de seccion0

				if (coincidencias == 1) {
					nuevo_dato = new HashMap<>();

					String[] elemento_para_guardar = datos.get(s0).clone();
					elemento_para_guardar[indice_coincidencia] = "-"; // Añadimos el guion al valor correspondiente
					datos.put(s0 + "," + s1, elemento_para_guardar); // Almacenamos en datos su binario correspondiente

					nuevo_elemento_seccion.add(s0 + "," + s1);
					vistos.add(s0);
					vistos.add(s1);
				}

			} // fin for seccion1

		}

		if (debug == 0) {
			System.out.println("Elementos en seccion1: " + seccion1);
			for (String el : seccion1) {
				System.out.println(el + " - " + Arrays.toString(datos.get(el)));
			}
		}
		if (!nuevo_elemento_seccion.isEmpty()) {
			nueva_seccion.put(numero_de_unos, nuevo_elemento_seccion);

			if (debug == 0) {
				System.out.println("Nueva seccion con: " + numero_de_unos + " unos");
				for (Entry<Integer, ArrayList<String>> sec : nueva_seccion.entrySet()) {
					for (String val : sec.getValue()) {
						System.out.println(
								"Seccion: " + sec.getKey() + " - " + val + " - " + Arrays.toString(datos.get(val)));
					}
				}
			} // FIN DEBUG
		} else {

			if (debug == 0) {
				System.out.println("No ha salido ninguna nueva seccion.");
			}
		}

		return nueva_seccion;
	}
	
	
	
	
	
public static HashMap<String, String[]> eliminarDuplicados(HashMap<String, String[]> utilizados, int debug) {
		
		ArrayList<String> unicos = new ArrayList<>();
		ArrayList<String> evitar = new ArrayList<>();
		HashMap<String, String[]> utilizados_temp = new HashMap<>();
		utilizados_temp = (HashMap<String, String[]>) utilizados.clone();
		
		int ocurrencias = 0;
		if (debug == 0) {
			System.out.println("\nELIMINAMOS LOS MINTERMS QUE APARECEN VARIAS VECES EN DIFERENTE ORDEN" );
		}
		
		for (Entry<String, String[]> u1 : utilizados.entrySet()) {
			
			ocurrencias = 0;
			String[] u1_temp = u1.getKey().split(",");
			
			if (!evitar.contains(u1.getKey())) {

				for (Entry<String, String[]> u2 : utilizados_temp.entrySet()) {
					String[] u2_temp = u2.getKey().split(",");
					int valores_iguales = u2_temp.length;
					
					for (int i = 0; i < u1_temp.length; i++) {
						
						for (int j = 0; j < u2_temp.length; j++) {
							if (u1_temp[i].compareTo(u2_temp[j]) == 0) {
								valores_iguales--;
							}
						}	
					}// for u1_temp.lenght
					
					if (valores_iguales == 0) {
						ocurrencias++;
						if (u1.getKey().compareTo(u2.getKey()) == 0) {
							unicos.add(u1.getKey());
							if (debug == 0)
								System.out.println("Añadimos "+ u1.getKey());
						}else {
							if (debug == 0)
								System.out.println("Evitamos "+ u2.getKey() );
							evitar.add(u2.getKey());
						}
					}
				}
				
				if (debug == 0) {
					System.out.println("Aparece: "+ocurrencias + " veces");
				}
				
			}
			
		}
		
		if (debug == 0) {
			System.out.println("\nEVITAR");
			for (String un : evitar) {
				System.out.println(un);
				utilizados.remove(un);
			}
			
			System.out.println("FINALES");
			for (Entry<String, String[]> unic : utilizados.entrySet()) {
				System.out.println(unic.getKey());
			}
		} else {
			for (String un : evitar) {
				utilizados.remove(un);
			}
		}
		
		return utilizados;
		
	}







public static ArrayList<String> primerosImplicantes(HashMap<String, String[]> datos_iniciales, 
														 HashMap<String, String[]> datos_noni,
														 HashMap<String, String[]> utilizados,
														 							int debug) {
	
	
	HashMap<String, ArrayList<String>> implicantes = new HashMap<>();
	HashMap<String, String[]> finales = new HashMap<>();
	ArrayList<String> obligatorios = new ArrayList<>(); // Aqui devolveremos los id que luego buscaremos en datos como finales para mostrar el resultado
	ArrayList<String> posibles = new ArrayList<>();
	
//	debug = 0;
	
	if (debug == 0) {
		System.out.println("\nCREAMOS TABLAS DE PRIMEROS IMPLICANTES");
		System.out.println("TERMINOS INICIALES");
		int index_print = 0;
		for (Entry<String, String[]> u1 : datos_iniciales.entrySet()) {
			if (index_print == 0) {
				System.out.print(u1.getKey());
				index_print++;
			}
			else {
				System.out.print(" - " + u1.getKey());
			}
		}
		index_print = 0;
		System.out.println("\nTERMINOS CREADOS QUE CUBREN TODA LA TABLA");
		for (Entry<String, String[]> u1 : utilizados.entrySet()) {
			if (index_print == 0) {
				System.out.print(u1.getKey());
				index_print++;
			}
			else {
				System.out.print(" - " + u1.getKey());
			}
		}
		System.out.println("");
	}
	

	
	//Por cada inicial, comprobar cuantos de los que sacamos al emparejar necesitamos para la tabla de implicantes
	for (Entry<String, String[]> termino_inicial : datos_iniciales.entrySet()) {
		
			for (Entry<String, String[]> utilizado : utilizados.entrySet()) {
				String[] array_utilizado = utilizado.getKey().split(",");
				for (String posible : array_utilizado) {
					
					if (posible.compareTo(termino_inicial.getKey()) == 0) {
						if (implicantes.get(termino_inicial.getKey()) == null) {
							implicantes.put(termino_inicial.getKey(), new ArrayList<>());
						}
						implicantes.get(termino_inicial.getKey()).add(utilizado.getKey());
					}
				}
			}
	}


	
	
	//Comprobar obligatorios y crear hashmmap que cubra todo
	
	
	if (debug == 0) {
		System.out.println("\nCOMPROBAMOS TABLA DE IMPLICANTES");
	}

	boolean es_noni;
	for (Entry<String, ArrayList<String>> tabla_implicantes : implicantes.entrySet()) {
		if (debug==0) {
			System.out.println(tabla_implicantes.getKey() + " -> " + tabla_implicantes.getValue());
		}
		
		
		es_noni = false;
		if (!datos_noni.isEmpty()) { //Evitamos los terminos NONI de los imprescindibles
			for (Entry<String, String[]> termino_noni : datos_noni.entrySet()) { //por cada noni
				for (String implicante : tabla_implicantes.getValue()) { //Miramos que no esté entre los implicantes
					
					String[] implicante_splited;
					if (implicante.split(",").length > 1) {
						implicante_splited = implicante.split(",");
					}else {
						implicante_splited = new String[1];
						implicante_splited[0] = implicante;
					}
					
					for (String implicante_es_noni : implicante_splited) { //Dividimos un implicante en espacios para comprobarlo correctamente
						if (implicante_es_noni.equals(termino_noni.getKey())) { //Si está entre los noni lo descartamos como obligatorio
							es_noni = true;
						}
					}
					
				}
				
			}
		}
		
		
		if (!es_noni) {
			if (tabla_implicantes.getValue().size() == 1) { //Si solo podemos obtenerlo con este término va a obligatorios
				if (!obligatorios.contains(tabla_implicantes.getValue().get(0))) {
					obligatorios.add(tabla_implicantes.getValue().get(0));
				}
			}
		}
		
		
		if (!obligatorios.contains(tabla_implicantes.getValue().get(0))) { //Si no está en obligatorios irá a posibles
			
			int temp_index = 0;
			String posible = tabla_implicantes.getValue().get(temp_index);
			if (!posibles.contains(posible)) {
				posibles.add(posible);
			} else {
				
				
				while (posibles.contains(posible) && temp_index < tabla_implicantes.getValue().size()) {
					posible = tabla_implicantes.getValue().get(temp_index);
					es_noni=false;
					
					for (Entry<String, String[]> termino_noni : datos_noni.entrySet()) { //Comprobamos que no sea noni y no podamos tomar descartando el noni
						
						String[] implicante_splited;
						if (posible.split(",").length > 1) {
							implicante_splited = posible.split(",");
						}else {
							implicante_splited = new String[1];
							implicante_splited[0] = posible;
						}
						
						for (String implicante_es_noni : implicante_splited) { //Dividimos un implicante en espacios para comprobarlo correctamente
							if (implicante_es_noni.equals(termino_noni.getKey())) { //Si está entre los noni lo descartamos como obligatorio
								es_noni = true;
							}
						}
					}
					
					if (!posibles.contains(posible) && !es_noni) {
						posibles.add(posible);
					}
					temp_index++;
				}
			}	
		} 
	}
	
	if (debug == 0) {
		System.out.println("\nPOSIBLES");
		for (String temp : posibles) {
			System.out.println(temp);
		}
	}
	
	
	
	//Establecemos un conjunto de minterms obligatorios (son los unicos que unifican con el termino a elegir) para luego añadir individualmente los posibles
	ArrayList<String> check_final = new ArrayList<>();
	if (debug == 0)
		System.out.println("\nIMPLICANTES SELECCIONADOS COMO OBLIGATORIOS");
	
	for (String vl : obligatorios) {
		if (debug == 0) {
			System.out.println(vl);
		}
		String[] temp_vl = vl.split(",");
		for (String temp_vl_splited : temp_vl) {
			if (!check_final.contains(temp_vl_splited)) { //Meto por separado cada uno de los elementos de manera univoca para compararlos con los terminos que hay que conseguir
				check_final.add(temp_vl_splited);
			}
		}			
	}
	
	
	
	//añadimos los posibles que acaban de cubrir toda la tabla de implicantes
	for (Entry<String, String[]> final_set : datos_iniciales.entrySet()) {
		
		if (!check_final.contains(final_set.getKey())) { //En este set está cada uno de los términos cogidos por un termino obligatorio, si aún no está incluido, seleccionar el mejor candidato
			
			for (String seleccion_posibles : posibles) { // de cada posible
				String[] temp_seleccion_posibles = seleccion_posibles.split(",");
				
				for (String temp_seleccion_posibles_splitted : temp_seleccion_posibles) {
										
					if (final_set.getKey().compareTo(temp_seleccion_posibles_splitted) == 0) { //comparamos con los obligatorios y lo añadimos o no
						if (!obligatorios.contains(seleccion_posibles)) {
							obligatorios.add(seleccion_posibles);
						}
						
					}
				}
			}
		}		
	}
	
	
	
	if (debug==0) {
		System.out.println("\nDESCARTAMOS LOS NONI SI FUERA NECESARIO");
	}
	
	
	ArrayList<String> noni_obligatorio = new ArrayList<>();
	ArrayList<String> termino_para_eliminar = new ArrayList<>();
	boolean existe = false;

	
	for (String obligatorio : obligatorios) {
		
		if (debug==0) {
			System.out.println("Comprobamos: " + obligatorio);
		}
		if (!termino_para_eliminar.contains(obligatorio)) {
			
			
			String[] obligatorio_splitted;
			if (obligatorio.length() > 2 ) {
				obligatorio_splitted = obligatorio.split(",");
			} else {
				obligatorio_splitted = new String[1];
				obligatorio_splitted[0] = obligatorio;
			}
			
			
			es_noni = false;
			for (String checking : obligatorio_splitted) {
				for (Entry<String, String[]> noni : datos_noni.entrySet()) {
					if (checking.equals(noni.getKey())) {
						es_noni = true;
					}
				}
			}
			
			if (es_noni) {//Si el elemento de estudio tiene un noni comprobamos que no sea imprescindible y lo quitamos
				
				for (String algun_elemento_con_noni : obligatorio_splitted) {
					
					if (!datos_noni.containsKey(algun_elemento_con_noni)) {
						existe = false;
						for (String obligatorio_descartar : obligatorios) {
							if (!obligatorio_descartar.equals(obligatorio) && !termino_para_eliminar.contains(obligatorio_descartar)) {
								String[] obligatorio_descartar_splitted = obligatorio_descartar.split(",");
								for (String final_check : obligatorio_descartar_splitted) {
									if (debug==0) {
										System.out.println(algun_elemento_con_noni + " == " +final_check );
									}
									if (final_check.equals(algun_elemento_con_noni)) {
										existe = true; //Sera veradero si otro termino cubre 
									}
								}
							}
							
						}
						if (debug==0) {
							System.out.println("Existe: "+existe);
						}
						//Si nadie contiene alguno de sus elemento a pesar de tener un noni es obligatorio
						if (!existe) {
							noni_obligatorio.add(obligatorio);
						} else{ //quitamos los que no son imprescindibles
							termino_para_eliminar.add(obligatorio);
						}
						
					}
				}
			}
		}
	}
	
	
	for (String evitar : termino_para_eliminar) {
		
		if (obligatorios.contains(evitar)) {
			if (debug==0) {
				System.out.println("Eliminamos: " + evitar);
			}
			obligatorios.remove(evitar);
		}
	}
	
	
	for (String noni : noni_obligatorio) {
		if (!obligatorios.contains(noni)) {
			if (debug==0) {
				System.out.println("Añadimos: " + noni);
			}
			obligatorios.add(noni);
		}
	}
	
	
	if (debug == 0) {
		System.out.println("\nIMPLICANTES FINALES");
		for (String vl : obligatorios) {
			System.out.println(vl);
		}
		
	}
	
	//si tenemos dos valores que hayan sido seleccionados para los mismo objetivos, escoger el más grande de los dos
	
	return obligatorios;
	
}

public static <T> void mostrarTablaPrimerosImplicantes(String[] terminos,
								HashMap<String, String[]> datos_binarios,
							   HashMap<String, String[]> datos_iniciales,
									HashMap<String, String[]> datos_noni,
										     ArrayList<String> resultado) {

	System.out.println("\nTABLA PRIMEROS IMPLICANTES");

	int tamaño_resultado = 0;
	for (String tamaño : resultado) {
		if (tamaño.length() > tamaño_resultado) {
			tamaño_resultado = tamaño.length();
		}
	}

	if (tamaño_resultado > 8) {
		System.out.print("\t");
	}


	int mostrados = 0;
	for (int i = 0; mostrados < datos_iniciales.size(); i++) {
		if (datos_iniciales.containsKey(Integer.toString(i))) {
			if (!datos_noni.containsKey(Integer.toString(i))) {
				System.out.print("\t" + i);
			}
			mostrados++;
		}
	}
	
	if (!datos_noni.isEmpty()) {
		System.out.print("\t" + "|");
		 mostrados = 0;
		for (int i = 0; mostrados < datos_iniciales.size(); i++) {
			if (datos_iniciales.containsKey(Integer.toString(i))) {
				if (datos_noni.containsKey(Integer.toString(i))) {
					System.out.print("\t" + i);
				}
				mostrados++;
			}
		}
		
		
	}
	System.out.print("\t => "+Arrays.toString(terminos).toUpperCase());
		
	
	
	
	
	for (String implicante : resultado) {

		System.out.print("\n" + implicante + "\t");
		mostrados = 0;
		for (int i = 0; mostrados < datos_iniciales.size(); i++) {
			
			if (datos_iniciales.containsKey(Integer.toString(i))) {
				if (!datos_noni.containsKey(Integer.toString(i))) {
					
					String[] todos = implicante.split(",");					
					if (todos.length > 1) {
						int escrito = 0;
						for (String asignados : todos) {
								if (asignados.compareTo(Integer.toString(i)) == 0) {
									System.out.print("*\t");
									escrito=1;
								}
						}
						
						if (escrito == 0)
							System.out.print(".\t");

					} else {
						for (String asignados : todos) {
							if (asignados.compareTo(Integer.toString(i)) == 0) {
								System.out.print("*\t");
							} else {
								System.out.print(".\t");
							}
						}
					}
					
					
				}
				mostrados++;
			}
		}//fin for
		//mostramos los noni
		if (!datos_noni.isEmpty()) {
			
			System.out.print("|\t");
			mostrados = 0;
			for (int i = 0; mostrados < datos_iniciales.size(); i++) {
				
				if (datos_iniciales.containsKey(Integer.toString(i))) {
					if (datos_noni.containsKey(Integer.toString(i))) {
						
						String[] todos = implicante.split(",");					
						if (todos.length > 1) {
							int escrito = 0;
							for (String asignados : todos) {
									if (asignados.compareTo(Integer.toString(i)) == 0) {
										System.out.print("*\t");
										escrito=1;
									}
							}
							
							if (escrito == 0)
								System.out.print(".\t");

						} else {
							for (String asignados : todos) {
								if (asignados.compareTo(Integer.toString(i)) == 0) {
									System.out.print("*\t");
								} else {
									System.out.print(".\t");
								}
							}
						}
						
						
					}
					mostrados++;
				}
			}//fin for
		}
		System.out.print(" => "+Arrays.toString(datos_binarios.get(implicante)));
		
	}
	
	
	
	/*
	for (String implicante : resultado) {

		System.out.print("\n" + implicante + "\t");

		for (Entry<String, String[]> iniciales : datos_iniciales.entrySet()) {
		
			String[] todos = implicante.split(",");
			if (todos.length > 1) {
				System.out.println("tamaño1");
				int escrito = 0;
				for (String asignados : todos) {
						if (asignados.compareTo(iniciales.getKey()) == 0) {
							System.out.print("*\t");
							escrito=1;
						}
				}
				
				if (escrito == 0)
					System.out.print(".\t");

			} else {
				for (String asignados : todos) {
					if (asignados.compareTo(iniciales.getKey()) == 0) {
						System.out.print("*\t");
					} else {
						System.out.print(".\t");
					}
				}
			}

		}
	}
	*/
	
	System.out.println("");

}





public static void mostrarValores(HashMap<Integer, HashMap<Integer, ArrayList<String>>> valores) {
	
	for (Entry<Integer, HashMap<Integer, ArrayList<String>>> seccion_general : valores.entrySet()) {
		System.out.println("\nSECCION "+seccion_general.getKey());
		System.out.println("Nº 1os\t|\tTERMINOS");
		for (Entry<Integer, ArrayList<String>> col_seccion : seccion_general.getValue().entrySet()) {
			System.out.println(col_seccion.getKey() + "\t|\t" + col_seccion.getValue());	
		}	
	}
}



public static void imprimirResultados(String[] terminos, HashMap<String, String[]> datos, ArrayList<String> resultado) {
	
	System.out.println("\nRESULTADO MINTERMS");
	
	ArrayList<String> mejor_minterms = new ArrayList<>();
	System.out.print("f"+Arrays.toString(terminos).toUpperCase()+" = ");
	int primero = 0;
	for (String mostrar : resultado) {
		if (primero == 0) {
			primero++;
		} else {
			System.out.print(" + ");
		}
		
		String[] minterm = datos.get(mostrar);
		String minter_mostrar = "";
		for (int i = 0; i < minterm.length; i++) {
			if (minterm[i].equals("0")) {
				minter_mostrar+=terminos[i].toUpperCase();
			}else if (minterm[i].equals("1")) {
				minter_mostrar+=terminos[i].toLowerCase();
			}
		}
		
		System.out.print(minter_mostrar);
		mejor_minterms.add(minter_mostrar.replaceAll("\\s+", "").replaceAll("\\s+", ""));

		
	}
	
	System.out.println("\nRESULTADO MAXTERMS");
	
	ArrayList<String> mejor_maxterms = new ArrayList<>();
	System.out.print("f"+Arrays.toString(terminos).toUpperCase()+" = ");
	primero = 0;
	for (String mostrar : resultado) {
		
		String[] maxterm = datos.get(mostrar);
		String maxterm_mostrar = "";
		for (int i = 0; i < maxterm.length; i++) {
			if (maxterm[i].equals("0")) {
				if (maxterm_mostrar.length() == 0) {
					maxterm_mostrar+=terminos[i].toLowerCase();
				}else {
					maxterm_mostrar+=" + "+terminos[i].toLowerCase();
				}
				
			}else if (maxterm[i].equals("1")) {
				if (maxterm_mostrar.length() == 0) {
					maxterm_mostrar+=terminos[i].toUpperCase();
				} else {
					maxterm_mostrar+=" + "+terminos[i].toUpperCase();
				}
				
			}
		}
		System.out.print("("+maxterm_mostrar+")");
		mejor_maxterms.add(maxterm_mostrar.replaceAll("\\s+", ""));
	}
	
	
	//Comparar y decir cual es el mejor resultado

	int negados_min = 0;
	int num_terms_min= 0;
	for (String comparacion : mejor_minterms) {
		String[] characters = comparacion.split("\\+");
		
		for (String letra : characters) {
			if (letra.equals(letra.toLowerCase())) {
				negados_min++;
			}
			num_terms_min++;
		}
	}
	num_terms_min+=mejor_minterms.size();
	
	int num_terms_max = 0;
	int negados_max = 0;
	for (String comparacion : mejor_maxterms) {
		String[] characters = comparacion.split("\\+");
		
		for (String letra : characters) {
			if (letra.equals(letra.toUpperCase())) {
				negados_max++;
				
			}
			num_terms_max++;
		}
	}
	num_terms_max+=mejor_maxterms.size();
	
	System.out.println("\n\nMEJOR SOLUCION");
	
	
	if (num_terms_max < num_terms_min) {
		int diferencia = num_terms_min - num_terms_max;
		int num_negados = negados_min - negados_max;
		
		int resultado_final = diferencia - num_negados;
		
		
		if (resultado_final < 0) {
			System.out.println("MAXTERMS");
		} else if (resultado_final > 0) {
			System.out.println("MINTERMS");
		} else {
			System.out.println("PUEDES ESCOGER CUALQUIER SOLUCION");
		}
		
	} else if (num_terms_max > num_terms_min) {
		int diferencia = num_terms_max - num_terms_min;
		int num_negados = negados_max - negados_min;
		
		int resultado_final = diferencia - num_negados;
		
		
		if (resultado_final < 0) {
			System.out.println("===> MINTERMS");
		} else if (resultado_final > 0) {
			System.out.println("===> MAXTERMS");
		} else {
			System.out.println("PUEDES ESCOGER CUALQUIER SOLUCION");
		}
		
	} else {
		

		int resultado_final = negados_max - negados_min;
		
		if (resultado_final < 0) {
			System.out.println("===> MAXTERMS");
		} else if (resultado_final > 0) {
			System.out.println("===> MINTERMS");
		} else {
			System.out.println("PUEDES ESCOGER CUALQUIER SOLUCION");
		}
	}
	

	
	System.out.println("\nPulsa una \"ENTER\" para continuar...");
	
	String continuar = new Scanner(System.in).nextLine();
	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	
	
	
}




}
