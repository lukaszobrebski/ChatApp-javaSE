
public interface ISocketManager extends Runnable{

	
	public void sendMessage(String message);
	public String recieveMessage();
	public String getErrorMessage();	
}
