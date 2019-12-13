package principal;
import scartas.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cartas.Carta;

public class ControladorPartida {
	private SMesa mesa;
	private List<SJugador> players;
	private Baraja mazo;
	public int turno;
	private boolean gameOver;
	
	public ControladorPartida() {
		this.mesa=new SMesa();
		this.players=new ArrayList<SJugador>();
		this.mazo=new Baraja();
		this.turno=0;
		this.gameOver=false;
	}
	
	public int numeroJugadores() {
		return this.players.size();
	}
	public SJugador getJugador(int i) {
		return this.players.get(i);
	}	
	public void añadirJugador(SJugador j) {
		this.players.add(j);
	}
	
	public void repartir() {
		//Pre:
		//Post: Shuffles the deck, deals the cards among the players and assigns the initial turn.
		this.mazo.shuffle();
		SCarta c;
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
	
	public void mostrarMesa() {
		this.mesa.showMesa();
	}
	public String getStringMesa() {
		return this.mesa.toString();
	}
	public void colocarCarta(SCarta c) {
		this.mesa.place(c);
	}
	
	public SCarta robar() {
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
