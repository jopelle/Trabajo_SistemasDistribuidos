package servidor;

import java.net.Socket;

import cartas.*;

public class Hilo extends Thread{
	
	private Socket socket;
	private Humano jugador;
	
	public Hilo(Socket s) {
		this.socket=s;
		this.jugador=new Humano("Jon");
	}
	
	public void run() {
		//repetir hasta que se acebe la partida
			//hacer si es su turno
				//enviar al cliente sus cartas y el estado de la mesa
				//recibir del cliente un carta colocarla en la mesa
	}
	
	private void enviarMesa() {
		//Servidor.getPartida().
	}
	
	private void enviarCartas() {
		
	}
	
	private void colocarCarta() {
		
	}
	
	private void robar() {
		
	}
}
