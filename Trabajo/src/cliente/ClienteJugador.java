package cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cartas.Carta;
import cartas.Palo;

public class ClienteJugador {
	
	private static String nombre;
	private static List<Carta> mano=new ArrayList<>();
	private static MesaJugador mesaJugador=new MesaJugador();
	private static List<Carta> cartasColocables=new ArrayList<>();
	
	public static void main(String[] args) {
		
		/*try(Socket c = new Socket("localhost", 6666);
			BufferedWriter out= new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));*/
			Scanner teclado=new Scanner(System.in);//){
			
			/*String s=in.readLine();
			String m=in.readLine();
			
			System.out.println(s);
			System.out.println(m);*/
			
			traducirMano("[[7 Oros], [2 Bastos], [5 Copas], [S Bastos], [1 Bastos], [5 Oros], [4 Copas], [4 Oros], [6 Oros], [6 Espadas]]");
			
			System.out.println(mano.get(0));
						
			/*for(int i=0;i<mano.size();i++) {
				if(mesaJugador.colocable(mano.get(i))) {
					cartasColocables.add(mano.get(i));
				}
			}
			
			System.out.println(cartasColocables);
			colocarCarta(cartasColocables.get(0));
			
			System.out.println(mesaJugador);
			System.out.println(mano);*/

						
		//}catch(IOException e) {e.printStackTrace();}
	}
	
	//Transformar la cadena en una lista de cartas
	private static void traducirMano(String cadena) {
		String s=cadena.replace("[", "");
		s=s.replace("]", "");
		
		String[]cartas=s.split(",");
		String[]stringCarta;
		Carta carta;
		int valor;
		
		for(int i=0;i<cartas.length;i++) {
			if(i!=0){
				cartas[i]=cartas[i].replaceFirst(" ", "");
			}
			stringCarta=cartas[i].split(" ");
			
			if(stringCarta[0].equals("S")) {
				valor=8;
			}
			else if(stringCarta[0].equals("C")) {
				valor=9;
			}
			else if(stringCarta[0].equals("R")) {
				valor=10;
			}
			else {
				valor=Integer.valueOf(stringCarta[0]);
			}
			
			
			if(stringCarta[1].equals("Oros")) {
				carta=new Carta(Palo.Oros,valor);
			}
			else if(stringCarta[1].equals("Espadas")) {
				carta=new Carta(Palo.Espadas,valor);
			}
			else if(stringCarta[1].equals("Bastos")) {
				carta=new Carta(Palo.Bastos,valor);
			}
			else {
				carta=new Carta(Palo.Copas,valor);
			}
			
			mano.add(carta);
		}
	}
	
	private static void colocarCarta(Carta carta){
		mesaJugador.colocar(carta);
		mano.remove(carta);
		//enviar carta al servidor
	}
}
