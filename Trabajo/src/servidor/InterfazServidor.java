package servidor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import servidor.Servidor;

public class InterfazServidor {

	private JFrame frame;
	private JTextField textJugadores;
	private JTextArea txtaServidor;
	private Servidor servidor;
	private JButton botonIniciar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazServidor window = new InterfazServidor();
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
	public InterfazServidor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 673, 514);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel labelServidor = new JLabel("Servidor");
		labelServidor.setForeground(Color.RED);
		labelServidor.setFont(new Font("Arial", Font.BOLD, 34));
		labelServidor.setBounds(43, 12, 180, 129);
		frame.getContentPane().add(labelServidor);
		
		botonIniciar = new JButton("Iniciar");
		botonIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				iniciarServidor();
			}
		});
		botonIniciar.setBounds(474, 97, 89, 23);
		frame.getContentPane().add(botonIniciar);
		
		JButton botonDetener = new JButton("Detener");
		botonDetener.setBounds(520, 442, 89, 23);
		frame.getContentPane().add(botonDetener);
		
		textJugadores = new JTextField();
		textJugadores.setBounds(319, 98, 86, 20);
		frame.getContentPane().add(textJugadores);
		textJugadores.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("N\u00BA Jugadores (2-6):");
		lblNewLabel.setBounds(177, 101, 132, 14);
		frame.getContentPane().add(lblNewLabel);
		
		txtaServidor = new JTextArea();
		txtaServidor.setFont(new Font("Arial", Font.PLAIN, 11));
		txtaServidor.setForeground(Color.GREEN);
		txtaServidor.setBackground(Color.DARK_GRAY);
		txtaServidor.setBounds(88, 152, 475, 254);
		JScrollPane js= new JScrollPane(txtaServidor);
		js.setBounds(88, 152, 475, 254);
		frame.getContentPane().add(js);
	}
	
	private void iniciarServidor() {
		try {
			int n;
			n=Integer.parseInt(this.textJugadores.getText());
			if(n>=2 && n<=6) {
				this.botonIniciar.setVisible(false);
				this.textJugadores.setEditable(false);
				this.txtaServidor.append("Iniciando servidor para "+n+" jugadores\r\n");
				this.servidor= new Servidor(n,this);
				this.servidor.start();
			}
			else {
				this.txtaServidor.append("Nº de jugadores incorrecto\r\n");
			}
		}
		catch(NumberFormatException e) {
			this.txtaServidor.append("Nº de jugadores incorrecto\r\n");
		}	
	}
	
	public void anadirLinea(String s) {
		this.txtaServidor.append(s+"\r\n");
	}
}
