import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

	public static void main(String[] args) {
		try(ServerSocket server=new ServerSocket(6666);
				ObjectOutputStream oos = new ObjectOutputStream(server.accept().getOutputStream());){
				
				List<Carta> c=new ArrayList<>();
				Carta c1=new Carta(Palo.Oros,5);
				Carta c2=new Carta(Palo.Bastos,5);
				Carta c3=new Carta(Palo.Copas,5);
				Carta c4=new Carta(Palo.Espadas,5);
				
				Mesa m=new Mesa();
				
				m.place(c1);
				m.place(c2);
				m.place(c3);
				m.place(c4);
						
				c.add(c1);
				c.add(c2);
				c.add(c3);
				c.add(c4);
				
				String s="Hola";
				
				oos.writeObject(m);
				
				
			}catch(IOException e) {
				e.printStackTrace();
			}

	}

}
