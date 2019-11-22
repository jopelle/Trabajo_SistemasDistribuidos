package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import cartas.Humano;
import cartas.Jugador;
import cartas.Palo;
import cartas.Carta;

public class Hilo extends Thread{
	
	private Socket socket;
	private Humano jugador;
	
	public Hilo(Socket s,Jugador j) {
		this.socket=s;
		this.jugador=(Humano)j;
		this.jugador.turno=true;
	}
	public void run() {
		try(BufferedWriter out= new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));){
			Carta cartaAColocar;
			
			//se envian las cartas al cliente
			out.write(this.jugador.handToString()+"\r\n");
			out.flush();
			
			//repetir hasta que se acebe la partida
			while(true) {
				//hacer si es su turno
				if(this.jugador.turno) {
					//enviar al cliente el estado de la mesa
					out.write(Servidor.partida.getStringMesa()+"\r\n");
					out.flush();
					//recibir del cliente la carta a colocar
					cartaAColocar=this.traducirCarta(in.readLine());
					
					//colocar la carta en la mesa del servidor
					Servidor.partida.colocarCarta(cartaAColocar);

					//eliminar la carta de la mano del jugador
					this.jugador.eliminarCartaMano(cartaAColocar);

					//pasar turno
					this.jugador.turno=false;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Carta traducirCarta(String cadena){
		
		Carta carta;
		String[]stringCarta;
		int valor;
		
		stringCarta=cadena.split(" ");
		
		if(stringCarta[0].equals("S")) {
			valor=8;
		}
		else if(stringCarta[0].equals("C")) {
			valor=9;
		}
		else if(stringCarta[0].equals("R")) {
			valor=10;
		}
		else {
			valor=Integer.valueOf(stringCarta[0]);
		}
		
		
		if(stringCarta[1].equals("Oros")) {
			carta=new Carta(Palo.Oros,valor);
		}
		else if(stringCarta[1].equals("Espadas")) {
			carta=new Carta(Palo.Espadas,valor);
		}
		else if(stringCarta[1].equals("Bastos")) {
			carta=new Carta(Palo.Bastos,valor);
		}
		else {
			carta=new Carta(Palo.Copas,valor);
		}
		
		return carta;
	}

}
