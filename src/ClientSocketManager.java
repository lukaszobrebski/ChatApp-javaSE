import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocketManager extends SocketManager{

	@Override
	public void run() {
		
		Boolean shouldChatThreadWork=true;;
	
		try {
			establishInputOutputStream();
		} catch (IOException e) {}
		
		
		while(shouldChatThreadWork)
		{
			 try {
				inputMessage = inputStream.readLine();
			} catch (IOException e) {
				errorMessage = "Nie udalo siê odczytac wiadomosci";
				inputMessage = null;
			}
			
			if(inputMessage != null)
			{
				isNewMessageWaitingForDisplay = true;
			}
		}
	}
	
	public ClientSocketManager(int port) throws IOException
	{
		try {
			connection = new Socket(InetAddress.getLocalHost().getHostName(), port);
	
		} catch (IOException e) {
			
			errorMessage = "Nie udalo sie otworzyc portu nr: " + port;
			throw e;
			}
	}
	
}
