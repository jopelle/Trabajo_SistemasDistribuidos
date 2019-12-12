package principal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Servidor {
	public static ControladorPartida partida;
	public static Semaphore semaforo=new Semaphore(2);
	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.println("Introduce el numero de jugadres (n<6): ");
		int n=in.nextInt();
		int i=0;
		
		try (ServerSocket server=new ServerSocket(6666);){
			partida= new ControladorPartida();
			CountDownLatch count=new CountDownLatch(3);
			while(i<n) {
				System.out.println("Esperando cliente (6666)");
				Socket cliente=server.accept();
				partida.anadirJugador("Jugador "+i);
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
			in.close();
			
		}catch(IOException e) {e.printStackTrace();
		}catch(InterruptedException e) {e.printStackTrace();}
	}
}
