package principal;

import java.util.ArrayList;
import java.util.List;

import cartas.Carta;
import cartas.Palo;

public class pruebas {

	public static void main(String[] args) {
		String s="5 Oros";
		
		List<Carta> listaCartas=new ArrayList<>();
		
		String[]palos=s.split("-");
		
		for(int j=0;j<palos.length;j++) {

			palos[j]=palos[j].replace("[", "");
			palos[j]=palos[j].replace("]", "");
						
			if(!palos[j].equals("")) {
				System.out.println(palos[j]);

				String[]cartas=palos[j].split(",");
								
				String[]stringCarta;

				Carta carta;
				int valor;
				
				for(int i=0;i<cartas.length;i++) {

					if(i!=0){
						cartas[i]=cartas[i].replaceFirst(" ", "");
					}
						
					stringCarta=cartas[i].split(" ");
					
					if(stringCarta[0].equals("S")) {
						valor=8;
					}
					else if(stringCarta[0].equals("C")) {
						valor=9;
					}
					else if(stringCarta[0].equals("R")) {
						valor=10;
					}
					else {
						valor=Integer.valueOf(stringCarta[0]);
					}
					
					if(stringCarta[1].equals("Oros")) {
						carta=new Carta(Palo.Oros,valor);
					}
					else if(stringCarta[1].equals("Espadas")) {
						carta=new Carta(Palo.Espadas,valor);
					}
					else if(stringCarta[1].equals("Bastos")) {
						carta=new Carta(Palo.Bastos,valor);
					}
					else {
						carta=new Carta(Palo.Copas,valor);
					}
					listaCartas.add(carta);
				}
			}
		}
		System.out.println(listaCartas.get(0));
	}
}
