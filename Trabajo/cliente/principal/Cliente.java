package principal;

import java.io.IOException;
import java.net.Socket;

import cartas.Carta;
import interfaz.framePrueba;

public class Cliente {

	public static void main(String[] args) {
		try(Socket socket = new Socket("localhost", 6666);){
						
			ModeloCliente cliente=new ModeloCliente(socket);
			boolean fin=false;
			String mensaje;
			
			//Enviar nombre
			cliente.elegirNombre();
			
			//Recibe la mano
			cliente.recibirMano();
			
			while(!fin) {
				/*Recibe un mensaje, "continua" (tu turno), "actualizar"(se actualiza la mesa)
				o "fin"(la partida se acabó)*/		
				mensaje=cliente.recibirMensaje();
				
				

				if(mensaje.equals("continua")) {
					//Elegir una carta, si no se puede elegir (null), se roba y si tampoco se pasa
					Carta c=cliente.elegirCarta();
					if(c==null) {
						boolean b=cliente.robar();
						if(b==true) {
							c=cliente.elegirCarta();
							if(c!=null) {
								cliente.enviarCarta(c);
							}
							else { cliente.pasar();}
						}
					}
					else {
						cliente.enviarCarta(c);
					}
				}
				else if(mensaje.equals("actualizar")) {}
				else {
					fin=true;
				}
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
