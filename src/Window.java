import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.net.InetAddress;
import java.net.UnknownHostException;



public class Window extends JPanel{
	
	public static Window window;
	public static String nr_portu="1000";
	private static JPanel info;	// nie static
	private JButton start;
	private JTextField portText;
	
	
	
	private JScrollPane scroll;
	
	
	private boolean online=false;
	private Connection connection;
	JButton exit;
	JLabel ip;
	JLabel portInfo;
	
	

	/**
	 * ustawiama pola tekstowe i przyciski
	 */
	public Window() {
		
		window = this;
		info = new JPanel();
		
		JPanel button = new JPanel();
		JPanel north = new JPanel();
		JPanel south = new JPanel();
		JPanel port = new JPanel();
		
		start = new JButton("Uruchom serwer");
		exit = new JButton("Zamknij");
		portInfo = new JLabel("Port"  + ": ");
		
		try {
			ip = new JLabel("IP serwera:   " 
			+ InetAddress.getLocalHost().getHostAddress());
		}
		catch (UnknownHostException e){
			notification("Nie mozna pobrac adresu IP serwera");
		}
		
		
		portText = new JTextField(nr_portu);
		scroll = new JScrollPane(info);
		
		setLayout(new BorderLayout());
		south.setLayout(new BorderLayout());
		north.setLayout(new BorderLayout());
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		//infoText.setLayout(new BoxLayout(_info, BoxLayout.Y_AXIS));
		
		ip.setForeground(new Color(0x00000));
		portInfo.setForeground(new Color(0x000000));
		
		
		start.setPreferredSize(new Dimension(200,25));
		exit.setPreferredSize(new Dimension(80,25));
		
		portText.setPreferredSize(new Dimension(0, 25));
		portText.setColumns(7);
		portText.setSize(new Dimension(0, 25));
		
		button.add(exit);
		button.add(start);
		port.add(portInfo);
		port.add(portText);
		
		south.add(button, BorderLayout.EAST);
		north.add(ip, BorderLayout.WEST);
		north.add(port, BorderLayout.EAST);
		add(scroll, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		add(north, BorderLayout.NORTH);
		
		/**
		 * wyjscie z programu
		 */
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
		
		/**
		 * reaguje na klikanie przycisku uruchom/zatrzymaj
		 */
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				if (online) {
					connection.interrupt();
					setEnabled();
				} else {
					try {
						int port = Integer.parseInt(portText.getText());
						start.setText("Zatrzymaj");
						portText.setEnabled(false);
						online = true;
						try {
							connection = new Connection(port);
						} catch (Exception e) {
						}
					} catch (NumberFormatException e) {
						notification("zly numer portu");
					}
				}
			}
		});
		
		/**
		 * ? kontroluje czy trzeba scrolowac
		 */
		info.addContainerListener(new ContainerListener() {
			public void componentAdded(ContainerEvent e) {
				scroll.getVerticalScrollBar().setValue(
						scroll.getVerticalScrollBar().getMaximum());
			}

			public void componentRemoved(ContainerEvent e) {
			}
		});
		
		// komunikat powitalny po uruchomieniu
		notification("Serwer Cie wita, drogi u¿tykowniku");
	}

	/**
	 * wypisuje tekst na ekranie
	 */
	public static void notification(String tekst) {
		JLabel label;
		label = new JLabel(tekst);
		
		label.setFont(label.getFont().deriveFont((float)12));
		label.setForeground(new Color(0x0000FF));
		info.add(label);
		info.revalidate();
		}

	/**
	 * Po zakonczeniu pracy serwera ustawia napis przycisku z zatrzymaj na uruchom  
	 * i zmienia status online na false
	 */
	public void setEnabled() {
		start.setText("Uruchom serwer");
		portText.setEnabled(true);
		online = false;
	}
	
	
	
	
	
}
