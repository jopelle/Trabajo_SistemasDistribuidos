package principal;

import java.io.IOException;
import java.net.Socket;
import cartas.Carta;
import modeloCliente.*;

public class Cliente {

	public static void main(String[] args) {
		try(Socket socket = new Socket("localhost", 6666);){
						
			ModeloCliente cliente=new ModeloCliente("Jon",socket);
			
			//Recibe la mano
			cliente.recibirMano();
			
			while(/*cliente.continua()==*/true) {
				//Recibe la mesa
				cliente.recibirMesa();
				//Elegir una carta
				Carta c=cliente.elegirCarta();
				if(c==null) {
					if(cliente.robar()) {
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
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
