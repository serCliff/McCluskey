package Comun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class FuncionesComunes {
	
	
	public static void titular() {
		
		System.out.println(" __  __           ____ _           _              \n"
						  +"|  \\/  | ___     / ___| |_   _ ___| | _____ _   _ \n"
						  +"| |\\/| |/ __|   | |   | | | | / __| |/ / _ \\ | | |\n"
						  +"| |  | | (__    | |___| | |_| \\__ \\   <  __/ |_| |\n"
						  +"|_|  |_|\\___|    \\____|_|\\__,_|___/_|\\_\\___|\\__, |\n"
						  +"                                            |___/ \n");
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
		
		funcion = funcion.replaceAll("\\s+","");
		funcion = funcion.toLowerCase();
		
		String temp = funcion.split("\\(")[1];
		temp = temp.split("\\)")[0];
		values = temp.split(",");

		return values;

	}
	
	public static HashMap<String, String[]> establecerBinarios(String funcion, int debug) {
		
		HashMap<String, String[]> resultados = new HashMap<String, String[]>();
		
		String[] terminos = FuncionesComunes.obtenerTerminos(funcion);
		funcion = funcion.replaceAll("\\s+","");
		String f_minterms = funcion.split("=")[1];
		String[] minterms = f_minterms.split("\\+");
				
		
		for (int i = 0; i < minterms.length; i++) {
			
			String new_val = "";
			String[] valSplited = new String[terminos.length];
			for (int j = 0; j < terminos.length; j++) {
				
				if (minterms[i].indexOf(terminos[j]) != -1 ) {
					valSplited[j] = "1";
					new_val=new_val+"1";
				}else{
					valSplited[j] = "0";
					new_val=new_val+"0";
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
	
	public static HashMap<String, String[]>  establecerBinariosNONI(String funcion, String terminosNoNi) {
		
		String[] temp_funcion = funcion.split("=");
			
		return FuncionesComunes.establecerBinarios(temp_funcion[0]+"="+terminosNoNi, 2);

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
				for (int j = 0; j < num_terminos; j++)
					if (value[j] == "1")
						encontrados++;

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

					if (elemento_s0[i] == "1") {
						numero_de_unos++;
					}

					if (elemento_s0[i] == "0" && elemento_s1[i] == "0") {
						coincidencias--;
					} else if (elemento_s0[i] == "1" && elemento_s1[i] == "1") {
						coincidencias--;

					} else if (elemento_s0[i] == "-" && elemento_s1[i] == "-") {
						indice_seccion++;
						coincidencias--;
					} else if (elemento_s0[i] == "0" && elemento_s1[i] == "1") {
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







public static ArrayList<String> primerosImplicantes(HashMap<String, String[]> datos_iniciales, HashMap<String, String[]> datos_noni, HashMap<String, String[]> utilizados, int debug) {
	
	
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
	//TODO: Comprobar NO/NI
	
	if (debug == 0) {
		System.out.println("\nCOMPROBAMOS TABLA DE IMPLICANTES");
	}

	
	for (Entry<String, ArrayList<String>> tabla_implicantes : implicantes.entrySet()) {
		if (debug==0) {
			System.out.println(tabla_implicantes.getKey() + " -> " + tabla_implicantes.getValue());
		}
		
		
		boolean es_noni = false;
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
			if (tabla_implicantes.getValue().size() == 1) {
				if (!obligatorios.contains(tabla_implicantes.getValue().get(0))) {
					obligatorios.add(tabla_implicantes.getValue().get(0));
				}
			}
		}
		
		//TODO: Revisar esta parte y mirar a ver que pasa con los valores que no logra incluir pero son necesarios
		if (!obligatorios.contains(tabla_implicantes.getValue().get(0))) {
			
			int temp_index = 0;
			String posible = tabla_implicantes.getValue().get(temp_index);
			if (!posibles.contains(posible)) {
				posibles.add(posible);
			} else {
				
				while (!posibles.contains(posible) && temp_index <= tabla_implicantes.getValue().size()) {
					temp_index++;
					posible = tabla_implicantes.getValue().get(temp_index);
					System.out.println(temp_index +" != "+ tabla_implicantes.getValue().size());
				}
				
				if (temp_index <= tabla_implicantes.getValue().size()) {
					posibles.add(posible);
				}
				
			}
			
		} 
		
	}
	
	System.out.println("\nPOSIBLES");
	for (String temp : posibles) {
		System.out.println(temp);
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
			if (!check_final.contains(temp_vl_splited)) {
				check_final.add(temp_vl_splited);
			}
		}			
	}
	
	
	//añadimos los posibles que acaban de cubrir toda la tabla de implicantes
	//TODO: Utilizar terminos noni si es necesario
	for (Entry<String, String[]> final_set : datos_iniciales.entrySet()) {
		
		if (!check_final.contains(final_set.getKey())) {
			
			for (String seleccion_posibles : posibles) {
				String[] temp_seleccion_posibles = seleccion_posibles.split(",");
				
				for (String temp_seleccion_posibles_splitted : temp_seleccion_posibles) {
					if (final_set.getKey().compareTo(temp_seleccion_posibles_splitted) == 0) {
						if (!obligatorios.contains(seleccion_posibles)) {
							obligatorios.add(seleccion_posibles);
						}
						
					}
				}
			}
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

public static void mostrarTablaPrimerosImplicantes(HashMap<String, String[]> datos_iniciales,
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

	for (Entry<String, String[]> iniciales : datos_iniciales.entrySet()) {
		System.out.print("\t" + iniciales.getKey());
	}

	for (String implicante : resultado) {

		System.out.print("\n" + implicante + "\t");

		for (Entry<String, String[]> iniciales : datos_iniciales.entrySet()) {
		
			String[] todos = implicante.split(",");
			if (todos.length > 1) {
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
	
	System.out.println("\nRESULTADO");
	
	System.out.print("f"+Arrays.toString(terminos)+" = ");
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
			if (minterm[i] == "0") {
				minter_mostrar+=terminos[i].toUpperCase();
			}else if (minterm[i] == "1") {
				minter_mostrar+=terminos[i].toLowerCase();
			}
		}
		
		System.out.print(minter_mostrar);
		
	}
}




}
