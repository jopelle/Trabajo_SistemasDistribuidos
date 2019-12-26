package interfaz;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cartas.Carta;

public class framePrueba {

	private JFrame frame;
	private JTextField oros;
	private JTextField bastos;
	private JTextField espadas;
	private JTextField copas;
	private JTextField nombre;
	private JTextField turno;

	public framePrueba(String n,List<Carta> cartas) {
		initialize(cartas);
		frame.setVisible(true);
		nombre.setText(n);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(List<Carta> cartas) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 847, 703);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel mesa = new JPanel();
		frame.getContentPane().add(mesa);
		mesa.setLayout(new GridLayout(5, 0, 0, 0));
		
		JPanel panel = new JPanel();
		mesa.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		nombre = new JTextField();
		nombre.setHorizontalAlignment(SwingConstants.CENTER);
		nombre.setFont(new Font("Arial", Font.BOLD, 14));
		nombre.setEnabled(true);
		nombre.setEditable(false);
		nombre.setText("");
		panel.add(nombre);
		nombre.setColumns(10);
		
		turno = new JTextField();
		turno.setEditable(false);
		panel.add(turno);
		turno.setColumns(10);
		
		oros = new JTextField();
		oros.setEditable(false);
		oros.setText("[4 Oros][5 Oros][6 Oros]");
		oros.setHorizontalAlignment(SwingConstants.CENTER);
		mesa.add(oros);
		oros.setColumns(10);
		
		espadas = new JTextField();
		espadas.setEditable(false);
		espadas.setHorizontalAlignment(SwingConstants.CENTER);
		mesa.add(espadas);
		espadas.setColumns(10);
		
		copas = new JTextField();
		copas.setEditable(false);
		copas.setHorizontalAlignment(SwingConstants.CENTER);
		mesa.add(copas);
		copas.setColumns(10);
		
		bastos = new JTextField();
		bastos.setEditable(false);
		bastos.setHorizontalAlignment(SwingConstants.CENTER);
		mesa.add(bastos);
		bastos.setColumns(10);
		
		JPanel mano = new JPanel();
		frame.getContentPane().add(mano);
		mano.setLayout(new GridLayout(2, 0, 0, 0));
		
		for(Carta c: cartas) {
			JButton btnNewButton = new JButton(c.toString());
			mano.add(btnNewButton);
		}
		
	}
}
