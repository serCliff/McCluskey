package exec;

import java.io.IOException;
import java.util.Scanner;

import Comun.FuncionesComunes;
import Resultados.Maxterms;
import Resultados.Minterms;

public class Ejecutable extends Thread{

	// f(A,B,C,D,E) = aBC + Ade + ABCd+abc+acbd+abcde
	// f(A,B,C,D) = b + b d + a+ ad+ acd + bc + bcd
	// f(A,B,C,D) = ABCD + c + cd + bd + bcd + a + ac + acd + abcd
	// f(A,B,C,D,E) = de + b + be+ bd + bde + bc + bcd + ade + ab + abde + bce + abd
	// f(A,B,C,D,E) = de + c + ce+ cd + cde

	

	public static void main(String[] args) {
		
		//DEBUG = 0 PARA VER EL PROCESO COMPLETO DE EJECUCIÓN
		int debug = 1;
		
		
		Thread minterms = new Minterms(debug);
		Thread maxterms = new Maxterms(debug);
		
		String accion = "";
		do {
			
			accion = FuncionesComunes.titular();

			if (accion.equals("1")) {
				maxterms.run();
			} else if (accion.equals("2")) {
				minterms.run();
			}
			
			
		} while (!accion.equals("3"));
		
		
	}
	
	
	
	
	
	
}
