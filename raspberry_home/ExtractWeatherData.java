import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.*;

public class ExtractWeatherData{

  public void getWeatherValues(ArrayList<String> tabularValues,ArrayList<String> time,ArrayList<String> periodValues,ArrayList<String> symbols){
      //3 periodes of the first day and 4 colums
      final int valuesIterations = 32;
      Document doc = getDocument();
      doc.getDocumentElement().normalize();
      //System.out.println("root: " + doc.getDocumentElement().getNodeName());
      Node tabular = doc.getElementsByTagName("tabular").item(0);
      NodeList weekCast = tabular.getChildNodes();

      for (int i = 0;i < valuesIterations;i++) {
        Node node = weekCast.item(i);
        if(node.getNodeType() == Node.ELEMENT_NODE){
          Element temp = (Element) node;

          NodeList tempNodes = temp.getElementsByTagName("temperature");
          //NodeList windNodes = temp.getElementsByTagName("windSpeed");
          NodeList symbolNodes = temp.getElementsByTagName("symbol");

          Element tempValue = (Element) tempNodes.item(0);
          Element symbolValue = (Element) symbolNodes.item(0);
          //Element windValue = (Element) windNodes.item(0);
          //String wind = windValue.getAttribute("mps") + "m/s";
          //wind += ", " + windValue.getAttribute("name");

          periodValues.add(temp.getAttribute("period"));
          tabularValues.add(tempValue.getAttribute("value"));
          symbols.add(symbolValue.getAttribute("number"));
          //tabularValues.add(wind);

          //extract only the H in hour where in yr.no xml file is described as "2017-11-13T14:00:00"
          String to = temp.getAttribute("to");
          String from = temp.getAttribute("from");
          Pattern p = Pattern.compile("T(.*?):");
          Matcher toMatch = p.matcher(to);
          Matcher fromMatch = p.matcher(from);
          toMatch.find();
          fromMatch.find();
          String fromTo = "kl. " + fromMatch.group(1) + "-" +  toMatch.group(1);
          time.add(fromTo);

        }
      }
}

  public Document getDocument(){
    try{
      URL yrWeatherXml = new URL("https://www.yr.no/sted/Norge/Oslo/Oslo/Oslo/varsel.xml");
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      dbFactory.setIgnoringComments(true);
      dbFactory.setIgnoringElementContentWhitespace(true);
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      return dBuilder.parse(yrWeatherXml.openStream());
    }catch(Exception e){ //e.printStackTrace();
    System.out.println(e.getMessage());
    }
    return null;
  }

  private static String getElementAndAttrib(NodeList fcast, String name, String attr){
    try{
      for(int i = 0; i < fcast.getLength();i++){
        Node cast = fcast.item(i);
        Element showElement = (Element)cast;
        NodeList textList = showElement.getElementsByTagName(name);
        Element textElement = (Element)textList.item(0);
        NodeList elementList = textElement.getChildNodes();
        if(textElement.hasAttribute(attr)){
          System.out.println(name + " : " +
          (elementList.item(0)).getNodeValue() + " has attribut " + textElement.getAttribute(attr));
          return textElement.getAttribute(attr);
        }else{
          System.out.println("no Attribute");
          return textElement.getTextContent();
        }
      }
    }catch(Exception e){System.out.println(e.getMessage());}
    return "nothing found";
  }
}
