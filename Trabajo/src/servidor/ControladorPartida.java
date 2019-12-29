package servidor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cartas.*;

/*Controla los elementos básicos de la partida, crea el mazo reparte las cartas
 * roba cartas del mazo, las coloca en mesa etc*/
public class ControladorPartida {
	private Mesa mesa;
	private List<Jugador> players;
	private Baraja mazo;
	public int turno;
	private boolean gameOver;
	
	public ControladorPartida() {
		this.mesa=new Mesa();
		this.players=new ArrayList<Jugador>();
		this.mazo=new Baraja();
		this.turno=0;
		this.gameOver=false;
	}
	
	public int numeroJugadores() {
		return this.players.size();
	}
	public Jugador getJugador(int i) {
		return this.players.get(i);
	}	
	public void añadirJugador(Jugador j) {
		this.players.add(j);
	}
	
	public void repartir() {
		//Pre:
		//Post: Shuffles the deck, deals the cards among the players and assigns the initial turn.
		this.mazo.shuffle();
		Carta c;
		int p=this.players.size();
		for(int j=0;j<6;j++) {
			for(int k=0;k<p;k++) {
				c=this.mazo.drawCard();
				this.players.get(k).recibirCarta(c);
				if(c.equals(Baraja.cincoOro)) {
					/*Si la carta repartida es el cinco de oros, se le da al 
					atributo turno el indice del jugador (De la lista de jugadores)*/
					this.turno=k;
				}
			}
		}
	}
	public boolean getGameOver() {
		return this.gameOver;
	}
	public void setGameOver(boolean b) {
		this.gameOver=b;
	}

	public int getTurno() {
		return this.turno;
	}
	
	public Mesa getMesa() {
		return this.mesa;
	}
	public void colocarCarta(Carta c) {
		this.mesa.place(c);
	}
	
	public Carta robar() {
		return this.mazo.drawCard();
	}
	public boolean mazoVacio() {
		return this.mazo.isEmpty();
	}
	
	public void pasarTurno() {
		if(this.turno==this.numeroJugadores()-1) {
			//System.out.println("pasar turno 1");
			this.turno=0;
		}
		else {
			//System.out.println("pasar turno 0");
			this.turno++;
		}
	}
}
