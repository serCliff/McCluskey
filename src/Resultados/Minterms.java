package Resultados;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import Comun.FuncionesComunes;

public class Minterms {

	private static HashMap<String, String[]> datos;
	private static HashMap<String, String[]> datosNONI;
	private static HashMap<Integer, HashMap<Integer, ArrayList<String>>> valores;
	private static HashMap<String, String[]> utilizados;
	private static HashMap<String, String[]> implicantes;
	private static int num_terminos;

	public static void main(String[] args) {

		
		//DEBUG = 0 PARA VER EL PROCESO COMPLETO DE EJECUCIÓN
		int debug = 1;

		// f(A,B,C,D,E) = aBC + Ade + ABCd+abc+acbd+abcde
		// f(A,B,C,D) = b + b d + a+ ad+ acd + bc + bcd
		// f(A,B,C,D) = ABCD + c + cd + bd + bcd + a + ac + acd + abcd
		// f(A,B,C,D,E) = de + b + be+ bd + bde + bc + bcd + ade + ab + abde + bce + abd
		// f(A,B,C,D,E) = de + c + ce+ cd + cde

		

		
		HashMap<String, String[]> datos_iniciales = new HashMap<>();
		ArrayList<String> resultado = new ArrayList<>();
		valores = new HashMap<Integer, HashMap<Integer, ArrayList<String>>>();
		datos = new HashMap<>();
		datosNONI = new HashMap<>();
		String[] terminos;
		String[] terminosNONI;
		
		FuncionesComunes.titular();
		
		String funcion = FuncionesComunes.pedirFuncionMinterms();
		String funcionNoNi = FuncionesComunes.pedirTerminosNONI();
		
		terminos = FuncionesComunes.obtenerTerminos(funcion).clone();
		num_terminos = terminos.length;
		datos = (HashMap<String, String[]>) FuncionesComunes.establecerBinarios(funcion, 0);
		
		if (!funcionNoNi.isEmpty()) { //Si hay datosNONI los añado a datos
			datosNONI = FuncionesComunes.establecerBinariosNONI(funcion, funcionNoNi);
						
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
		
		datos_iniciales = (HashMap<String, String[]>) datos.clone(); //TODO: Hacer distincion NONI

		utilizados = (HashMap<String, String[]>) FuncionesComunes.emparejar(datos, valores, num_terminos, debug);

		utilizados = FuncionesComunes.eliminarDuplicados(utilizados, debug);

		resultado = FuncionesComunes.primerosImplicantes(datos_iniciales, datosNONI, utilizados, 0); //TODO: METER VALORES NONI PARA SEPARARLOS

		FuncionesComunes.mostrarValores(valores);
		FuncionesComunes.mostrarTablaPrimerosImplicantes(datos_iniciales, resultado);
		FuncionesComunes.imprimirResultados(terminos, datos, resultado);

		
	}

	

}
