package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import scartas.SCarta;
import scartas.SPalo;

public class Hilo extends Thread{
	
	private Socket socket;
	private SJugador jugador;
	private CountDownLatch count;
	private int turno;
	
	public Hilo(Socket s,SJugador j, CountDownLatch c, int t) {
		this.socket=s;
		this.jugador=j;
		this.count=c;
		this.turno=t;
	}
	public void run() {
		try(BufferedWriter out= new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));){
			String stringCarta;
			SCarta cartaAColocar;
						
			//se envian las cartas al cliente
			out.write(this.jugador.handToString()+"\r\n");
			out.flush();
			System.out.println(this.turno);
			//SCarta c=this.traducirCarta(in.readLine());
			//System.out.println(c);
			//Servidor.partida.colocarCarta(c);
			this.count.countDown();
			this.count.await();
			
			while(Servidor.partida.getGameOver()==false) {
				if(this.turno==Servidor.partida.getTurno()) {
					Servidor.semaforo.acquire();
					
					System.out.println(this.jugador.getName());
					
					if(this.turno==Servidor.partida.players.size()-1) {
						Servidor.partida.turno=0;
					}
					else {
						Servidor.partida.turno++;
					}
					Servidor.semaforo.release();
				}
			}
			
			/*
			//repetir hasta que se acebe la partida
			while(true) {
				//hacer si es su turno
				if(Servidor.partida.getTurno()==this.turno) {
					//enviar al cliente el estado de la mesa
					out.write(Servidor.partida.getStringMesa()+"\r\n");
					out.flush();
					
					//recibir del cliente la carta a colocar
					stringCarta=in.readLine();
					if(stringCarta.equals("ROBAR")) {
						//robar carta del mazo
						//enviarla
						//recibir del cliente carta a colocar
						cartaAColocar=this.traducirCarta(in.readLine());
					}
					else {
						cartaAColocar=this.traducirCarta(in.readLine());
					}
					//colocar la carta en la mesa del servidor
					Servidor.partida.colocarCarta(cartaAColocar);
				}
			}*/
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setCountDown(CountDownLatch c) {
		this.count=c;
	}
	
	private SCarta traducirCarta(String cadena){
		
		SCarta sCarta;
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
			sCarta=new SCarta(SPalo.Oros,valor);
		}
		else if(stringCarta[1].equals("Espadas")) {
			sCarta=new SCarta(SPalo.Espadas,valor);
		}
		else if(stringCarta[1].equals("Bastos")) {
			sCarta=new SCarta(SPalo.Bastos,valor);
		}
		else {
			sCarta=new SCarta(SPalo.Copas,valor);
		}
		
		return sCarta;
	}
	
	public void esperar() {
		
	}
	
	public void jugar() {
		
	}
}
