import java.awt.Dimension;
import java.awt.Panel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class Server extends JFrame {
	public Server() {
		super("MLG Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
/*		try {
			ImageIcon server = new ImageIcon(("icon.png"));
			setIconImage(server.getImage());
		} catch (NullPointerException e) {
		}*/
		Window window = new Window();
		setPreferredSize(new Dimension(400,500));
		add(window);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Server server = new Server();
				server.setVisible(true);
				server.pack();
				Dimension dimension = server.getToolkit().getScreenSize();
				server.setLocation(
					(int) (dimension.getWidth() / 8 - server.getWidth() / 8),
					(int) (dimension.getHeight() / 4 - server.getHeight() / 4));
			}
		});
	}
}