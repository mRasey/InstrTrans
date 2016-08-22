package op;

import java.io.BufferedWriter;
import java.io.File;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class output {
	static String byteCodeSavePath = "E:\\result.txt";
	
	int outputTypeCodeNumber = 0;
	
	File byteCodeFile = new File(byteCodeSavePath);
    
	public void print() throws IOException{
		FileWriter fw = new FileWriter(byteCodeFile.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
        for(;outputTypeCodeNumber<globalArguments.traTabByteCodePC;outputTypeCodeNumber++) {
        	bw.write(outputTypeCodeNumber+": " + globalArguments.traTabByteCode.get(outputTypeCodeNumber) +"\n");
        }
        bw.close();
	}
	

}
