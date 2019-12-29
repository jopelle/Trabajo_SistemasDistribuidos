package servidor;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/*Representa la partida, formada por la mesa, la baraja y los jugadores,
 * y va recibiendo ,enviando , y colocando cartas segun sea necesario*/
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
	
	/*Espera a que se conecten el numero de clientes indicado,*/
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
			/*Hasta que no se conecten todos los cliente y sse repartn las cartas la partida no comienza*/
			partida.repartir();

			count.countDown();
			count.await();
			
			/*Cuando se acaba la partida se espera a que todos los hilos finalicen*/
			fin.await();
			
			interfaz.anadirLinea("SE ACABO");
			interfaz.anadirLinea("El ganador es "+partida.getJugador(partida.getTurno()).getNombre());
			
		}catch(IOException e) {e.printStackTrace();
		}catch(InterruptedException e) {e.printStackTrace();}
	}
	
	/*Muestra la mesa en la interfaz*/
	public static void showMesa() {
		interfaz.anadirLinea("Mesa: ");
		interfaz.anadirLinea(""+partida.getMesa().getOros());
		interfaz.anadirLinea(""+partida.getMesa().getBastos());
		interfaz.anadirLinea(""+partida.getMesa().getCopas());
		interfaz.anadirLinea(""+partida.getMesa().getEspadas());
		interfaz.anadirLinea("");
	}
}
