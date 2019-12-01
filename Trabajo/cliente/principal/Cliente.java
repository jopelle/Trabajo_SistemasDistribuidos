package principal;

import java.io.IOException;
import java.net.Socket;

import modeloCliente.*;

public class Cliente {

	public static void main(String[] args) {
		try(Socket socket = new Socket("localhost", 6666);){
						
			ModeloCliente cliente=new ModeloCliente("Jon",socket);
			
			cliente.recibirMano();
			System.out.println(cliente.getName());
			
			
			
			while(continua==true) {
				cliente.recibirMesa();
				cliente.enviarCarta();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
