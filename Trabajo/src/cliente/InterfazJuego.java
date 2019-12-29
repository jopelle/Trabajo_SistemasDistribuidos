package cliente;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cartas.Carta;
import cartas.Mesa;

/*Se trata de una interfaz  que permite jugar la partida*/
public class InterfazJuego {

	private Cliente cliente;
	private JFrame frame;
	private JPanel mano;
	private JPanel mesa;
	private JTextField bastos;
	private JTextField copas;
	private JTextField espadas;
	private JTextField oros;
	private JTextField nombre;
	private JTextField mensaje;
	private Map<JButton,Carta> cartasEnMano;

	/**
	 * Create the application.
	 */
	/*Inicia la interfaz y crea el cliente con la ip dada*/
	public InterfazJuego(String n,String ip) {
		initialize();
		nombre.setText(n);
		mensaje.setText("Esperando al resto");
		cartasEnMano=new HashMap<>();
		cliente=new Cliente(this,ip);
		cliente.start();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 845, 614);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 0, 0, 0));
		
		mesa = new JPanel();
		frame.getContentPane().add(mesa);
		mesa.setLayout(new GridLayout(5, 0, 0, 0));
		
		JPanel info = new JPanel();
		mesa.add(info);
		info.setLayout(new GridLayout(0, 2, 0, 0));
		
		nombre = new JTextField();
		nombre.setHorizontalAlignment(SwingConstants.CENTER);
		nombre.setFont(new Font("Arial", Font.BOLD, 14));
		nombre.setEditable(false);
		info.add(nombre);
		nombre.setColumns(10);
		
		mensaje = new JTextField();
		mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		mensaje.setForeground(new Color(255, 0, 0));
		mensaje.setFont(new Font("Arial", Font.BOLD, 14));
		mensaje.setEditable(false);
		info.add(mensaje);
		mensaje.setColumns(10);
		
		bastos = new JTextField();
		bastos.setForeground(new Color(255, 255, 255));
		bastos.setEditable(false);
		bastos.setHorizontalAlignment(SwingConstants.CENTER);
		bastos.setFont(new Font("Arial", Font.BOLD, 15));
		bastos.setBackground(new Color(0, 100, 0));
		mesa.add(bastos);
		bastos.setColumns(10);
		
		copas = new JTextField();
		copas.setHorizontalAlignment(SwingConstants.CENTER);
		copas.setEditable(false);
		copas.setFont(new Font("Arial", Font.BOLD, 15));
		copas.setForeground(new Color(255, 255, 255));
		copas.setBackground(new Color(0, 100, 0));
		mesa.add(copas);
		copas.setColumns(10);
		
		espadas = new JTextField();
		espadas.setHorizontalAlignment(SwingConstants.CENTER);
		espadas.setEditable(false);
		espadas.setFont(new Font("Arial", Font.BOLD, 15));
		espadas.setForeground(new Color(255, 255, 255));
		espadas.setBackground(new Color(0, 100, 0));
		mesa.add(espadas);
		espadas.setColumns(10);
		
		oros = new JTextField();
		oros.setHorizontalAlignment(SwingConstants.CENTER);
		oros.setForeground(new Color(255, 255, 255));
		oros.setFont(new Font("Arial", Font.BOLD, 15));
		oros.setEditable(false);
		oros.setBackground(new Color(0, 100, 0));
		mesa.add(oros);
		oros.setColumns(10);
		
		mano = new JPanel();
		mano.setBackground(new Color(250, 250, 210));
		frame.getContentPane().add(mano);
		mano.setLayout(new GridLayout(2, 0, 0, 0));
	}
	
	/*Devuelve el nombre del jugador mostrado en la interfaz*/
	public String getNombre() {
		return nombre.getText();
	}
	
	/*Modifica el texto del cuadro de texto mensaje de la interfaz*/
	public void setMensaje(String s) {
		mensaje.setText(s);
	}
	
	/*Añade cartas a la interfaz que se reprsentan por botones (por defecto estan deshabilitados), 
	 * estos se asocian a las cartas con el Map cartasEnMano*/
	public void aniadirrCarta(Carta c) {
		JButton botonCarta = new JButton(c.toString());
		botonCarta.setEnabled(false);
		botonCarta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				elegirCarta((JButton)arg0.getSource());
			}
		});
		mano.add(botonCarta);
		cartasEnMano.put(botonCarta,c);
		mano.updateUI();
	}
	
	/*La accion producida al clickar en una carta, se modifica la carta a colocar en el cliente
	 * y se elimina la carta seleccionada de la interfaz */
	public void elegirCarta(JButton jb) {
		
		cliente.setParaColocar(cartasEnMano.get(jb));
		
		cartasEnMano.remove(jb);
		mano.remove(jb);
		mano.updateUI();
	}
	
	/*Dibuja la mesa recibida*/
	public void colocarMesa(Mesa m) {
		this.bastos.setText(m.getBastos());
		this.copas.setText(m.getCopas());
		this.espadas.setText(m.getEspadas());
		this.oros.setText(m.getOros());
		
	}
	
	/*Habilita un boton (Carta)*/
	public void colocable(Carta c) {
		for(JButton jb:cartasEnMano.keySet()) {
			if(cartasEnMano.get(jb).equals(c)) {
				jb.setEnabled(true);
			}
		}
	}
	
	/*Deshabilita todos los botones (Cartas)*/
	public void descolocables() {
		for(JButton jb:cartasEnMano.keySet()) {
			jb.setEnabled(false);
		}
	}
}
