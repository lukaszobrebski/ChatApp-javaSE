import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerSocketManager extends SocketManager{

	private ServerSocket socket;
	private InetAddress clientAddress;
	
	@Override
	public void run() {
		
		Boolean shouldChatThreadWork=true;;
		
		//Nawi¹¿ po³¹czenie
		try {
			//findClient(); -------------TO DO
			establishInputOutputStream();
			
		} catch (IOException e) {e.printStackTrace();}

		
		//Obs³uguj czat
		while(shouldChatThreadWork)
		{
			try {
				inputMessage = inputStream.readLine();
				
				if(!inputMessage.isEmpty())
				{
					isNewMessageWaitingForDisplay = true;
				}
				
			} catch (IOException e) {
				errorMessage = "Nie udalo siê odczytac wiadomosci";
				inputStream = null;
			}	
		}	
	}
	
	
	/*
	 * IN PROGRESS
	 * TO DO:
	 * Zmieniæ serwer na nas³uch i dostosowaæ klienta
	 * 
	 * 
	private void findClient() throws IOException
	{
		Boolean isClientFound = false;
		while(!isClientFound)
		{
			byte[] streamBuffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(streamBuffer, streamBuffer.length);
	        
			try {
				UDPconnection.receive(packet);											//Pobierz dane z sieci
				String Message = new String(streamBuffer);
				
				if(Message == "MESSANGER:CONNECT")										//Jesli to klient czatu, to akceptuj
				{
					Message = "MESSANGER:ACCEPT";										
					streamBuffer = Message.getBytes();
					clientAddress = packet.getAddress();
		            packet = new DatagramPacket(streamBuffer, streamBuffer.length, clientAddress, port);
		            UDPconnection.send(packet);
		            
				}else continue;
				
				UDPconnection.receive(packet);											//Jesli do³¹cza to nas³uchuj
				Message = new String(streamBuffer);
				
				if(Message == "MESSANGER:ACK")
				{
					isClientFound = true;
					acceptConnection(clientAddress);
					
				}else continue;
				
			} catch (IOException e) {
				errorMessage = "B³¹d odczytu protoko³u UDP";
				throw e;
			}
		}
		
		
	}
	*/
	
	private void acceptConnection(InetAddress address) throws IOException
	{
		try {
			socket = new ServerSocket(port, 100, address);
			connection = socket.accept();
			
		} catch (IOException e) {
			errorMessage = "Nie znaleziono nikogo do rozmowy";
			throw e;
			}
	}
		
	public ServerSocketManager(int port) throws IOException
	{
		try {
			this.port = port;
			socket = new ServerSocket(port);
			
		} catch (IOException e) {
			
			errorMessage = "Nie udalo sie otworzyc portu nr: " + port;
			throw e;
			}
	}
}
