import java.awt.Component;
import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class Xml2DomDocument {
	
	private String xmlFileName;
	
	public Xml2DomDocument(String xmlFilePath){
		this.xmlFileName = xmlFilePath;						
	}
	
    public Document parseXML(){
    	Document doc = null;
		try {
			File inputFile = new File(xmlFileName);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
			
	        return doc;
			
		}catch (Exception e) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame,
					e.toString() + "\n"+"Program will be closed.",
				    "Warning- EXN-Code_Geni-V.0.1",
				    JOptionPane.WARNING_MESSAGE);
		         e.printStackTrace();
		      }
      
    	return doc;
    }
    
    
    
}
