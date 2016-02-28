import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Response {
	
	Request req;
	
	String response;
	String root = "/Users/LeonidShashkov/Public/clipboard/HSE";
	
	public Response (Request request){
		req = request;
		//open the file 
		File f = new File(root + req.filename);
		
		if (req.StringOfParameters == ""){
			try {
				
				response = "HTTP/1.1 200";
				response += "Server: Our Java Server/1.0 \r\n";
				response += "Content-Type: text/html \r\n";
				response += "Connection close \r\n";
				long i = f.length();
				response += "Content-Length: " + i + " \r\n";
				response += "\r\n";
				
				//to read the file
				FileInputStream fis = new FileInputStream(f);
				int s;
				
				while ((s = fis.read()) != -1) {
					response += (char) s;
				}
				fis.close();
			} catch (FileNotFoundException e) {
				response = response.replace("200", "404");
			}
			catch (Exception e) {
				
			}
		}
		else {
			//найдем значение нужного нам параметра, распарсим XML и выведем в новую html страницу
			try {
				String params[] = req.StringOfParameters.split("&");
				String nameOfXMLParameter = "firstName";
				String XMLString;
//				if (params[0].substring(0,9) == nameOfXMLParameter){
					XMLString = params[0].substring(nameOfXMLParameter.length()+1, params[0].length());
						
					response = "HTTP/1.1 200";
					response += "Server: Our Java Server/1.0 \r\n";
					response += "Content-Type: text/html \r\n";
					response += "Connection close \r\n";
					long i = f.length();
					response += "Content-Length: " + i + " \r\n";
					response += "\r\n";
					
					//to read the file
					FileInputStream fis = new FileInputStream(f);
					int s;
					
					while ((s = fis.read()) != -1) {
						response += (char) s;
					}
					fis.close();
					
					//заменить в будущем строку на выводе, распарсив XML
					response = response.replace("#XMLAnswer", XMLString);
					
//				}
			} catch (FileNotFoundException e) {
				response = response.replace("200", "404");
			}
			catch (Exception e) {
				
			}
		}
	}
}
