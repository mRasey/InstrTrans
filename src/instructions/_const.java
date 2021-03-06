package instructions;

import op.Register;
import op.globalArguments;

import java.util.ArrayList;

import static op.globalArguments.dexCodeNumber;

/*数据定义指令
数据定义指令用来定义程序中用到的常量，字符串，类等数据。它的基础字节码为const。
“const/4 vA, #+B”：将数值符号扩展为32位后赋给寄存器vA。
“const/16 vAA, #+BBBB”：将数据符号扩展为32位后赋给寄存器vAA。
“const vAA, #+BBBBBBBB”：将数值赋给寄存器vAA。
“const/high16 vAA, #+BBBB0000“：将数值右边零扩展为32位后赋给寄存器vAA。
“const-wide/16 vAA, #+BBBB”：将数值符号扩展为64位后赋给寄存器对vAA。
“const-wide/32 vAA, #+BBBBBBBB”：将数值符号扩展为64位后赋给寄存器对vAA。
“const-wide vAA, #+BBBBBBBBBBBBBBBB”：将数值赋给寄存器对vAA。
“const-wide/high16 vAA, #+BBBB000000000000”：将数值右边零扩展为64位后赋给寄存器对vAA。
“const-string vAA, string@BBBB”：通过字符串索引构造一个字符串并赋给寄存器vAA。
“const-string/jumbo vAA, string@BBBBBBBB”：通过字符串索引（较大）构造一个字符串并赋给寄存器vAA。
“const-class vAA, type@BBBB”：通过类型索引获取一个类引用并赋给寄存器vAA。
“const-class/jumbo vAAAA, type@BBBBBBBB”：通过给定的类型索引获取一个类引用并赋给寄存器vAAAA。
                                            这条指令占用两个字节，值为0xooff（Android4.0中新增的指令）。*/
public class _const extends Instruction{

    @Override
    public void analyze(String[] dexCodes) {
        super.analyze(dexCodes);
    	String newInstr;
    	//保存dex->class
        Register firstRegister = globalArguments.registerQueue.getByDexName(dexCodes[1]);
    	
        String dataType = "";
        dataType = firstRegister.getType(dexCodeNumber);
        //System.out.println(dexCodes[0]+" "+dexCodes[1]+" "+dexCodes[2]);
        switch (dexCodes[0]){
        	//char 应该放在哪里？？？
            case "const/4" :
            case "const/16" :
            case "const" :
            case "const/high16" :
            	switch(dataType){
            		case "Z":
            		case "I":
            			globalArguments.finalByteCode.add("ldc"+" "+ dexCodes[2]);
            			globalArguments.finalByteCode.add("istore" + " " + firstRegister.stackNum);
            			globalArguments.finalByteCodePC += 2;
            			break;
            		case "F":
            			globalArguments.finalByteCode.add("ldc"+" "+dexCodes[2]+"F");
            			globalArguments.finalByteCode.add("fstore" + " " + firstRegister.stackNum);
            			globalArguments.finalByteCodePC += 2;
            			break;
            		case "S":
            			globalArguments.finalByteCode.add("sipush"+" "+dexCodes[2]);
            			globalArguments.finalByteCode.add("istore" + " " + firstRegister.stackNum);
            			globalArguments.finalByteCodePC += 2;
            			break;
            		case "B":
            		case "C":
            			globalArguments.finalByteCode.add("bipush"+" "+dexCodes[2]);
            			globalArguments.finalByteCode.add("istore" + " " + firstRegister.stackNum);
            			globalArguments.finalByteCodePC += 2;
            			break;
            		default:
            			System.err.println("error in _const/analyze");
            			System.err.println(globalArguments.dexCodeNumber);
            			for(int i=0; i<dexCodes.length;i++){
            				System.err.print(dexCodes[i]+" ");
            			}
            			System.out.println("");
            			break;
            	}
            	break;
            case "const-wide/16" :
            case "const-wide/32" :
            case "const-wide" :
            case "const-wide/high16" :
            	switch(dataType){
            		case "J":
            			if(dexCodes[2].charAt(dexCodes[2].length()-1) != 'L'){
            				dexCodes[2]+='L';
            			}
            			globalArguments.finalByteCode.add("ldc2_w"+" "+dexCodes[2]);
            			globalArguments.finalByteCode.add("lstore" + " " + firstRegister.stackNum);
            			globalArguments.finalByteCodePC += 2;
            			break;
            		case "D":
            			if(dexCodes[2].charAt(dexCodes[2].length()-1) != 'L'){
            				dexCodes[2]+='L';
            			}
            			globalArguments.finalByteCode.add("ldc2_w"+" "+dexCodes[2]);
            			globalArguments.finalByteCode.add("dstore" + " " + firstRegister.stackNum);
            			globalArguments.finalByteCodePC += 2;
            			break;
            		default:
            			System.err.println("error in _const/analyze");
            			System.err.println(globalArguments.dexCodeNumber);
            			for(int i=0; i<dexCodes.length;i++){
            				System.err.print(dexCodes[i]+" ");
            			}
            			break;
            	}
            	break;
            	
            case "const-string" :
            case "const-string/jumbo" :
                globalArguments.finalByteCode.add("ldc" + " " + dexCodes[2]);
    			globalArguments.finalByteCode.add("astore" + " " + firstRegister.stackNum);
    			globalArguments.finalByteCodePC += 2;
            	break;
            	
            //类的引用不确定怎么翻译？
            case "const-class" :
            case "const-class/jumbo" :
                globalArguments.finalByteCode.add("ldc" + " " + dexCodes[2]);
    			globalArguments.finalByteCode.add("astore" + " " + firstRegister.stackNum);
    			globalArguments.finalByteCodePC += 2;
            	break;
        }
    }

