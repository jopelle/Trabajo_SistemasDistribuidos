package cartas;
import cartas.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Humano extends Jugador{
	 public Humano(String n) {
		 super(n);
	 }
	public Carta chooseCard(Mesa m) {
		//Pre: Receive a Mesa.
		//Post: Returns a placeable card (Chosen by the player) from the hand and remove it. 
		Carta c;
		int n,h;
		h=this.cardsInHand();
		java.util.List<Carta> aColocar=new ArrayList<>();
		if(this.hand.contains(Baraja.cincoOro)) {
			System.out.println("El jugador "+this.name+" empieza con el "+Baraja.cincoOro);
			this.hand.remove(Baraja.cincoOro);
			this.handValue=this.handValue-5;
			return Baraja.cincoOro;
		}
		else {
			for(int i=0;i<h;i++) {
				c=this.hand.get(i);
				if(m.placeable(c)) {
					aColocar.add(c);
				}
			}
			n=aColocar.size();
			System.out.println("Mano"+this.hand);
			if(n==0) {
				System.out.println("\nNo puedes colocar ninguna carta");
				return null;
			}
			else {
				int j,k;
				String s="";
				Scanner entrada=new Scanner (System.in);
				System.out.println("\nPuedes colocar: ");
				for(j=0;j<n;j++) {
					s=s+aColocar.get(j)+"("+j+") ";
				}
				do {
					System.out.println(s);
					System.out.print("Carta a colocar: ");
					k=entrada.nextInt();
				}while(k>=aColocar.size() || k<0);
				c=aColocar.get(k);
				this.hand.remove(c);
				this.handValue=this.handValue-c.getValor();
				return c;
			}
		}
	}

	public void receive(Carta c) {
		//Pre: Receive a Carta.
		//Post: Adds the given card to the player's hand. 
		this.hand.add(c);
		this.handValue=this.handValue+c.getValor();
	}
}
