import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;




public class Data extends Thread{
	
	private Socket socket;
	private BufferedReader buffered_reader;
	private PrintWriter print_writer;
	
	Data(Socket s) throws IOException  {
		
		socket = s;
		/**
		 * tworzy strumien wejsciowy
		 */
		InputStream input_stream = socket.getInputStream();
		buffered_reader = new BufferedReader(new InputStreamReader(input_stream));
		
		/**
		 * tworzy strumien wyjsciowy
		 */
		OutputStream output_stream = socket.getOutputStream();
		print_writer = new PrintWriter(output_stream, true);
		
		start();
	}
	
	public void run(){
		
		String command;	// to bedzie komenda, zapytanie przesylane przez siec
		try {
			// odczytaj komende ze strumienia wejsciowego
			command = buffered_reader.readLine();
			// wypisz odczytana komende na konsole
			Window.notification("Od " + socket.getInetAddress().getHostAddress() 
					+ ": " + command);
		

		
		// jak zachowa sie serwer w zaleznosci od otrzymanej komendy
		switch (command) {
			
		case "get_properties": {
			// otwieram plik, dostajemy strumien bajtow
			File file = new File("mlg_server.properties");
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close(); // zamykamy plik
		
			// bajty konwertuje na string i wysylam
			String str = new String(data, "UTF-8");
			print_writer.println(str); // wysylam tekst na strumien wyjsciowy
			
			
			Window.notification("wyslalem ustawienia konfiguracyjne "
					+ socket.getInetAddress().getHostAddress() + ": ");
			socket.close();	// zamykam gniazdko
			break;
			
		}
		
		// testowa komenda
		case "test_command": {
			
			String answer = "to jest odpowiedz serwera na test ";
			
			print_writer.println(answer);	// wyrzucam na output stream 
			System.out.println(answer);		// dla pewnosci wypisuje to samo
				
			// teraz infromuje w konsoli co sie stalo
			Window.notification("Odpowiedzialem na testowa komende "
					+ socket.getInetAddress().getHostAddress() + ": ");
			socket.close();
			break;
		}
			
		
		
			
		}	// koniec switch
		
		} catch (IOException e) {
			Window.notification("Wystapil blad przy pobieraniu danych");
		}
		
		
	}
	
	
	
	
	
}
