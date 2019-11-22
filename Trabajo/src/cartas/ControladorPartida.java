package cartas;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControladorPartida {
	private Mesa mesa;
	public List<Jugador> players;
	private Baraja mazo;
	private int turno;
	private boolean gameOver;
	
	public ControladorPartida(int h,int m) {
		mesa=new Mesa();
		players=new ArrayList<Jugador>();
		/*Scanner entrada=new Scanner (System.in);
		for(int i=1;i<=h;i++) {
			System.out.print("Nombre del jugador "+i+": ");
			players.add(new Humano(entrada.next()));
		}*/
		players.add(new Humano("Jon"));

		for(int j=1;j<=m;j++) {
			String n="Máquina"+j;
			players.add(new Maquina(n));
		}
		
		mazo=new Baraja();
		turno=0;
		gameOver=false;
		this.repartir();

		//this.jugada();
	}
	
	public void repartir() {
		//Pre:
		//Post: Shuffles the deck, deals the cards among the players and assigns the initial turn.
		this.mazo.barajar();
		Carta c;
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
				c=this.mazo.robar();
				this.players.get(k).recibirCarta(c);
				if(c.equals(Baraja.cincoOro)) {
					/*Si la carta repartida es el cinco de oros, se le da al 
					atributo turno el indice del jugador (De la lista de jugadores)*/
					this.turno=k;
				}
			}
		}
	}
	public void jugada() {
		Carta c;
		//Se asigna turno al jugador
		Jugador j=this.players.get(this.turno);
		j.turno=true;
		
		/*System.out.println("Turno del jugador "+j.getName());
		
		c=j.elegirCarta(this.mesa);
		if(c==null) {
			if(!this.mazo.mazoVacio()) {
				System.out.println("Roba");
				c=mazo.robar();
				j.recibirCarta(c);
				c=j.elegirCarta(this.mesa);
				if(c!=null) {
					System.out.println("Coloca "+c+"\n");
					this.mesa.place(c);
				}
			}
			else {
				System.out.println("Pasa\n");
			}
		}
		else {
			System.out.println("Coloca "+c+"\n");
			this.mesa.place(c);
		}*/
		/*if(j.cardsInHand()==0) {
			this.gameOver=true;
		}
		else {
			if(this.turno==this.players.size()-1) {
				this.turno=0;
			}
			else {
				this.turno++;
			}
			j.turno=false;
		}*/
	}
	public boolean getGameOver() {
		//Pre:
		//Post: Returns gameOver.
		return this.gameOver;
	}
	public void table() {
		//Pre:
		//Post: Shows the state of the table(mesa).
		this.mesa.showMesa();
	}
	public void winner() {
		//Pre:
		//Post: If the game is over shows the winner.
		if(this.gameOver==true) {
			System.out.println("\n Se acabo la partida \n");
			System.out.println("El ganador es: "+this.players.get(this.turno).getName()+"\n");
		}
	}
	public void rating() {
		//Pre:
		//Post: If the game is overs,shows the players in increasing order of value of the hand.
		List<Jugador> lista=new ArrayList<Jugador>();
		if(this.gameOver==true) {
			for(int i=0;i<this.players.size();i++) {
				if(i!=this.turno) {
					if(lista.size()==0) {
						lista.add(this.players.get(i));
					}
					else {
						if(this.players.get(i).getHandValue()<lista.get(0).getHandValue()) {
							lista.add(0,this.players.get(i));
						}
						else {
							int j=0;
							while(j<lista.size() && lista.get(j).getHandValue()<this.players.get(i).getHandValue()) {
								j++;
							}
							if(i==lista.size()) {
								lista.add(this.players.get(i));
							}
							else {
								lista.add(j,this.players.get(i));
							}
						}
					}
				}
			}
			System.out.println("Clasificacion:");
			for(int i=0;i<lista.size();i++) {
				System.out.println("Posicion "+(i+2));
				System.out.println(lista.get(i)+"\n");
			}
		}
		else {
			System.out.println("La partida aun no ha finalizado");
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
	
	public void colocarCarta(Carta c) {
		this.mesa.place(c);
	} 
}
