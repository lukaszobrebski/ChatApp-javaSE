import java.io.*;
import java.net.*;

public class ServerSocketManager extends SocketManager{

	private ServerSocket socket;
		
	@Override
	public void run() {
		
		Boolean shouldChatThreadWork=true;;
		
		try {
			acceptConnection();
		} catch (IOException e) {}
		
		try {
			establishInputOutputStream();
		} catch (IOException e) {}
		
		while(shouldChatThreadWork)
		{
			try {
				inputMessage = inputStream.readLine();
			} catch (IOException e) {
				errorMessage = "Nie udalo siê odczytac wiadomosci";
				inputStream = null;
				System.out.println("DUPA");
			}
			
			if(!inputMessage.isEmpty())
			{
				System.out.println("Serwer: Nowa wiadomosc: " + inputMessage + "\n");
				isNewMessageWaitingForDisplay = true;
			}
		}	
	}
	
	private void acceptConnection() throws IOException
	{
		try {
			connection = socket.accept();
			
		} catch (IOException e) {
			errorMessage = "Nie znaleziono nikogo do rozmowy";
			throw e;
			}
	}
		
	public ServerSocketManager(int port) throws IOException
	{
		try {
			socket = new ServerSocket(port);
			
		} catch (IOException e) {
			
			errorMessage = "Nie udalo sie otworzyc portu nr: " + port;
			throw e;
			}
	}
}
