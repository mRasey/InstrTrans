package op;

import exec.OpApk;
import instructions.*;
import transToClass.ClassFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/*
* 在翻译指令时：
* 1.遇到特殊指令，能确定某个寄存器类型时要及时修改registerType
* */


public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Schedule sch = new Schedule();
		sch.run();
		
		ClassFile cf = new ClassFile();
	}
}
