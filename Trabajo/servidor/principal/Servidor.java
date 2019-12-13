package principal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Servidor {
	public static ControladorPartida partida;
	public static CountDownLatch count;
	public static CountDownLatch fin;
	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.println("Introduce el numero de jugadres (n<6): ");
		int n=in.nextInt();
		
		try (ServerSocket server=new ServerSocket(6666);){
			partida= new ControladorPartida();
			count=new CountDownLatch(n+1);
			fin=new CountDownLatch(n);
			
			SJugador j;
			
			for(int i=0;i<n;i++) {
				System.out.println("Esperando cliente (6666)");
				Socket cliente=server.accept();
				j=new SJugador("Jugador "+i);
				partida.añadirJugador(j);
				System.out.println("Se conecto un cliente");
				
				Hilo hilo=new Hilo(cliente,j,i);
				hilo.start();
			}
			
			partida.repartir();
			
			count.countDown();
			count.await();
			
			fin.await();
			
			in.close();
		}catch(IOException e) {e.printStackTrace();
		}catch(InterruptedException e) {e.printStackTrace();}
	}
}
