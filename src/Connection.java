import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class Connection extends Thread {

	private ServerSocket serverSocket;
	private int port;
	private boolean online;
	
	/**
	 * Ustawia port i startuje watek
	 */
	public Connection(int p) {
		port = p;
		online = true;
		start();
	}
	

	/**
	 * zatrzymuje serwer i watek
	 */
	public void interrupt() {
		
		Window.notification("Zatrzymano serwer");
		try {
			serverSocket.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	
	}
	
	/**
	 * uruchamia serwer
	 */
	public void run() {
	
		try {	// tworzy gniazdko - jego utworzenie jest rownoznaczne z nawiazaniem polaczenia
			serverSocket = new ServerSocket(port);
			Window.notification("Serwer gotowy do pracy, port: " + port);
		} 
		catch (IOException e) {	// jesli port jest juz uzywany
			Window.notification("Port " + port + "jest juz uzywany, sprobuj wybrac inny");
			Window.window.setEnabled();
			return;
		} catch (IllegalArgumentException e) {	// gdy podano numer portu w zlym formanie, np: 3wafz3t2
			Window.notification("Podano zly numer portu");
			Window.window.setEnabled();
			return;
		}

		while (online) {
			try {
				new Data(serverSocket.accept());
			} catch (SocketException e) {
			} catch (IOException e) {
				Window.notification("Blad serwera");
			}
		}
		
	}
	
	
	
	
	
	}
