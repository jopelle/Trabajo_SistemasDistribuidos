package principal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Servidor {
	public static ControladorPartida partida;
	public static Semaphore semaforo=new Semaphore(2);
	
	public static void main(String[] args) {
		//Socket cliente;
		int i=0;
		try (ServerSocket server=new ServerSocket(6666);){
			////pool de hilos
			partida= new ControladorPartida();
			CountDownLatch count=new CountDownLatch(3);
			//Semaphore semaforo=new Semaphore(1);
			System.out.println(partida.turno);
			while(i<2) {
				System.out.println("Esperando cliente (6666)");
				Socket cliente=server.accept();
				partida.anadirJugador("Jugador"+i);
				System.out.println("Se conecto un cliente");
				Hilo hilo=new Hilo(cliente,partida.getPlayer(i),count,i);
				hilo.start();
				i++;
			}
			partida.repartir();
			count.countDown();
			count.await();
			while(partida.getGameOver()==false) {}
			System.out.println("SE ACABO");
			//partida.show();
		}catch(IOException e) {e.printStackTrace();
		}catch(InterruptedException e) {e.printStackTrace();}
	}
}
