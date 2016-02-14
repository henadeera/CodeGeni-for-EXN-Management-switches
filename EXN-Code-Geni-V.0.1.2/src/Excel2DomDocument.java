import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Excel2DomDocument {
	
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	
	public Document convert (String excel_path, String xml_path) throws InvalidFormatException, Exception
	{
	   
		OPCPackage pkg = null ;
		Document document = null;
	    pkg = OPCPackage.open(new File(excel_path));
	 
	    try {
	        //Initializing the XML document
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        document = builder.newDocument();
	        Element rootElement = document.createElement("data-set");
	        document.appendChild(rootElement);

	        XSSFWorkbook workBookx=  new XSSFWorkbook (pkg); 
	        XSSFSheet sheetx = workBookx.getSheetAt(0);
	        Iterator<?> rowsx     = sheetx.rowIterator ();
	        ArrayList<ArrayList<String>> datax = new ArrayList<ArrayList<String>>();
	        
	       while (rowsx.hasNext ()) 
	        {
	            XSSFRow rowx = (XSSFRow) rowsx.next();
	        	int rowNumberx = rowx.getRowNum ();	
	            // display row number
	        	System.out.println ("Row No.: " + rowNumberx);
	            Iterator<?> cellsx = rowx.cellIterator ();
	            ArrayList<String> rowDatax = new ArrayList<String>();
	            
	            while (cellsx.hasNext ())
	            {
	            	XSSFCell cellx = (XSSFCell) cellsx.next ();
	                switch (cellx.getCellType ())
	                {
	                case XSSFCell.CELL_TYPE_NUMERIC :
	                {
	                    // NUMERIC CELL TYPE
	                    System.out.println ("Numeric: " + cellx.getNumericCellValue ());
	                    rowDatax.add(cellx.getNumericCellValue () + "");
	                    break;
	                }
	                case HSSFCell.CELL_TYPE_STRING :

	                {
	                    // STRING CELL TYPE
	                    XSSFRichTextString richTextString = cellx.getRichStringCellValue ();

	                    System.out.println ("String: " + richTextString.getString ());
	                    rowDatax.add(richTextString.getString ());
	                    break;
	                }
	                default:
	                {
	                    // types other than String and Numeric.
	                    System.out.println ("Type not supported.");
	                    break;
	                }
	                } // end switch

	            }  // end while 
	            datax.add(rowDatax);


	        } //end while 

	        int numOfProduct = datax.size();

	        for (int i = 1; i < numOfProduct; i++){
	            Element productElement = document.createElement("record");
	            rootElement.appendChild(productElement);

	            int index = 0;
	            for(String s: datax.get(i)) {
	                String headerString = datax.get(0).get(index);
	                if( datax.get(0).get(index).equals("image link") ){
	                    headerString = "image_link";
	                }

	                if( datax.get(0).get(index).equals("product type") ){
	                    headerString = "product_type";
	                }

	                Element headerElement = document.createElement(headerString);
	                productElement.appendChild(headerElement);
	                headerElement.appendChild(document.createTextNode(s));
	                index++;
	            }
	        }

	        TransformerFactory tFactory = TransformerFactory.newInstance();

	        Transformer transformer = tFactory.newTransformer();
	        //Add indentation to output
	        transformer.setOutputProperty
	        (OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(
	                "{http://xml.apache.org/xslt}indent-amount", "2");

	        DOMSource source = new DOMSource(document);
	        StreamResult result = new StreamResult(new File(xml_path+"_" +dateFormat.format(new Date())+".xml"));
	        transformer.transform(source, result);

	    }
	    catch (ParserConfigurationException e) {
	        System.out.println("ParserConfigurationException " + e.getMessage());
	    } catch (TransformerConfigurationException e) {
	        System.out.println("TransformerConfigurationException "+ e.getMessage());
	    } catch (TransformerException e) {
	        System.out.println("TransformerException " + e.getMessage());
	    }
	    return document;
	}
 
}
