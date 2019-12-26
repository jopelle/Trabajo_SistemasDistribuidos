package cartas;

import java.util.concurrent.ThreadLocalRandom;

public class Baraja {
	public java.util.List<Carta> deck;
	public static final Carta cincoOro=new Carta(Palo.Oros,5);
	
	public Baraja() {
		//Pre: Constructor of Baraja. 
		//Post: Creates all of the cards(40) and adds them to the deck.
		this.deck=new java.util.ArrayList<Carta>();
		for(int a=1;a<=10;a++) {
			Carta e=new Carta(Palo.Bastos,a);
			this.deck.add(e);
			e=new Carta(Palo.Oros,a);
			this.deck.add(e);
			e=new Carta(Palo.Espadas,a);
			this.deck.add(e);
			e=new Carta(Palo.Copas,a);
			this.deck.add(e);
		}
	}
	public boolean isEmpty() {
		//Pre: 
		//Post: Returns true if the deck is empty.
		return this.deck.isEmpty();
	}
	public void shuffle() {
		//Pre:
		//Post: Randomly sorts the deck and makes sure that the "5 of golds" is among the first twenty cards. 
		Carta a,b;
		int i,n;
		for(i=0;i<40;i++) {
			n=ThreadLocalRandom.current().nextInt(40);
			a=this.deck.get(i);
			b=this.deck.get(n);
			this.deck.set(n,a);
			this.deck.set(i,b);
		}
		i=this.deck.indexOf(cincoOro);
		n=ThreadLocalRandom.current().nextInt(12);
		a=this.deck.get(n);
		this.deck.set(n,cincoOro);
		this.deck.set(i,a);
	}
	public Carta drawCard() {
		//Pre: 
		//Post: Returns the first card in the deck and removes it.
		Carta c=this.deck.get(0);
		this.deck.remove(0);
		return c;
	}
}