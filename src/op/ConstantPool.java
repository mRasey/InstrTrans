package op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//push不放进常量池
public class ConstantPool {

	// 编号 #n -> 类型
	// 所有的东西都是先用Utf8存的，然后合并为具体的类型Class,NameAndType,Fieldref,Methodref,InterfaceMethodref,Long,Float,Double,String
	public int insNum = 0;

	public void strConstPool() {
		//前两个存类名和父类名
		globalArguments.const_id_type.put(globalArguments.const_id, "Class");
		globalArguments.const_id_value.put(globalArguments.const_id, globalArguments.className.substring(1, globalArguments.className.length()-1));
		globalArguments.const_id++;
		globalArguments.const_id_type.put(globalArguments.const_id, "Class");
		globalArguments.const_id_value.put(globalArguments.const_id, globalArguments.superClassName.substring(1, globalArguments.className.length()-1));
		globalArguments.const_id++;
		//存接口
		_interface();
		//存字段名
		_fieldName();
		//存字段类型
		_fieldType();
		//存方法名
		_methodName();
		
		String code;
		String[] byteCodes;
		for (; insNum < globalArguments.traTabByteCodePC; insNum++) {
			code = globalArguments.traTabByteCode.get(insNum);
			byteCodes = code.split(" ");
			if (globalArguments.rf.ifAnInstruction(byteCodes[0])) {
				if (byteCodes[0].contains("invoke")) {
					globalArguments.traTabByteCode.set(insNum, _invoke(byteCodes));
				}
				else if (byteCodes[0].contains("field")) {
					globalArguments.traTabByteCode.set(insNum, _field(byteCodes));
				}
				else if (byteCodes[0].contains("ldc")) {
					globalArguments.traTabByteCode.set(insNum, _ldc(byteCodes));
				}
				else if (byteCodes[0].equals("new")) {
					globalArguments.traTabByteCode.set(insNum, _new(byteCodes));
				}
				else if(byteCodes[0].equals("putstatic") || byteCodes[0].equals("getstatic")){
					globalArguments.traTabByteCode.set(insNum, _pgstatic(byteCodes));
				}
				else if(byteCodes[0].equals("anewarray")){
					globalArguments.traTabByteCode.set(insNum, _anewarray(byteCodes));
				}
			}
		}
	}

	
	public void _methodName(){
		int i = 0;
		for(i=0;i<globalArguments.method_count;i++){
			String str = globalArguments.method_name.get(i);
			globalArguments.const_id_type.put(globalArguments.const_id, "Utf8");
			globalArguments.const_id_value.put(globalArguments.const_id, str);
			globalArguments.methodName_conpool_number.add(globalArguments.const_id);
			globalArguments.const_id++;
		}
	}
	
	public void _methodType(){
		int i = 0;
		for(i=0;i<globalArguments.method_count;i++){
			String str = globalArguments.method_type.get(i);
			globalArguments.const_id_type.put(globalArguments.const_id, "Utf8");
			globalArguments.const_id_value.put(globalArguments.const_id, str);
			globalArguments.methodType_conpool_number.add(globalArguments.const_id);
			globalArguments.const_id++;
		}
	}
	
	public void _interface(){
		int i = 0;
		for(i=0;i<globalArguments.inter_count;i++){
			String str = globalArguments.inter_name.get(i);
			if(str.startsWith("L")){
				str = str.substring(1);
			}
			if(str.endsWith(";")){
				str = str.substring(0,str.length()-1);
			}
			globalArguments.const_id_type.put(globalArguments.const_id, "Class");
			globalArguments.const_id_value.put(globalArguments.const_id, str);
			globalArguments.inter_conpool_number.add(globalArguments.const_id);
			globalArguments.const_id++;
		}
	}
	
