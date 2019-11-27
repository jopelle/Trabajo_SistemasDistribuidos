package cartas;

import cartas.Palo;

public class Carta {
	private Palo Palo;
	private int valor;
	
	public Carta(Palo p,int v) {
		this.Palo=p;
		this.valor=v;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public Palo getPalo() {
		return this.Palo;
	}
	
	public String toString() {
		String v;
		if(this.valor==10) {
			v="R";
		}
		else if(this.valor==9) {
			v="C";
		}
		else if(this.valor==8) {
			v="S";
		}
		else {
			v=String.valueOf(this.valor);
		}
		return(v+" "+this.Palo);
	}
	
	public boolean equals(Object c) {
		if(c instanceof Carta) {
			Carta carta=(Carta) c;
			if(this.Palo==carta.getPalo() && this.valor==carta.getValor()){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
