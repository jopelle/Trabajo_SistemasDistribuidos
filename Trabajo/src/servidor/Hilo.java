package servidor;

import java.net.Socket;

import cartas.Jugador;

public class Hilo extends Thread{
	
	Socket socket;
	Jugador jugador;
	
	public Hilo(Socket s) {
		this.socket=s;
	}
	
	public void run() {
		//repetir hasta que se acebe la partida
			//hacer si es su turno
				//enviar al cliente sus cartas y el estado de la mesa
				//recibir del cliente un carta colocarla en la mesa
	}
	
	public void enviarMesa() {
		//Servidor.getPartida().
	}
	
	public void enviarCartas() {
		
	}
	
	private void colocarCarta() {
		
	}
	
	private void robar() {
		
	}
}
