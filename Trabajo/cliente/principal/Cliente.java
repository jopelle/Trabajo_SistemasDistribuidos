package principal;

import java.io.IOException;
import java.net.Socket;

import modeloCliente.*;

public class Cliente {

	public static void main(String[] args) {
		try(Socket socket = new Socket("localhost", 6666);){
			
			String juegoContinua="si";
			
			ModeloCliente cliente=new ModeloCliente("Jon",socket);
			
			cliente.recibirMano();
			
			while(juegoContinua.equals("si")) {
				cliente.recibirMesa();
				cliente.enviarCarta();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
