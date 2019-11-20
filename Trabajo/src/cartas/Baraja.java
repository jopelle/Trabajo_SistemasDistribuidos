package cartas;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Baraja {
	public List<Carta> mazo;
	public static final Carta cincoOro=new Carta(Palo.Oros,5);
	
	public Baraja() {
		this.mazo=new java.util.ArrayList<Carta>();
		for(int a=1;a<=10;a++) {
			Carta e=new Carta(Palo.Bastos,a);
			this.mazo.add(e);
			e=new Carta(Palo.Oros,a);
			this.mazo.add(e);
			e=new Carta(Palo.Espadas,a);
			this.mazo.add(e);
			e=new Carta(Palo.Copas,a);
			this.mazo.add(e);
		}
	}
	
	public boolean mazoVacio() {
		return this.mazo.isEmpty();
	}
	
	public void barajar() {
		Carta a,b;
		int i,n;
		for(i=0;i<40;i++) {
			n=ThreadLocalRandom.current().nextInt(40);
			a=this.mazo.get(i);
			b=this.mazo.get(n);
			this.mazo.set(n,a);
			this.mazo.set(i,b);
		}
		i=this.mazo.indexOf(cincoOro);
		n=ThreadLocalRandom.current().nextInt(20);
		a=this.mazo.get(n);
		this.mazo.set(n,cincoOro);
		this.mazo.set(i,a);
	}
	
	public Carta robar() {
		Carta c=this.mazo.get(0);
		this.mazo.remove(0);
		return c;
	}
}