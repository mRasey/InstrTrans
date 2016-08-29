package transToClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import op.globalArguments;

public class ClassFile {
    u4 magic = new u4();// 魔数 0xCAFEBABE
    u2 minor_version = new u2();// class文件副版本号
    u2 major_version = new u2();// class文件主版本号
    u2 constant_pool_count = new u2();// 常量池计数器
    cp_info[] constant_pool;// 常量池
    u2 access_flags = new u2();// 访问标志
    u2 this_class = new u2();// 类索引
    u2 super_class = new u2();// 父类索引
    u2 interfaces_count = new u2();// 接口计数器
    u2[] interfaces;// 接口表
    u2 fields_count = new u2();// 字段计数器
    field_info[] fields;// 字段表
    u2 method_count = new u2();// 方法计数器
    method_info[] methods;// 方法表
    u2 attributes_count = new u2();// 属性计数器
    attribute_info[] attributes;// 属性表
    
    ArrayList<String> byteCodeData = new ArrayList<>();
    
    public ClassFile() {
    	readByteCodeFile();
    	magic.set(-889275714);
    	minor_version.set((short) 0);
    	major_version.set((short) 50);
    	constant_pool_count.set((short) globalArguments.const_id);
    	constant_pool = new cp_info[constant_pool_count.get() - 1];
    	fill_constant_pool();
        
        
        
        
        
//        interfaces = new u2[interfaces_count.get() - 1];
//        fields = new field_info[fields_count.get() - 1];
//        methods = new method_info[method_count.get() - 1];
//        attributes = new attribute_info[attributes_count.get() - 1];
    }

    @Override
    public String toString() {
        String constant_pool_string = "";
        for(int i = 0; i < constant_pool_count.get()-1; i++)
            constant_pool_string += constant_pool[i].toString();
        String interfaces_string = "";
        for(int i = 0; i < interfaces_count.get(); i++)
            interfaces_string += interfaces[i].toString();
        String fields_string = "";
        for(int i = 0; i < fields_count.get(); i++)
            fields_string += fields[i].toString();
        String methods_string = "";
        for(int i = 0; i < method_count.get(); i++) {
            methods_string += methods[i].toString();
        }
        String attributes_string = "";
        for(int i = 0; i < attributes_count.get(); i++)
            attributes_string += attributes[i].toString();
        return magic.toString()
                + minor_version.toString()
                + major_version.toString()
                + constant_pool_count.toString()
                + constant_pool_string
                + access_flags.toString()
                + this_class.toString()
                + super_class.toString()
                + interfaces_count.toString()
                + interfaces_string
                + fields_count.toString()
                + fields_string
                + method_count.toString()
                + methods_string
                + attributes_count.toString()
                + attributes_string;
    }

    public void readByteCodeFile(){
    	String byteCodeFilePath = "res/result.txt";
    	File file = new File(byteCodeFilePath);
    	FileInputStream fis = null;
    	InputStreamReader isr = null;
    	BufferedReader br = null; 
    	try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		isr = new InputStreamReader(fis);
		br = new BufferedReader(isr);
		
		String str;
		try {
			while((str = br.readLine()) != null){
				if(!str.equals("")){
					byteCodeData.add(str);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void fill_constant_pool(){
    	int i = 0;
    	int j = 0;
    	while(!byteCodeData.get(i).equals("Constant pool:")){
    		i++;
    	}
    	i++;
    	while(!byteCodeData.get(i).equals("{")){
    		constant_pool[j] = new cp_info(byteCodeData.get(i));
    		i++;
    		j++;
    	}
    }

}