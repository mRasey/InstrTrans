package op;

import transToClass.ClassFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/*
* 在翻译指令时：
* 1.遇到特殊指令，能确定某个寄存器类型时要及时修改registerType
* */


public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Schedule sch = new Schedule();
		sch.run();
		
		ClassFile cf = new ClassFile();
		char[] code = cf.toString().toCharArray();
		File f = new File("res/MainActivity.class");
		FileWriter fw = new FileWriter(f.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		int mark = 0;
		for(int i=0;i<code.length;i++){
			bw.write(code[i++]);
			bw.write(code[i++]);
			bw.write(code[i++]);
			bw.write(code[i]);
			bw.write(" ");
			mark++;
			if(mark == 8){
				bw.write("\n");
			}
		}
		bw.close();
	}
}
