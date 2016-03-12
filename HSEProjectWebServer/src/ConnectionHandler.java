import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler extends Thread{
	
	Socket s;
	long pause;
	
	//for sending the output to client
	PrintWriter pw;
	
	//for getting the input from client
	BufferedReader br;
	
	//constructor
	public ConnectionHandler(Socket s, long pause) throws Exception{
		this.s = s;
		this.pause = pause;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		pw = new PrintWriter(s.getOutputStream());
	}
	
	//thread class contains a method run which is call automatically when we start the thread
	public void run() {
		try {
//			long pause = Math.round(Math.random()*2000);
            Thread.sleep(pause);
            System.out.println("Simple Thread - pause="+pause);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//here we get the request string and give this string to Request class
			String reqS = "";
			
			//from br we have to read our request
			while (br.ready() || reqS.length() == 0) {
				reqS += (char) br.read();
			}
			
			System.out.println(reqS);
			Request req = new Request(reqS);
			
			Response res = new Response(req);
			
			pw.write(res.response.toCharArray());
			pw.close();
			br.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
