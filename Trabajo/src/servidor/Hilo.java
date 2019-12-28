package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cartas.Carta;
import cartas.Mesa;

public class Hilo extends Thread{
	
	private Socket socket;
	private Jugador jugador;
	private int turno;
	
	public Hilo(Socket s,Jugador j, int t) {
		this.socket=s;
		this.jugador=j;
		this.turno=t;
	}
	public void run() {
		try(ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());){

			String s;
			
			//Se da nombre al jugador
			this.jugador.setNombre((String)ois.readObject());
			
			//Se espera a que todos los jugadores se hayan conectado			
			Servidor.count.countDown();
			Servidor.count.await();
			
			//Se envian las cartas al cliente
			oos.writeObject(this.jugador.getMano());
			
			//Turno
			int ultimoTurno=Servidor.partida.getTurno();

			while(Servidor.partida.getGameOver()==false) {
				Thread.sleep(1);
				//Comprobar si el turno ha cambiado para actulizar la mesa
				if(ultimoTurno!=Servidor.partida.getTurno()) {
					if(ultimoTurno==Servidor.partida.numeroJugadores()-1) {
						ultimoTurno=0;
					}
					else {
						ultimoTurno++;
					}
					s="actualizar";
					oos.writeObject(s);
					s=Servidor.partida.getJugador(Servidor.partida.turno).getNombre();
					oos.writeObject(s);
					oos.writeObject(Servidor.partida.getMesa());
					oos.reset();
				}
				
				//Si es su turno
				if(this.turno==Servidor.partida.getTurno()) {
					Servidor.interfaz.anadirLinea("Turno: "+this.jugador.getNombre());

					//Envia la mesa
					s="mesa";
					oos.writeObject(s);
					Mesa m=Servidor.partida.getMesa();
					Servidor.showMesa();
					oos.writeObject(m);
					
					//Robar o colocar
					s=(String)ois.readObject();
					if(s.equals("robar")) {
						if(Servidor.partida.mazoVacio()) {
							s="vacio";
							oos.writeObject(s);
						}
						else {
							s="robando";
							oos.writeObject(s);
							Servidor.interfaz.anadirLinea("Robando:");
							Carta robada=Servidor.partida.robar();
							this.jugador.recibirCarta(robada);
							Servidor.interfaz.anadirLinea("Robando: "+robada+"\r\n");
							oos.writeObject(robada);
							
							s=(String)ois.readObject();
							if(!s.equals("pasar")) {
								this.colocar((Carta)ois.readObject());
							}
						}
					}
					else {
						this.colocar((Carta)ois.readObject());
					}
					
					//Se acaba la partida si no le quedan cartas al jugador
					//Se pasa el turno en caso contrario
					if(this.jugador.numeroCartas()==0) {
						Servidor.partida.setGameOver(true);
					}
					else {
						Servidor.partida.pasarTurno();
					}
					oos.reset();
				}
			}
			s="fin";
			oos.writeObject(s);
			s="El ganador es: "+Servidor.partida.getJugador(Servidor.partida.turno).getNombre();
			oos.writeObject(s);
			
			Servidor.fin.countDown();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void colocar(Carta c) {
		Servidor.partida.colocarCarta(c);
		this.jugador.eliminarCarta(c);
	}
}
