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

public class Inicio {

	private JFrame frame;
	private JTextField nombre;

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
				if(nombre.getText()!=null && !nombre.getText().equals("")) {
					mostrarMesa();
				}
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 23));
		btnNewButton.setBounds(183, 173, 163, 75);
		frame.getContentPane().add(btnNewButton);
		
		nombre = new JTextField();
		nombre.setBounds(183, 284, 163, 33);
		frame.getContentPane().add(nombre);
		nombre.setColumns(10);
		
		JLabel lblTuNombre = new JLabel("Tu nombre:");
		lblTuNombre.setBounds(89, 293, 84, 14);
		frame.getContentPane().add(lblTuNombre);
	}
	
	public void mostrarMesa() {
		InterfazJuego ic=new InterfazJuego(nombre.getText());
		frame.dispose();
	}
}