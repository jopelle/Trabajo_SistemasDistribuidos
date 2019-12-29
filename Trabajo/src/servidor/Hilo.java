package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cartas.Carta;
import cartas.Mesa;

/*Se encarga de procesar la partida de un jugador y actualizar la interfaz del servidor*/
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
			
			/*Hasta que no se conecten todos los cliente y sse repartan las cartas la partida no comienza*/
			Servidor.count.countDown();
			Servidor.count.await();
			
			//Se envian las cartas al cliente
			oos.writeObject(this.jugador.getMano());
			
			//Se envia el truno inicial
			s="Empieza "+Servidor.partida.getJugador(Servidor.partida.turno).getNombre();
			oos.writeObject(s);
			
			/*Se guaarda el primer turno y cada vez que el turno avance,
			 * se envia la mesa al jugador para mantenerla actualizada a
			 * los movimientos de cada jugador*/
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
					//Se envia "actualizar" que indica que se va a enviar la mesa
					s="actualizar";
					oos.writeObject(s);
					//Se envia el nombre del jugador al que le toca jugar
					s=Servidor.partida.getJugador(Servidor.partida.turno).getNombre();
					oos.writeObject(s);
					//Se envia la mesa
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
					
					/*Recibe un string, si es robar, se roba una carta y envia al cliente,
					si es "colocar" recibe una carta y la coloca en la mesa*/
					s=(String)ois.readObject();
					if(s.equals("robar")) {
						/*Si elmazo esta vacio se envia "vacio", si no se elimina una carta del mazo
						 * y se envia "robando" y seguido la carta robada*/
						if(Servidor.partida.mazoVacio()) {
							s="vacio";
							oos.writeObject(s);
						}
						else {
							s="robando";
							oos.writeObject(s);
							Carta robada=Servidor.partida.robar();
							this.jugador.recibirCarta(robada);
							Servidor.interfaz.anadirLinea("Robando: "+robada+"\r\n");
							oos.writeObject(robada);
							
							/*Tras enviar la carta al cliente, se recibe un string , si es "pasar"
							 * significa que el cliente pasa turno (no puede colocar nada), si no es"pasar"
							 * se recibirá una carta que se colocará en la mesa*/
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

	/*Se coloca la carta en la mesa y se elimina del mano del jugador*/
	public void colocar(Carta c) {
		Servidor.partida.colocarCarta(c);
		this.jugador.eliminarCarta(c);
	}
}
