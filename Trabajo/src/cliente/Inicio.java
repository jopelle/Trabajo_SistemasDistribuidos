package cliente;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Inicio {

	private JFrame frame;
	private JTextField nombre;
	private JTextField servidor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio window = new Inicio();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Inicio() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 559, 402);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SUPER GENIAL JUEGO DE CARTAS");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 30));
		lblNewLabel.setBounds(12, 13, 541, 181);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Por Jon Pellejero Espinosa");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel_1.setBounds(12, 126, 258, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("ENTRAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verificarDatos();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 23));
		btnNewButton.setBounds(183, 153, 163, 75);
		frame.getContentPane().add(btnNewButton);
		
		nombre = new JTextField();
		nombre.setFont(new Font("Arial", Font.BOLD, 14));
		nombre.setForeground(Color.BLUE);
		nombre.setHorizontalAlignment(SwingConstants.CENTER);
		nombre.setBounds(183, 304, 163, 33);
		frame.getContentPane().add(nombre);
		nombre.setColumns(10);
		
		JLabel lblTuNombre = new JLabel("Tu nombre:");
		lblTuNombre.setBounds(89, 313, 84, 14);
		frame.getContentPane().add(lblTuNombre);
		
		JLabel lblServidor = new JLabel("Servidor:");
		lblServidor.setBounds(89, 268, 46, 14);
		frame.getContentPane().add(lblServidor);
		
		servidor = new JTextField();
		servidor.setHorizontalAlignment(SwingConstants.CENTER);
		servidor.setFont(new Font("Arial", Font.BOLD, 14));
		servidor.setForeground(Color.BLUE);
		servidor.setText("localhost");
		servidor.setBounds(183, 252, 163, 33);
		frame.getContentPane().add(servidor);
		servidor.setColumns(10);
	}
	
	public void verificarDatos() {
		if(nombre.getText()!=null && !nombre.getText().equals("")) {
			String s=servidor.getText();
			if(s.equals("localhost")) {
				mostrarMesa();
			}
			else {
				if(esIP(s)) {
					mostrarMesa();
				}
			}
		}
	}
	
	private boolean esIP(String ip) {
		try {
			if(ip==null) {
				return false;
			}
			
			String partes[]=ip.split("\\.");
			if(partes.length !=4) {
				return false;
			}
			
			for(String s:partes) {
				int i=Integer.parseInt(s);
				if((i<0)||(i>255)) {
					return false;
				}
			}
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}	
	}
	
	public void mostrarMesa() {
		InterfazJuego ic=new InterfazJuego(nombre.getText(),servidor.getText());
		frame.dispose();
	}
}