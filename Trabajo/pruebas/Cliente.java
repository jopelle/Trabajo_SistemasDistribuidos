import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class Cliente {

	public static void main(String[] args) {
		try(Socket socket = new Socket("localhost", 6666);
			ObjectInputStream oos = new ObjectInputStream(socket.getInputStream());){
			Mesa m=new Mesa();
			
			
			m=(Mesa)oos.readObject();
			m.showMesa();
			
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
