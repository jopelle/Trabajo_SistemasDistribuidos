package principal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static void main(String[] args) {
		
		try (ServerSocket server=new ServerSocket(6666);){
			ControladorPartida partida= new ControladorPartida();
			try {
				Socket cliente=server.accept();
				Hilo hilo=new Hilo(cliente,partida.players.get(0));
				hilo.start();
				
				while(partida.getGameOver()==false) {
					
				}
	
			}catch(IOException e) {e.printStackTrace();}
		}catch(IOException e) {e.printStackTrace();}
		
	}
}
