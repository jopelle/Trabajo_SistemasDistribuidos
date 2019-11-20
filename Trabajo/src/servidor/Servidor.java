package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cartas.ControladorPartida;

public class Servidor{
	
	public static int njugadores=0;
	private static ControladorPartida partida= new ControladorPartida(1,1);
	
	
	public static void main(String[] args) {
		
		ExecutorService pool=Executors.newFixedThreadPool(4);
		ControladorPartida partida= new ControladorPartida(1,1);
		
		try (ServerSocket server=new ServerSocket(6666);){
			//while(true) {
				try {
					Socket cliente=server.accept();
					Hilo hilo=new Hilo(cliente);
					hilo.start();
					//pool.execute(new Hilo(cliente));		
					
					
					
					
				}catch(IOException e) {e.printStackTrace();}
			//}
		}catch(IOException e) {e.printStackTrace();}
		finally {
			pool.shutdown();
		}
	}
	
	static public ControladorPartida getPartida() {
		return partida;
	}	
}
