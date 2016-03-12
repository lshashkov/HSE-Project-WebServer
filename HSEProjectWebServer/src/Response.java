import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Response {
	
	Request req;
	
	String response;
	String root = "/Users/LeonidShashkov/Public/clipboard/HSE";
	long i;
	
	public Response (Request request){
		
		req = request;
		
		if (!req.filename.equals("/CalculateExpression"))
		{
			//open the file 
			File f = new File(root + req.filename);
			
			try {
				
				response = "HTTP/1.1 200";
				response += "Server: Our Java Server/1.0 \r\n";
				response += "Content-Type: text/html \r\n";
				response += "Connection close \r\n";
				i = f.length();
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
			//open the file 
			File f = new File(root + "/answer.html");
			
			try {
				
				response = "HTTP/1.1 200";
				response += "Server: Our Java Server/1.0 \r\n";
				response += "Content-Type: text/html \r\n";
				response += "Connection close \r\n";
				i = f.length();
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

		if (req.StringOfParameters != "") {
			//найдем значение нужного нам параметра, распарсим XML и выведем в новую html страницу
			//+временно уберем, обрабатываем по-другому
//			String params[] = req.StringOfParameters.split("&");
//			String nameOfXMLParameter = "firstName";
//			String XMLString = "";
////				if (params[0].substring(0,9) == nameOfXMLParameter){
//				String XMLFileName = params[0].substring(nameOfXMLParameter.length()+1, params[0].length());
//				
//				try {
//					SAXParserFactory factory = SAXParserFactory.newInstance();
//					SAXParser parser = factory.newSAXParser();
//					TestSAXHandler handler = new TestSAXHandler();
//					XMLFileName = "/Users/LeonidShashkov/Public/clipboard/HSE/"+XMLFileName;
//					File input = new File(XMLFileName);
//					parser.parse(input, handler);
//					XMLString = handler.getResult();
////					System.out.println(handler.getResult());
//				} catch (Exception e) {
//				e.printStackTrace();
//				}
//				
//				//заменить в будущем строку на выводе, распарсив XML
//				response = response.replace("#XMLAnswer", XMLString);
//				String StringFromReplace = "Content-Length: " + i + " \r\n";
//				String StringToReplace = "Content-Length: " + Long.toString(i-10+XMLString.length()) + " \r\n";
//				response = response.replace(StringFromReplace,StringToReplace);
				//-временно уберем, обрабатываем по-другому
		}
		
		if (req.XMLString != "")
		{
			String Result = "";
			try {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser = factory.newSAXParser();
				TestSAXHandler handler = new TestSAXHandler();
				InputStream stream = new ByteArrayInputStream(req.XMLString.getBytes(StandardCharsets.UTF_8));
				parser.parse(stream, handler);
				Result = handler.getResult();
				
			} catch (Exception e) {
				e.printStackTrace();
				response = response.replace("200", "404");
			}
			//заменить в будущем строку на выводе, распарсив XML
			response = response.replace("#XMLAnswer", Result);
			String StringFromReplace = "Content-Length: " + i + " \r\n";
			String StringToReplace = "Content-Length: " + Long.toString(i-10+Result.length()) + " \r\n";
			response = response.replace(StringFromReplace,StringToReplace);
		}
	}
}