    public boolean ifUpgrade(ArrayList<String> firstDexCode, ArrayList<String> secondDexCode, ArrayList<String> thirdDexCode, int lineNum) {
    	Register register = globalArguments.registerQueue.getByDexName(firstDexCode.get(1));
    	if(register.getType(lineNum) == null){
    		if(register.currentType != null){
    			register.updateType(lineNum, register.currentType);
    		}
    		else{
    			register.updateType(lineNum, "I");
    		}
    	}
    	return true;
    }
    
    
//	@Override
//	public boolean ifUpgrade(ArrayList<String> firstDexCode, ArrayList<String> secondDexCode, ArrayList<String> thirdDexCode, int lineNum) {
//		boolean ifDeal = false;
//		
//		if(thirdDexCode.get(0).equals(".local")) {
//			Register register = globalArguments.registerQueue.getByDexName(firstDexCode.get(1));
//			if(register.dexName.equals(thirdDexCode.get(1))){
//				register.updateType(lineNum,
//						thirdDexCode.get(2).substring(thirdDexCode.get(2).lastIndexOf(":") + 1));
//				ifDeal = true;
//			}
//			
//		}
//		if(secondDexCode.get(0).equals(".local")){
//			Register register = globalArguments.registerQueue.getByDexName(firstDexCode.get(1));
//			if(register.dexName.equals(secondDexCode.get(1))){
//				register.updateType(lineNum,
//						secondDexCode.get(2).substring(secondDexCode.get(2).lastIndexOf(":") + 1));
//				ifDeal = true;
//			}
//			
//		}
//		if(secondDexCode.get(0).contains("sput") || secondDexCode.get(0).contains("sget")){
//			if(secondDexCode.get(1).equals(firstDexCode.get(1))){
//				Register register = globalArguments.registerQueue.getByDexName(secondDexCode.get(1));
//		        register.updateType(lineNum, secondDexCode.get(2).substring(secondDexCode.get(2).lastIndexOf(":")+1));
//		        ifDeal = true;
//			}
//			
//		}
//		if(secondDexCode.get(0).contains("iput") || secondDexCode.get(0).contains("iget")){
//			if(secondDexCode.get(1).equals(firstDexCode.get(1))){
//				Register register = globalArguments.registerQueue.getByDexName(secondDexCode.get(1));
//		        register.updateType(lineNum, secondDexCode.get(3).substring(secondDexCode.get(3).lastIndexOf(":")+1));
//		        ifDeal = true;
//			}
//		}
//		if(secondDexCode.get(0).contains("invoke")){
//			int i = 0, t = 0;
//			for(i=1;i<secondDexCode.size()-1;i++){
//				if(secondDexCode.get(i).equals(firstDexCode.get(1))){
//					t = 1;
//				}
//			}
//			if(t == 0){
//			}
//			else{
//				new _invoke().ifUpgrade(secondDexCode,lineNum);
//				ifDeal = true;
//			}
//		}
//		if(secondDexCode.get(0).contains("if")){
//			if(secondDexCode.get(0).contains("z")){
//				if(secondDexCode.get(1).equals(firstDexCode.get(1))){
//					Register register = globalArguments.registerQueue.getByDexName(secondDexCode.get(1));
//					register.updateType(lineNum, "I");
//					ifDeal = true;
//				}
//	        }
//	        else{
//	        	if(secondDexCode.get(1).equals(firstDexCode.get(1)) || secondDexCode.get(2).equals(firstDexCode.get(1))){
//	        		ifDeal = true;
//	        		Register firstRegister = globalArguments.registerQueue.getByDexName(secondDexCode.get(1));
//		            firstRegister.updateType(lineNum, firstRegister.currentType);
//		            Register secondRegister = globalArguments.registerQueue.getByDexName(secondDexCode.get(2));
//		            secondRegister.updateType(lineNum, secondRegister.currentType);
//		            if(firstRegister.getType(lineNum) == null && secondRegister.getType(lineNum) != null){
//		            	firstRegister.updateType(lineNum,secondRegister.currentType);
//		            }
//		            else if(firstRegister.getType(lineNum) != null && secondRegister.getType(lineNum) == null){
//		            	secondRegister.updateType(lineNum,firstRegister.currentType);
//		            }
//		            else{
//		            }
//	        	}
//	        }
//		}
//		if(secondDexCode.get(0).contains("aput") || secondDexCode.get(0).contains("aget")){
//			 Register firstRegister = globalArguments.registerQueue.getByDexName(secondDexCode.get(1));
//			 Register secondRegister = globalArguments.registerQueue.getByDexName(secondDexCode.get(2));
//	         firstRegister.updateType(lineNum, secondRegister.currentType.substring(secondRegister.currentType.indexOf("[") + 1));
//	         secondRegister.updateType(lineNum, secondRegister.currentType);
//	         if(secondDexCode.get(3).equals(firstDexCode.get(1))){
//					Register register = globalArguments.registerQueue.getByDexName(firstDexCode.get(1));
//					register.updateType(lineNum, "I");
//					ifDeal = true;
//			}
//		}
//		if(secondDexCode.get(0).contains("return")){
//			if(secondDexCode.size() > 1){
//				if(secondDexCode.get(1).equals(firstDexCode.get(1))){
//					String methodInf = globalArguments.methodName;
//		            String dataType = methodInf.substring(methodInf.indexOf(")")+1);
//		            Register register = globalArguments.registerQueue.getByDexName(secondDexCode.get(1));
//		            register.updateType(lineNum, dataType);
//		            ifDeal = true;
//				}
//	            
//	        }
//		}
//		if(  secondDexCode.get(0).contains("add-")
//                || secondDexCode.get(0).contains("sub-")
//                || secondDexCode.get(0).contains("mul-")
//                || secondDexCode.get(0).contains("div-")
//                || secondDexCode.get(0).contains("rem-")
//                || secondDexCode.get(0).contains("and-")
//                || secondDexCode.get(0).contains("or-")
//                || secondDexCode.get(0).contains("xor-")
//                || secondDexCode.get(0).contains("shl-")
//                || secondDexCode.get(0).contains("shr-")
//                || secondDexCode.get(0).contains("ushr-")){
//	        String dataType = secondDexCode.get(0).substring(secondDexCode.get(0).indexOf("-") + 1, secondDexCode.get(0).indexOf("-") + 2).toUpperCase();//获得数据类型
//	        if(dataType.equals("L")){
//	        	dataType = "J";
//	        }
//	        Register register = globalArguments.registerQueue.getByDexName(firstDexCode.get(1));
//	        if(secondDexCode.get(0).contains("/")){
//	        	if(secondDexCode.get(1).equals(firstDexCode.get(1)) || secondDexCode.get(2).equals(firstDexCode.get(1))){
//	        		register.updateType(lineNum, dataType);
//	        		ifDeal = true;
//	        	}
//	        }
//	        else{
//	        	if(secondDexCode.get(1).equals(firstDexCode.get(1)) || secondDexCode.get(2).equals(firstDexCode.get(1)) || secondDexCode.get(3).equals(firstDexCode.get(1))){
//	        		register.updateType(lineNum, dataType);
//	        		ifDeal = true;
//	        	}
//	        }
//		}
//		if(secondDexCode.get(0).equals("new-array") || secondDexCode.get(0).equals("array-length")){
//			if(secondDexCode.get(2).equals(firstDexCode.get(1))){
//				Register register = globalArguments.registerQueue.getByDexName(firstDexCode.get(1));
//				register.updateType(lineNum, "I");
//				ifDeal = true;
//			}
//		}
//		if(thirdDexCode.get(0).contains("aput") || thirdDexCode.get(0).contains("aget")){
//			 Register firstRegister = globalArguments.registerQueue.getByDexName(thirdDexCode.get(1));
//			 Register secondRegister = globalArguments.registerQueue.getByDexName(thirdDexCode.get(2));
//	         firstRegister.updateType(lineNum, secondRegister.currentType.substring(secondRegister.currentType.indexOf("[") + 1));
//	         secondRegister.updateType(lineNum, secondRegister.currentType);
//	         if(thirdDexCode.get(3).equals(firstDexCode.get(1))){
//					Register register = globalArguments.registerQueue.getByDexName(firstDexCode.get(1));
//					register.updateType(lineNum, "I");
//					ifDeal = true;
//			}
//		}
//		if(!ifDeal){
//			Register register = globalArguments.registerQueue.getByDexName(firstDexCode.get(1));
//			if(register.currentType == null){
//				register.updateType(lineNum, "I");
//			}
//			else{
//				register.updateType(lineNum, register.currentType);
//			}
//		}
//
//		return true;
//	}
}
