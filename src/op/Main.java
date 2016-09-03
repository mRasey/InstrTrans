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
		new File(OutputByteCodeFile.byteCodeSavePath).delete();//如果原先输出文件存在则删除
		new File(OutputClassFile.ClassFileSavePath).delete();
		
		Schedule sch = new Schedule();
		sch.run();
		//优化ldc指令
		globalArguments.rl.replace();
		//处理数据
		globalArguments.cd.complete();
		//处理常量池
		globalArguments.cp.strConstPool();
		//输出byte code
		globalArguments.obcf.print();
		//转化成classwenj
		ClassFile cf = new ClassFile();
		globalArguments.ocf.print(cf.toString().toCharArray());
		
	}
}
