package servidor;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Servidor extends Thread{
	protected static ControladorPartida partida;
	protected static CountDownLatch count;
	protected static CountDownLatch fin;
	protected static InterfazServidor interfaz;
	protected int nJugadores;
	
	public Servidor(int n,InterfazServidor i) {
		nJugadores=n;
		interfaz=i;
		partida= new ControladorPartida();
		count=new CountDownLatch(n+1);
		fin=new CountDownLatch(n);
	}
	
	public void run() {
		try (ServerSocket server=new ServerSocket(6666);){
			
			Jugador j;
			
			interfaz.anadirLinea("Esperando clientes (6666)");
			
			for(int i=0;i<nJugadores;i++) {
				Socket cliente=server.accept();
				j=new Jugador();
				partida.añadirJugador(j);
				interfaz.anadirLinea("Se conecto un cliente");
				
				Hilo hilo=new Hilo(cliente,j,i);
				hilo.start();
			}
			interfaz.anadirLinea("");
			
			partida.repartir();
			
			count.countDown();
			count.await();
			
			fin.await();
			
			interfaz.anadirLinea("SE ACABO");
			interfaz.anadirLinea("El ganador es "+partida.getJugador(partida.getTurno()).getNombre());
			
		}catch(IOException e) {e.printStackTrace();
		}catch(InterruptedException e) {e.printStackTrace();}
	}
	
	public static void showMesa() {
		interfaz.anadirLinea("Mesa: ");
		interfaz.anadirLinea(""+partida.getMesa().getOros());
		interfaz.anadirLinea(""+partida.getMesa().getBastos());
		interfaz.anadirLinea(""+partida.getMesa().getCopas());
		interfaz.anadirLinea(""+partida.getMesa().getEspadas());
		interfaz.anadirLinea("");
	}
}
