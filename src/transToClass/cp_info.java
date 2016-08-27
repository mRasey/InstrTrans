package transToClass;

import java.util.ArrayList;

/**
 * 常量池
 */
public class cp_info {
    u1 tag = new u1();
    String info = "";
    
    //把常量传进来   #1 = Utf8 0x1
    public cp_info(String str){
    	String[] data;
    	data = str.split(" ");
    	
    	//填充数据
    	//Float,Long,Double,Integer翻译的方式都不一样，不过dexCode里是直接翻译好的，我们正好可以直接用。
    	if(data[3].startsWith("0x")){
    		int len = data[3].length();
    		//8位
    		if(data[3].charAt(len-1) == 'L'){
    			info+=data[3].substring(2,len-1);
    		}
//    		//4位
//    		else if(data[3].charAt(len-1) == 'F'){
//    			
//    		}
//    		//8位
//    		else if(data[3].charAt(len-1) == 'D'){
//    			
//    		}
//    		//Integer用4位
//    		else{
//    			int temp = toInt(data[3].substring(2,len));
//    			u4 num = new u4( temp);
//    			info+=num.toString();
//    		}
    		else{
    			info+=data[3].substring(2);
    		}
			
		}
		else if(data[3].startsWith("#")){
			char[] c = data[3].toCharArray();
			int temp = 0;
			for(int i=0;i<data[3].length();i++){
				if(c[i] == '#'){
					i++;
					while(i<data[3].length() && c[i] >= '0' && c[i] <= '9'){
						temp = temp*10 + c[i]-'0';
						i++;
					}
					u2 num = new u2((short) temp);
					info+=num.toString();
				}
			}
		}
		else{
			u2 len = new u2((short) data[3].length());
			info+=len.toString();
			char[] c = data[3].toCharArray();
			for(int i=0;i<data[3].length();i++){
				u1 temp = new u1((byte) c[i]);
				info+=temp.toString();
			}
			
			
		}
    	
    	//设置标号
		switch (data[2]) {
		case "Utf8":
			tag.set((byte) 1);
			break;
		case "Class":
			tag.set((byte) 7);
			break;
		case "Fieldref":
			tag.set((byte) 9);
			break;
		case "Methodref":
			tag.set((byte) 10);
			break;
		case "InterfaceMethodref":
			tag.set((byte) 11);
			break;
		case "String":
			tag.set((byte) 8);
			break;
		case "Integer":
			tag.set((byte) 3);
			break;
		case "Float":
			tag.set((byte) 4);
			break;
		case "Long":
			tag.set((byte) 5);
			break;
		case "Double":
			tag.set((byte) 6);
			break;
		case "NameAndType":
			tag.set((byte) 12);
			break;
		case "MethodHandle":
			tag.set((byte) 15);
			break;
		case "MethodType":
			tag.set((byte) 16);
			break;
		case "InvokeDynamic":
			tag.set((byte) 18);
			break;
		}
		
		
		
    }

    /*
     * 标号(1byte) (+长度(2byte) + 数据
     * */
    @Override
    public String toString() {
        return tag.toString()
                + info;
    }
    
    
    public  int toInt(String str) {  
        if(str==null||str.length()<1){  
            throw new RuntimeException("字符串不合法");  
        }  
          
        int sum=0;  
        int n=0;  
          
        for(int i=0;i<str.length();i++){  
            char c=str.charAt(str.length()-1-i);  
            if(c>='a'&&c<='f'||c>='A'&&c<='F'){  
                n=Character.toUpperCase(c)-'A';
                n+=10;
            }else if(c>='0'&&c<='9'){  
                n=c-'0';  
            }else{  
                throw new RuntimeException("字符串不合法");  
            }  
            sum+=n*(1<<4*i);  
        }  
        return sum;  
    }  
    
}
