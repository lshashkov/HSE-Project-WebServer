import java.net.ServerSocket;
import java.net.Socket;

public class Main{
	
	ServerSocket serverSocket;
	
	public static void main (String[] args) throws Exception{
		new Main().runServer();
	}
	
	public void runServer() throws Exception{
		System.out.println("Server is started");
		serverSocket = new ServerSocket(6544);
		
		//for accepting requests
		acceptRequests();
	}
	
	private void acceptRequests() throws Exception{
		while (true) {
//			long timeStart = System.currentTimeMillis();
			Socket s = serverSocket.accept();
			ConnectionHandler ch = new ConnectionHandler(s, 0);
			ch.start();
			
//			if (ch.isAlive())
//			{
//				Socket s2 = serverSocket.accept();
//				ConnectionHandler ch2 = new ConnectionHandler(s2, 10000);
//				ch2.start();
//				
//				try {
//					ch2.join();
//				}
//				catch(InterruptedException e){}
//				
//			}
//			long timeEnd = System.currentTimeMillis();
//			long Time = timeEnd - timeStart;
//			System.out.println("Time = " + Time);
		}
	}

}