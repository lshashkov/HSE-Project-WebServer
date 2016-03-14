import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class TestSAXHandler extends DefaultHandler {
 
private StringBuffer result;
private String resultString;
private String operation;
private String subOperation = "";
private String currentTag;
private double resultExpression = 0;
private String newExpression;
private ArrayList<Double> resultsSubExpressions = new ArrayList<Double>();
 
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
		if (atts.getQName(i) == "operation" && currentTag == "Expression"){
			operation = atts.getValue(i);
		}
		else if (atts.getQName(i) == "operation" && currentTag == "SubExpression"){
			subOperation = atts.getValue(i);
		}
		else if (atts.getQName(i) == "name"){
//			if (resultString != null)
//				result.append("<div class=\"size\">Result of " + newExpression + ": " + resultString + "</div>");
			if (currentTag == "Expression")
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
	if (currentTag == "SubExpression") {
		resultsSubExpressions.add(null);
	}
	if (currentTag == "Member" && subOperation.length() == 0 && value.length() != 0)
	{
		if(resultExpression == 0){
			if (operation.equals("sin")) {
				resultExpression = Math.sin(Double.parseDouble(value.trim()));
			}
			else if (operation.equals("cos")) {
				resultExpression = Math.cos(Double.parseDouble(value.trim()));
			}
			else if (operation.equals("sqrt")) {
				if (Double.parseDouble(value.trim()) > 0) {
					resultExpression = Math.sqrt(Double.parseDouble(value.trim()));
				}
			}
			else
				resultExpression = Double.parseDouble(value.trim());
		}
		else
			//определим текущую операцию и выполним
			if(operation.equals("+"))
			{
				resultExpression = resultExpression + Double.parseDouble(value.trim());
			}
			else if (operation.equals("-")) {
				resultExpression = resultExpression - Double.parseDouble(value.trim());
			}
			else if (operation.equals("*")) {
				resultExpression = resultExpression * Double.parseDouble(value.trim());
			}
			else if (operation.equals("/")) {
				resultExpression = resultExpression / Double.parseDouble(value.trim());
			}
		resultString = Double.toString(resultExpression);
		
		currentTag = null;
	}
	else if (currentTag == "Member" && subOperation.length() != 0 && value.length() != 0)
	{
		if(resultsSubExpressions.get(resultsSubExpressions.size()-1) == null) {
			if (subOperation.equals("sin"))
				resultsSubExpressions.set(resultsSubExpressions.size()-1, Math.sin(Double.parseDouble(value.trim())));
			else if (subOperation.equals("cos"))
				resultsSubExpressions.set(resultsSubExpressions.size()-1, Math.cos(Double.parseDouble(value.trim())));
			else if (subOperation.equals("sqrt")) {
				if (Double.parseDouble(value.trim()) > 0) {
					resultsSubExpressions.set(resultsSubExpressions.size()-1, Math.sqrt(Double.parseDouble(value.trim())));
				}
			}
			else
				resultsSubExpressions.set(resultsSubExpressions.size()-1, Double.parseDouble(value.trim()));
		}
		else
		{
			//определим текущую операцию и выполним
			if(subOperation.equals("+"))
			{
				resultsSubExpressions.set(resultsSubExpressions.size()-1, resultsSubExpressions.get(resultsSubExpressions.size()-1) + Double.parseDouble(value.trim()));
			}
			else if (subOperation.equals("-")) {
				resultsSubExpressions.set(resultsSubExpressions.size()-1, resultsSubExpressions.get(resultsSubExpressions.size()-1) - Double.parseDouble(value.trim()));
			}
			else if (subOperation.equals("*")) {
				resultsSubExpressions.set(resultsSubExpressions.size()-1, resultsSubExpressions.get(resultsSubExpressions.size()-1) * Double.parseDouble(value.trim()));
			}
			else if (subOperation.equals("/")) {
				resultsSubExpressions.set(resultsSubExpressions.size()-1, resultsSubExpressions.get(resultsSubExpressions.size()-1) / Double.parseDouble(value.trim()));
			}
		currentTag = null;
		}
	}
}
	 
@Override
public void endElement(String namespaceURI, String localName, String qName)
		throws SAXException {
	 
		//закрытие тега
		//*example
	//	result.append("Element closed, name = '" + qName + "'\n");
		//*example
		if (qName == "Expression")
		{
			//посчитать результат перед закрытием
			if(resultsSubExpressions.size() > 0)
			{
				if (operation.equals("sin"))
					resultExpression = Math.sin(resultsSubExpressions.get(0));
				else if (operation.equals("cos"))
					resultExpression = Math.cos(resultsSubExpressions.get(0));
				else if (operation.equals("sqrt")) {
					if (resultsSubExpressions.get(0) > 0) {
						resultExpression = Math.sqrt(resultsSubExpressions.get(0));
					}
				}
				else
					resultExpression = resultsSubExpressions.get(0);
				for (int i = 1; i < resultsSubExpressions.size(); i++)
				{
					if (resultsSubExpressions.get(i) != null)
					{
						if(operation.equals("+")) {
							resultExpression = resultExpression + resultsSubExpressions.get(i);
						}
						else if (operation.equals("-")) {
							resultExpression = resultExpression - resultsSubExpressions.get(i);
						}
						else if (operation.equals("*")) {
							resultExpression = resultExpression * resultsSubExpressions.get(i);
						}
						else if (operation.equals("/")) {
							resultExpression = resultExpression / resultsSubExpressions.get(i);
						}
					}
				}
				resultString = Double.toString(resultExpression);
			}
			if (resultString != null)
				result.append("<div class=\"size\">Result of " + newExpression + ": " + resultString + "</div>");
			resultsSubExpressions.clear();
			operation = "";
		}
		else if (qName == "SubExpression") {
			subOperation = "";
		}
	}
	 
public String getResult(){
//		result.append("<div class=\"size\">Result of " + newExpression + ": " + resultString + "</div>");
		return result.toString();
	}
}
