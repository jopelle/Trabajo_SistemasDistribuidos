package principal;

import java.io.IOException;
import java.net.Socket;
import cartas.Carta;

public class Cliente {

	public static void main(String[] args) {
		try(Socket socket = new Socket("10.11.62.23", 6666);){
						
			ModeloCliente cliente=new ModeloCliente("Jon",socket);
			boolean fin=false;
			boolean continua;
			
			//Recibe la mano
			cliente.recibirMano();
			
			while(!fin) {
				//Recibe la mesa, si recibe fin en vez de la mesa, la partida se acabo
				continua=cliente.recibirMesa();

				if(continua) {
					//Elegir una carta, si no se puede elegir (null), se roba y si tampoco se pasa
					Carta c=cliente.elegirCarta();
					if(c==null) {
						boolean b=cliente.robar();
						if(b==true) {
							c=cliente.elegirCarta();
						}
					}
					
					if(c!=null) {
						cliente.enviarCarta(c);
					}
					else {
						cliente.pasar();
					}
				}
				else {
					fin=true;
				}
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
