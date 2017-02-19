import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.Socket;

public abstract class SocketManager implements ISocketManager {

	protected DatagramSocket UDPconnection;
	protected Socket connection;
	protected BufferedReader inputStream;
	protected PrintWriter outputStream;
	protected volatile String inputMessage, outputMessage, errorMessage;
	protected volatile Boolean isNewMessageWaitingForDisplay = true;
	protected Integer port=null;
	
	
	
	@Override
	public void run(){/*TO OVERRIDE*/}
	
	protected void establishInputOutputStream() throws IOException
	{
		try {
			inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			outputStream = new PrintWriter(connection.getOutputStream(), true);
		} catch (IOException e) {
			errorMessage = "Nie uda³o siê utworzyæ strumienia danych";
			throw e;
		}
	}
	
	@Override
	public synchronized void sendMessage(String message) {
		
		outputMessage = message;
		outputStream.println(outputMessage);
		
	}

	@Override
	public synchronized String recieveMessage() {
		
		if(isNewMessageWaitingForDisplay)
		{
			isNewMessageWaitingForDisplay = false;
			return inputMessage;
		}
		else return null;
	}

	@Override
	public String getErrorMessage() {
		
		return errorMessage;
	}

}
