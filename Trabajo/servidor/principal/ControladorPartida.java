package principal;
import scartas.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControladorPartida {
	private SMesa mesa;
	public List<SJugador> players;
	private Baraja mazo;
	private int turno;
	private boolean gameOver;
	
	public ControladorPartida() {
		this.mesa=new SMesa();
		this.players=new ArrayList<SJugador>();
		this.mazo=new Baraja();
		this.turno=0;
		this.gameOver=false;
		this.repartir();
	}
	
	public void repartir() {
		//Pre:
		//Post: Shuffles the deck, deals the cards among the players and assigns the initial turn.
		this.mazo.shuffle();
		SCarta c;
		int i;
		int p=this.players.size();
		if(p<=4) {
			i=10;
		}
		else {
			i=40/p;
		}
		for(int j=0;j<i;j++) {
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
	public void jugada() {}
	
	public boolean getGameOver() {
		//Pre:
		//Post: Returns gameOver.
		return this.gameOver;
	}

	public void winner() {
		//Pre:
		//Post: If the game is over shows the winner.
		if(this.gameOver==true) {
			System.out.println("\n Se acabo la partida \n");
			System.out.println("El ganador es: "+this.players.get(this.turno).getName()+"\n");
		}
	}
	
	public String getStringMesa() {
		return this.mesa.toString();
	}
	
	public String getStringMazo() {
		return this.mazo.toString();
	}
	
	public String getStringMano() {
		return this.players.get(this.turno).handToString();
	}
	
	public void colocarCarta(SCarta c) {
		this.mesa.place(c);
	} 
}
