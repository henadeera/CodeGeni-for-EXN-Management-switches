import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JButton;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/* Written by : Chinthaka Henadeera
 * Date      : 01.02.2016
 * Class     : Main
 * Purpose   : providing GUI
 * 
 * */

public class Main {


	private JFrame frmExncodegeni;
	JLabel lblMessage;

	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	Map<String,String> map =  new HashMap<String, String>();;
	String user_path;
	String cat_path;
	String xml_path;
	String excel_path;
	String icon_path;
	String output_path;
	String record_element;
	String [] categoies;
	String [] excelFiles;
	String cat_name;
	String xml_name;
	String excel_name;

	/**
	 * Launch the application.
	 * @throws IOException 
	 */



	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmExncodegeni.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 * @throws URISyntaxException 
	 */
	public Main() throws URISyntaxException {
		System.out.println("Working Directory = " +
				System.getProperty("user.dir"));
		user_path= System.getProperty("user.dir");
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			prop.load(input);

			cat_path =user_path + prop.getProperty("cat_path");
			xml_path = user_path + prop.getProperty("xml_path");
			icon_path=user_path + prop.getProperty("icon_path");
			output_path=user_path + prop.getProperty("output_path");
			excel_path = user_path + prop.getProperty("excel_path"); 
			record_element= prop.getProperty("record_element");

		} catch (IOException ex) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame,
					ex.toString() + "\n"+"Program will be closed.",
					"Warning- EXN-Code_Geni-V.0.1",
					JOptionPane.WARNING_MESSAGE);
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							e.toString() + "\n"+"Program will be closed.",
							"Warning- EXN-Code_Geni-V.0.1",
							JOptionPane.WARNING_MESSAGE);
					e.printStackTrace();
				}
			}
		}
		setCategories();
		setExcelFile();
		initialize();

	}
	public void setCategories(){
		File [] f1 = listf(cat_path);
		//validate f1
		if (f1==null ){
			Component frame = null;
			JOptionPane.showMessageDialog(frame,
					"\n"+"Please set the path for cat folder and restart.",
					"Warning- EXN-Code_Geni-V.0.1",
					JOptionPane.WARNING_MESSAGE);
			System.exit(0);

		}
		categoies = new String[f1.length+1];
		categoies[0]="";
		for (int i = 0; i < f1.length; i++){
			categoies[i+1] = f1[i].getName();
		}

	}

	public void setExcelFile(){
		File [] f1 = listf(excel_path);
		//validate f1
		if (f1==null ){
			Component frame = null;
			JOptionPane.showMessageDialog(frame,
					"\n"+"Please set the path for xml folder and restart.",
					"Warning- EXN-Code_Geni-V.0.1",
					JOptionPane.WARNING_MESSAGE);
			System.exit(0);

		}
		excelFiles = new String[f1.length+1];
		excelFiles[0]="";
		for (int i = 0; i < f1.length; i++){
			excelFiles[i+1] = f1[i].getPath();
		}


	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {



		frmExncodegeni = new JFrame();
		frmExncodegeni.setResizable(false);
		frmExncodegeni.setIconImage(Toolkit.getDefaultToolkit().getImage(icon_path+"Entiretec_Log.png"));
		frmExncodegeni.setTitle("EXN-Code-Geni- v.0.1.1");
		frmExncodegeni.setBounds(100, 100, 786, 288);
		frmExncodegeni.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmExncodegeni.getContentPane().setLayout(null);

		lblMessage = new JLabel("");
		lblMessage.setBounds(113, 26, 566, 14);
		frmExncodegeni.getContentPane().add(lblMessage);
		
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setBounds(41, 79, 62, 14);
		frmExncodegeni.getContentPane().add(lblCategory);
		


		JComboBox<String []> cmbCategory = new JComboBox <String []>();
		cmbCategory.setEditable(true);
		cmbCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				cat_name = cmbCategory.getSelectedItem().toString();

			}
		});
		
		//cmbCategory.setModel(new DefaultComboBoxModel(new String[] {"", "Testing1", "Testing2", "Testing3"}));
		cmbCategory.setModel(new DefaultComboBoxModel(categoies));
		cmbCategory.setBounds(113, 73, 175, 20);
		frmExncodegeni.getContentPane().add(cmbCategory);

		JComboBox<String []> cmbXmlFile = new JComboBox<String []>();
		cmbXmlFile.setEditable(true);
		cmbXmlFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				excel_name = cmbXmlFile.getSelectedItem().toString();

			}
		});
		//cmbCategory.setModel(new DefaultComboBoxModel(new String[] {"", "Testing1", "Testing2", "Testing3"}));
		cmbXmlFile.setModel(new DefaultComboBoxModel(excelFiles));
		cmbXmlFile.setBounds(113, 117, 522, 20);
		frmExncodegeni.getContentPane().add(cmbXmlFile);


		JLabel lblXmlFile = new JLabel("Excel file:");
		lblXmlFile.setBounds(41, 123, 62, 14);
		frmExncodegeni.getContentPane().add(lblXmlFile);

		JButton btnGenerate = new JButton("Generate");
		
		
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				try {
					cat_name = cmbCategory.getSelectedItem().toString();
					if (cat_name.isEmpty()){
						JOptionPane.showMessageDialog(frmExncodegeni,
								"Please select a category." ,
								"Warning- EXN-Code_Geni-V.0.1",
								JOptionPane.WARNING_MESSAGE); 
						        lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");
					}
					generate();
					lblMessage.setText("Output file was generated successfully.");
					Runtime.getRuntime().exec("explorer.exe /open," + output_path);
					
					
				} catch (Exception e1) {
					lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGenerate.setBounds(375, 187, 89, 23);
		frmExncodegeni.getContentPane().add(btnGenerate);

		JButton btnCancel = new JButton("Exit");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(490, 187, 89, 23);
		frmExncodegeni.getContentPane().add(btnCancel);
	
		
		JButton btnViewExcelFile = new JButton("");
		btnViewExcelFile.setToolTipText("View Excel");
		btnViewExcelFile.setIcon(new ImageIcon(icon_path+"viewFile_24PX.png"));
		btnViewExcelFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					if (cmbXmlFile.getSelectedItem().toString().trim().isEmpty()){
						JOptionPane.showMessageDialog(frmExncodegeni,
								"Please select an Excel file first." ,
								"Warning- EXN-Code_Geni-V.0.1",
								JOptionPane.WARNING_MESSAGE);
						        lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");
					}
					
					Runtime.getRuntime().exec("cmd /c start " +excel_name); 
					
				} catch (IOException e) {
					lblMessage.setText("Oops! An error occured.");
					// TODO Auto-generated catch block
					e.printStackTrace();
				
					
				}
			}
			
		}); 
		
		btnViewExcelFile.setBounds(642, 108, 26, 29);
		frmExncodegeni.getContentPane().add(btnViewExcelFile);
		
		
		JButton btnOpenXmlFolder = new JButton("");
		btnOpenXmlFolder.setToolTipText("Open Xml folder");
		btnOpenXmlFolder.setIcon(new ImageIcon(icon_path+"openXmlFolder.png"));
		btnOpenXmlFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (xml_path.isEmpty()){
						JOptionPane.showMessageDialog(frmExncodegeni,
								"Oops!!! XML path is not set. This is an internal error." ,
								"Warning- EXN-Code_Geni-V.0.1",
								JOptionPane.WARNING_MESSAGE);
						        lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");
			
				}  
					System.out.println("xml path " + xml_path);
				    Runtime.getRuntime().exec("explorer.exe /open," + excel_path);
				    
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");
					e1.printStackTrace();
				}
				
				
				}
				
				
			}
		);
		btnOpenXmlFolder.setBounds(678, 108, 26, 29);
		frmExncodegeni.getContentPane().add(btnOpenXmlFolder);
		
		JLabel lblLoudspeaker = new JLabel("");
		lblLoudspeaker.setToolTipText("Voice of Mr. Extreme  :)");
		lblLoudspeaker.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblMessage.setText("Hello " + System.getProperty("user.name").substring(1));
			}
		});
		
		lblLoudspeaker.setIcon(new ImageIcon(icon_path+"loudspeaker.png"));
		lblLoudspeaker.setBounds(54, 11, 37, 40);
		frmExncodegeni.getContentPane().add(lblLoudspeaker);
		
		JButton btnViewTemplate = new JButton("");
		btnViewTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnViewTemplate.setToolTipText("View Excel");
		btnViewTemplate.setBounds(298, 64, 26, 29);
		frmExncodegeni.getContentPane().add(btnViewTemplate);
		
		JButton btnCreateExcelTemplate = new JButton("");
		btnCreateExcelTemplate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
	try {
					
					if (cmbCategory.getSelectedItem().toString().trim().isEmpty()){
						JOptionPane.showMessageDialog(frmExncodegeni,
								"Please select an Excel file first." ,
								"Warning- EXN-Code_Geni-V.0.1",
								JOptionPane.WARNING_MESSAGE);
						        lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");
					}
					
					//Runtime.getRuntime().exec("cmd /c start " +excel_name); 
					cat_name = cmbCategory.getSelectedItem().toString();
					if (cat_name.isEmpty()){
						JOptionPane.showMessageDialog(frmExncodegeni,
								"Please select a category." ,
								"Warning- EXN-Code_Geni-V.0.1",
								JOptionPane.WARNING_MESSAGE); 
						        lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");
					}
					
					ReadTxtFile readTxtFile = new ReadTxtFile(cat_name);
					ExcelDocument excelDocument = new ExcelDocument();
					excelDocument.create(readTxtFile.exctractKeywords(readTxtFile.txtFile2String()), cat_name+ ".xlsx", excel_path);
					
					
				} catch (IOException e) {
					lblMessage.setText("Oops! An error occured.");
					// TODO Auto-generated catch block
					e.printStackTrace();
				
					
				}
				
				
			}
		});
		btnCreateExcelTemplate.setToolTipText("View Excel");
		btnCreateExcelTemplate.setBounds(334, 64, 26, 29);
		frmExncodegeni.getContentPane().add(btnCreateExcelTemplate);

	}

	
	
	/*Method : public  String[] txtTempate2KeywordArray(String txtTemplate)
	 *Purpose: Extracts keywords from template and return them in an String[].
	 *Called by : public void codeMerge( Map<String,String> map, NodeList nList)
	 */

	public  String[] txtTempate2KeywordArray(String txtTemplate){

		String keywords="";
		for (int i = 0;i< txtTemplate.length();i++) {	
			char character = txtTemplate.charAt(i);
			if (character=='['){		
				for (;i< txtTemplate.length();i++){
					if (character!=']'){
						character = txtTemplate.charAt(i);
						keywords = keywords + character;
					}
					else{
						keywords = keywords + " ";
						break;
					}
				}				
			}
		} 

		return keywords.split("\\s+");

	}



	public  void creatingMAP(Map<String,String> map, String key, String  value){

		map.put(key.trim(), value.trim()); /*device_model, template*/

	}


	/*Method : public  void codeMerge( Map<String,String> map, NodeList nList )
	 *Purpose: Merge templates with data and writes into a text file.
	 *called by : public  void  generate() throws IOException
	 */
	public void codeMerge( Map<String,String> map, NodeList nList ) throws FileNotFoundException{

		String output="";
		String device_model = "";
		String txtCode="";
		String txtTemplate="";
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				device_model = eElement.getElementsByTagName("device_model").item(0).getTextContent().trim().toUpperCase();
				txtTemplate = map.get(device_model);

				if(txtTemplate ==null){
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							"No template has been given for " +device_model + " devices in this category." ,
							"Warning- EXN-Code_Geni-V.0.1",
							JOptionPane.WARNING_MESSAGE);
					        lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");
					break;
				}
				String[] keywords_array = txtTempate2KeywordArray(txtTemplate);
				txtCode = txtTemplate;
				for (int j=0; j<keywords_array.length;j++){
					String key = keywords_array[j].trim();
					txtCode = txtCode.replace(key, eElement.getElementsByTagName(key.substring(1, key.length()-1)).item(0).getTextContent());
				}  
			}
			System.out.println(txtCode);
			output =output + txtCode +"\n\n\n\n";
		}   
         
		File folder = new File(output_path);
		if (!folder.exists()){
			 try{
			        folder.mkdir();
			        
			    } 
			   catch(SecurityException se){
			        //handle it
			    }  
		}
		PrintWriter out = new PrintWriter(output_path+cat_name+"_" +dateFormat.format(new Date())+".txt");
		out.println(output);
		out.close();

	}


	public  File[] listf(String directoryName) {

		// list file
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();   
		return fList;
	} 

	public  void  generate() throws InvalidFormatException, Exception{

		File [] f = listf(cat_path+cat_name.trim());

		if(f!=null){

			map.clear();	//important to have an empty map 

			//add txt file strings into a hashmap <deivce_model, code>
			for (int a = 0 ; a< f.length;a++){		
				ReadTxtFile readTxtFile = new ReadTxtFile(f[a].toString());
				String txtTemplate = readTxtFile.txtFile2String().trim();
				String fileName= f[a].getName().trim();
				if(!txtTemplate.isEmpty()){
					creatingMAP(map,fileName.substring(0, fileName.length()-4),txtTemplate);
				}
				else{
					Component frame = null;
					JOptionPane.showMessageDialog(frame,
							"Found an empty template !!!",
							"Warning- EXN-Code_Geni-V.0.1",
							JOptionPane.WARNING_MESSAGE);
					        lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");
				}

			}
		}
		else{

			Component frame = null;
			JOptionPane.showMessageDialog(frame,
					"No code templates are in selected category. Please save a code template in the category",
					"Warning- EXN-Code_Geni-V.0.1",
					JOptionPane.WARNING_MESSAGE);
			        lblMessage.setText("Hello " + System.getProperty("user.name").substring(1) + ", Oops! An error occured.");

		}

		Excel2DomDocument excel2DomDocument = new Excel2DomDocument();
		Document doc  = excel2DomDocument.convert(excel_name, xml_path);
		NodeList nList = doc.getElementsByTagName(record_element);
		codeMerge(map, nList );	
	}
}
