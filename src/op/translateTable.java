package op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//每翻译完一个方法的指令调用一次，因为标签名可能会重名
public class translateTable {
	
	//记录每条标签和他下条byte指令
	public  Map <String,Integer> tabAndNextByteCodePC = new HashMap<>();
	
	public int finishedByteCodeNumber = 0;
	public int temp;
	
	
	public void clear(){
		tabAndNextByteCodePC.clear();
	}
	
	//读取翻译后的byteCode,记录标签信息
	public void readInf(){
		String [] byteCode;
		temp = finishedByteCodeNumber;
		
		System.out.println(globalArguments.finalByteCodePC);
		System.out.println(globalArguments.finalByteCode.size());
		
		for(;finishedByteCodeNumber<globalArguments.finalByteCodePC;finishedByteCodeNumber++){
		//for(;finishedByteCodeNumber<globalArguments.finalByteCode.size();finishedByteCodeNumber++){	
			byteCode = globalArguments.finalByteCode.get(finishedByteCodeNumber).split(" ");
			if(byteCode[0].startsWith(":")){
				tabAndNextByteCodePC.put(byteCode[0], finishedByteCodeNumber+1);
			}
		}
	}
	
	//处理跳转指令
	public void traTab(){
		String [] byteCode;
		int length = 0;
		for(;temp<globalArguments.finalByteCode.size();temp++){
			byteCode = globalArguments.finalByteCode.get(temp).split(" ");
			if(byteCode[0].startsWith(":")){
				continue;
			}
			else{
				length = byteCode.length;
				if(byteCode[length-1].startsWith(":")){
					byteCode[length-1] = tabAndNextByteCodePC.get(byteCode[length-1]).toString();
					String newByteCode = "";
					int i = 0;
					for(i=0;i<length-1;i++){
						newByteCode+=byteCode[i]+" ";
					}
					newByteCode+=byteCode[i];
					globalArguments.finalByteCode.set(temp, newByteCode);
				}
			}
		}
	}
	

}
