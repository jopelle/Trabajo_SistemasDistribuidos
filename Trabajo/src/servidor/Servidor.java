package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import scartas.ControladorPartida;
import scartas.SJugador;

public class Servidor{
	
	protected static int njugadores=0;
	protected static ControladorPartida partida;
	
	
	public static void main(String[] args) {
		
		ExecutorService pool=Executors.newFixedThreadPool(4);
		partida= new ControladorPartida(1,1);
		
		try (ServerSocket server=new ServerSocket(6666);){
			//while(true) {
				try {
					Socket cliente=server.accept();
					Hilo hilo=new Hilo(cliente,partida.players.get(0));
					hilo.start();
					
					/*partida.repartir();
					while(partida.getGameOver()==false){
						partida.jugada();
					}*/
					//pool.execute(new Hilo(cliente));		
					
					
					
					
				}catch(IOException e) {e.printStackTrace();}
			//}
		}catch(IOException e) {e.printStackTrace();}
		finally {
			pool.shutdown();
		}
	}
}
