package principal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Servidor {
	public static ControladorPartida partida;
	public static Semaphore semaforo=new Semaphore(1);
	
	public static void main(String[] args) {
		//Socket cliente;
		int i=0;
		try (ServerSocket server=new ServerSocket(6666);){
			
			partida= new ControladorPartida();
			CountDownLatch count=new CountDownLatch(4);
			Semaphore semaforo=new Semaphore(1);
			
			System.out.println(partida.getTurno());
			
			while(i<4) {
				System.out.println("Esperando cliente (6666)");
				Socket cliente=server.accept();
				System.out.println("Se conecto un cliente");
				Hilo hilo=new Hilo(cliente,partida.players.get(i),count,i);
				i++;
				hilo.start();
			}
			count.await();
						
			while(partida.getGameOver()==false) {				

			}
			System.out.println(partida.getStringMazo());
			//partida.show();
		}catch(IOException e) {e.printStackTrace();
		}catch(InterruptedException e) {e.printStackTrace();}
	}
}
