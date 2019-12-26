package servidor;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Servidor {
	public static ControladorPartida partida;
	public static CountDownLatch count;
	public static CountDownLatch fin;
	
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		System.out.print("Introduce el numero de jugadres (n<6): ");
		int n=in.nextInt();
		System.out.println("");
		
		try (ServerSocket server=new ServerSocket(6666);){
			partida= new ControladorPartida();
			count=new CountDownLatch(n+1);
			fin=new CountDownLatch(n);
			
			Jugador j;
			
			for(int i=0;i<n;i++) {
				System.out.println("Esperando cliente (6666)");
				Socket cliente=server.accept();
				j=new Jugador();
				partida.añadirJugador(j);
				System.out.println("Se conecto un cliente");
				
				Hilo hilo=new Hilo(cliente,j,i);
				hilo.start();
			}
			
			partida.repartir();
			
			count.countDown();
			count.await();
			
			fin.await();
			
			System.out.println("SE ACABÓ");
			
			in.close();
		}catch(IOException e) {e.printStackTrace();
		}catch(InterruptedException e) {e.printStackTrace();}
	}
}
