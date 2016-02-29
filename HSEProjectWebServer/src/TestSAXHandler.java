import java.io.StringReader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class TestSAXHandler extends DefaultHandler {
 
private StringBuffer result;
private String resultString;
private String operation;
private String currentTag;
private int resultExpression = 0;
private String newExpression;
 
public TestSAXHandler(){
	result = new StringBuffer();
}
 
@Override
public void startElement(String namespaceURI, String localName,
		String qName, Attributes atts) throws SAXException {
 
	//имя тега
	//*example
//	result.append("Element name = '"+ qName+"'\n");
	//*example
	
	currentTag = qName;
	 
	//атрибуты тега
	for (int i = 0; i < atts.getLength(); i++){
		//*example
//		result.append("Attribute name = '" +
//				atts.getQName(i) + "'; Attribute value = '" + atts.getValue(i)+"'\n");
		//*example
		
		//Определим атрибут и его значение, поймем что за операция предстоит	
		if (atts.getQName(i) == "operation"){
			operation = atts.getValue(i);
		}
		else if (atts.getQName(i) == "name"){
			if (resultString != null)
				result.append("<div class=\"size\">Result of " + newExpression + ": " + resultString + "</div>");
			newExpression = atts.getValue(i);
			resultExpression = 0;
		}
	}
}
 
@Override
public void characters(char[] ch, int start, int length)
		throws SAXException {
	String value = "";
 
	//содержимое тега
	for (int i = start; i < length; i++){
	value += ch[i];
	}
	 
	//*example
//	if (value.length() != 0)
//		result.append("Element content = '" + value.trim() + "'\n");
	//*example
	
//	result.append(currentTag+resultExpression+operation);
	//если текущий тег - member, то запишем его в выражение
	if (currentTag == "Member" && value.length() != 0)
	{
		if(resultExpression == 0){
			resultExpression = Integer.parseInt(value.trim());
		}
		else
			//определим текущую операцию и выполним
			if(operation.equals("+"))
			{
				resultExpression = resultExpression + Integer.parseInt(value.trim());
			}
			else if (operation.equals("-")) {
				resultExpression = resultExpression - Integer.parseInt(value.trim());
			}
			else if (operation.equals("*")) {
				resultExpression = resultExpression * Integer.parseInt(value.trim());
			}
			else if (operation.equals("/")) {
				resultExpression = resultExpression / Integer.parseInt(value.trim());
			}
		resultString = Integer.toString(resultExpression);
		
		currentTag = null;
	}
}
	 
@Override
public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
	 
	//закрытие тега
	//*example
//	result.append("Element closed, name = '" + qName + "'\n");
	//*example
	}
	 
	public String getResult(){
		result.append("<div class=\"size\">Result of " + newExpression + ": " + resultString + "</div>");
		return result.toString();
	}
}