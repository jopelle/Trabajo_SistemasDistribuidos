import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.CardLayout;

public class framePrueba {

	private JFrame frame;
	private JTextField oros;
	private JTextField bastos;
	private JTextField espadas;
	private JTextField copas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					framePrueba window = new framePrueba();
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
	public framePrueba() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 847, 703);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel mesa = new JPanel();
		frame.getContentPane().add(mesa);
		mesa.setLayout(new GridLayout(4, 0, 0, 0));
		
		oros = new JTextField();
		oros.setText("[4 Oros][5 Oros][6 Oros]");
		oros.setHorizontalAlignment(SwingConstants.CENTER);
		mesa.add(oros);
		oros.setColumns(10);
		
		espadas = new JTextField();
		espadas.setHorizontalAlignment(SwingConstants.CENTER);
		mesa.add(espadas);
		espadas.setColumns(10);
		
		copas = new JTextField();
		copas.setHorizontalAlignment(SwingConstants.CENTER);
		mesa.add(copas);
		copas.setColumns(10);
		
		bastos = new JTextField();
		bastos.setHorizontalAlignment(SwingConstants.CENTER);
		mesa.add(bastos);
		bastos.setColumns(10);
		
		JPanel mano = new JPanel();
		frame.getContentPane().add(mano);
		mano.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnNewButton_6 = new JButton("New button");
		mano.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("New button");
		mano.add(btnNewButton_7);
		
		JButton btnNewButton_4 = new JButton("New button");
		mano.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("New button");
		mano.add(btnNewButton_5);
		
		JButton btnNewButton_3 = new JButton("New button");
		mano.add(btnNewButton_3);
		
		JButton btnNewButton_2 = new JButton("New button");
		mano.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("New button");
		mano.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("New button");
		mano.add(btnNewButton);
	}
}