	public void _fieldName(){
		int i = 0;
		ArrayList<String> inf;
		String name = "";
		for(i=0;i<globalArguments.field_count;i++){
			inf = globalArguments.field_info.get(i);
			name = inf.get(inf.size()-1).split(":")[0];
			globalArguments.const_id_type.put(globalArguments.const_id, "Utf8");
			globalArguments.const_id_value.put(globalArguments.const_id, name);
			globalArguments.fieldName_conpool_number.add(globalArguments.const_id);
			globalArguments.const_id++;
		}
	}
	//字段类型得注意判断是否重复
	public void _fieldType(){
		int i = 0;
		ArrayList<String> inf;
		String type = "";
		for(i=0;i<globalArguments.field_count;i++){
			inf = globalArguments.field_info.get(i);
			type = inf.get(inf.size()-1).split(":")[1];
			if(globalArguments.const_id_value.containsValue(type)){
				globalArguments.fieldType_conpool_number.add(getKey(globalArguments.const_id_value, type));
			}
			else{
				globalArguments.const_id_type.put(globalArguments.const_id, "Utf8");
				globalArguments.const_id_value.put(globalArguments.const_id, type);
				globalArguments.fieldType_conpool_number.add(globalArguments.const_id);
				globalArguments.const_id++;
			}
		}
	}
	
	public String _ldc(String[] byteCodes){
		String newCode = new String();
		System.out.print(byteCodes[0]+" ");
		System.out.println(byteCodes[1]);
		String value = byteCodes[1].replace("\"", "");
		if(globalArguments.const_id_value.containsValue(value)){
			newCode = byteCodes[0]+" "+"#"+ getKey(globalArguments.const_id_value, value);
		}
		else{
			globalArguments.const_id_type.put(globalArguments.const_id, "Utf8");
			globalArguments.const_id_value.put(globalArguments.const_id, value);
			globalArguments.const_id++;
			
			newCode =  byteCodes[0]+" "+"#"+ (globalArguments.const_id-1);
		}
		return newCode;
	}
	public String _invoke(String[] byteCodes){
		int id_1=0,id_2=0,id_3=0,id_4=0;
		String _Methodref = byteCodes[1];
		String _Class = _Methodref.split("\\.")[0];
		String _NameAndType = _Methodref.split("\\.")[1];
		String _name = _NameAndType.split(":")[0];
		String _type = _NameAndType.split(":")[1];
		
		String newCode = new String();
		
		if(globalArguments.const_id_value.containsValue( _Class)){
			id_1 = getKey(globalArguments.const_id_value, _Class);
		}
		else{
			id_1 = globalArguments.const_id;
			globalArguments.const_id_type.put(globalArguments.const_id, "Class");
			globalArguments.const_id_value.put(globalArguments.const_id, _Class);
			globalArguments.const_id++;
		}
		
		if(globalArguments.const_id_value.containsValue( _name)){
			id_3 = getKey(globalArguments.const_id_value, _name);
		}
		else{
			id_3 = globalArguments.const_id;
			globalArguments.const_id_type.put(globalArguments.const_id, "Utf8");
			globalArguments.const_id_value.put(globalArguments.const_id, _name);
			globalArguments.const_id++;
		}
		
		if(globalArguments.const_id_value.containsValue( _type)){
			id_4 = getKey(globalArguments.const_id_value, _type);
		}
		else{
			id_4 = globalArguments.const_id;
			globalArguments.const_id_type.put(globalArguments.const_id, "Utf8");
			globalArguments.const_id_value.put(globalArguments.const_id, _type);
			globalArguments.const_id++;
		}
		
		if(globalArguments.const_id_value.containsValue("#"+id_3+":"+"#"+id_4)){
			id_2 = getKey(globalArguments.const_id_value, "#"+id_3+":"+"#"+id_4);
		}
		else{
			id_2 = globalArguments.const_id;
			globalArguments.const_id_type.put(globalArguments.const_id, "NameAndType");
			globalArguments.const_id_value.put(globalArguments.const_id, "#"+id_3+":"+"#"+id_4);
			globalArguments.const_id++;
		}
		
		if(globalArguments.const_id_value.containsValue("#"+id_1+"."+"#"+id_2)){
			newCode =  byteCodes[0]+" "+"#"+ getKey(globalArguments.const_id_value, "#"+id_1+"."+"#"+id_2);
		}
		else{
			if(byteCodes[0].equals("invokeinterface")){
				globalArguments.const_id_type.put(globalArguments.const_id, "InterfaceMethodref");
				globalArguments.const_id_value.put(globalArguments.const_id, "#"+id_1+"."+"#"+id_2);
				globalArguments.const_id++;
			}
			else{
				globalArguments.const_id_type.put(globalArguments.const_id, "Methodref");
				globalArguments.const_id_value.put(globalArguments.const_id, "#"+id_1+"."+"#"+id_2);
				globalArguments.const_id++;
			}
			
			newCode =  byteCodes[0]+" "+"#"+ (globalArguments.const_id-1);
		}
		return newCode;
	}
	
