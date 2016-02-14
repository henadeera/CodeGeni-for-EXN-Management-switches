package exn.utils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileStreamManupilation {
	
	
	public String FileStream2String(InputStream input) throws IOException{
		
		String output = "";
		StringBuilder sb=new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String read;

		while((read=br.readLine()) != null) {
		    sb.append(read);   
		}
		br.close();
		output = sb.toString();
		return output;
	}
	
	
	public InputStream String2FileStream(String input){
		
		InputStream output = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		return output;		
	}

}
