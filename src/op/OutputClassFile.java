package op;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputClassFile {
	static String ClassFileSavePath = "res/MainActivity.class";
	
	public void print(char[] code) throws IOException{
		File byteCodeFile = new File(ClassFileSavePath);
		FileWriter fw = new FileWriter(byteCodeFile.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		//char[] binary;
		String bString = "", btmp;
		int i = 0;
		String data = "";
		for(i=0;i<code.length;i++){
			data+=code[i];
		}
	    for (i = 0; i < data.length(); i++)  
	    {  
	        btmp = "0000"  
	                    + Integer.toBinaryString(Integer.parseInt(data  
	                            .substring(i, i + 1), 16));  
	        bString += btmp.substring(btmp.length() - 4);  
	    }
		//binary = bString.toCharArray();
		bw.write(bString);
		bw.close();
	}
	
}