	public String _field(String[] byteCodes){
		int id_1=0,id_2=0,id_3=0,id_4=0;
		String _Fieldref = byteCodes[1];
		String _Class = _Fieldref.split(";->")[0];
		String _NameAndType = _Fieldref.split(";->")[1];
		String _name = _NameAndType.split(":")[0];
		String _type = _NameAndType.split(":")[1];
		String newCode = new String();
		
		
		if(globalArguments.const_id_value.containsValue( _Class)){
			id_1 = getKey(globalArguments.const_id_value, _Class);
		}
		else{
			id_1 = globalArguments.const_id;
			globalArguments.const_id_type.put(globalArguments.const_id, "Class");
			globalArguments.const_id_value.put(globalArguments.const_id, _Class);
			globalArguments.const_id++;
		}
		
		if(globalArguments.const_id_value.containsValue( _name)){
			id_3 = getKey(globalArguments.const_id_value, _name);
		}
		else{
			id_3 = globalArguments.const_id;
			globalArguments.const_id_type.put(globalArguments.const_id, "Utf8");
			globalArguments.const_id_value.put(globalArguments.const_id, _name);
			globalArguments.const_id++;
		}
		
		if(globalArguments.const_id_value.containsValue( _type)){
			id_4 = getKey(globalArguments.const_id_value, _type);
		}
		else{
			id_4 = globalArguments.const_id;
			globalArguments.const_id_type.put(globalArguments.const_id, "Utf8");
			globalArguments.const_id_value.put(globalArguments.const_id, _type);
			globalArguments.const_id++;
		}
		
		if(globalArguments.const_id_value.containsValue("#"+id_3+":"+"#"+id_4)){
			id_2 = getKey(globalArguments.const_id_value, "#"+id_3+":"+"#"+id_4);
		}
		else{
			id_2 = globalArguments.const_id;
			globalArguments.const_id_type.put(globalArguments.const_id, "NameAndType");
			globalArguments.const_id_value.put(globalArguments.const_id, "#"+id_3+":"+"#"+id_4);
			globalArguments.const_id++;
		}
		
		if(globalArguments.const_id_value.containsValue("#"+id_1+"."+"#"+id_2)){
			newCode =  byteCodes[0]+" "+"#"+ getKey(globalArguments.const_id_value, "#"+id_1+"."+"#"+id_2);
		}
		else{
			globalArguments.const_id_type.put(globalArguments.const_id, "Fieldref");
			globalArguments.const_id_value.put(globalArguments.const_id, "#"+id_1+"."+"#"+id_2);
			globalArguments.const_id++;
			newCode =  byteCodes[0]+" "+"#"+ (globalArguments.const_id-1);
		}
		return newCode;
	}
	
	public String _new(String[] byteCodes){
		String _Class = byteCodes[1];
		String newCode = new String();
		
		if(globalArguments.const_id_value.containsValue(_Class)){
			newCode =  byteCodes[0]+" "+"#"+ getKey(globalArguments.const_id_value, _Class);
		}
		else{
			globalArguments.const_id_type.put(globalArguments.const_id, "Class");
			globalArguments.const_id_value.put(globalArguments.const_id, _Class);
			globalArguments.const_id++;
			newCode =  byteCodes[0]+" "+"#"+ (globalArguments.const_id-1);
		}
		
		
		return newCode;
	}
	
	public String _pgstatic(String[] byteCodes){
		return _field(byteCodes);
	}
	
	public String _anewarray(String[] byteCodes){
		return _new(byteCodes);
	}
	
	public int getKey(Map<Integer,String> id_type, String value){
		int i = 1;
		for(;i<globalArguments.const_id;i++){
			if(id_type.get(i).equals(value)){
				return i;
			}
		}
		return -1;
	}
	
}
