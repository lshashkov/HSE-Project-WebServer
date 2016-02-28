
public class Request {
	
	String filename;
	String StringOfParameters;
	
	//we have to create a constructor that accept a string
	public Request (String request) {
		String lines[] = request.split("\n");
		String firstString[];
		//System.out.println(lines[0]);
		firstString = lines[0].split(" ");
		filename = firstString[1];
		
		//searching the parameter of a XML string
		if (lines.length == 12){
			StringOfParameters = lines[11];
		}
		else StringOfParameters = "";
	}
}
