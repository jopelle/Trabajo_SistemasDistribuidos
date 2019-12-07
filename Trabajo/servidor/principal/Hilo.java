package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

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
			System.out.println(this.jugador.handToString());

			System.out.println(this.turno);
			//SCarta c=this.traducirCarta(in.readLine());
			//System.out.println(c);
			//Servidor.partida.colocarCarta(c);
			this.count.countDown();
			//this.count.await();
			
			while(Servidor.partida.getGameOver()==false) {
				if(this.turno==Servidor.partida.getTurno()) {
					
					System.out.println(this.jugador.getName());

					//Envia la mesa
					System.out.println(Servidor.partida.getStringMesa());
					out.write(Servidor.partida.getStringMesa()+"\r\n");
					out.flush();
					
					//Robar o coloca
					String s;
					do{s=in.readLine();} while(s==null);
					System.out.println(s);
					if(s.equals("robar")) {
						if(Servidor.partida.mazoVacio()) {
							out.write("vacio\r\n");
							out.flush();
						}
						else {
							System.out.println("robando");
							SCarta robada=Servidor.partida.robar();
							this.jugador.recibirCarta(robada);
							System.out.println(robada);
							out.write(robada.toString()+"\r\n");
							out.flush();
							
							s=in.readLine();
							if(!s.equals("pasar")) {
								this.colocar(s);
							}
						}
					}
					else {
						this.colocar(s);
					}
					
					//Se acaba la partida si no le quedan cartas al jugador
					//Se pasa el turno en caso contrario
					if(this.jugador.cardsInHand()==0) {
						Servidor.partida.setGameOver(true);
					}
					else if(this.turno==Servidor.partida.players.size()-1) {
						Servidor.partida.turno=0;
					}
					else {
						Servidor.partida.turno++;
					}
					Servidor.partida.getMesa().showMesa();
				}
			}
			out.write("fin\r\n");
			out.flush();
			out.write("El ganador es: "+Servidor.partida.players.get(Servidor.partida.turno).getName()+"\r\n");	
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} /*catch (InterruptedException e) {
			e.printStackTrace();
		}*/
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
	
	public void colocar(String s) {
		SCarta c=this.traducirCarta(s);
		Servidor.partida.colocarCarta(c);
		this.jugador.eliminarCarta(c);
	}
}
