import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadTxtFile {
	
	String textFile;
	
	public ReadTxtFile(String textFile){
		this.textFile = textFile;
	}
	
	public String txtFile2String() throws IOException{
	
		BufferedReader br = new BufferedReader(new FileReader(textFile));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } 
	    
	    finally {
	        br.close();
	    }
	}
	
	public String[] exctractKeywords (String fileString){
		String keywords="";
		for (int i = 0;i< fileString.length();i++) {	
			char character = fileString.charAt(i);
			if (character=='['){		
				for (;i< fileString.length();i++){
					if (character!=']'){
						character = fileString.charAt(i);
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
}
