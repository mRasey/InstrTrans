package op;

import optimize.CompleteData;
import optimize.Optimize;
import optimize.ReplaceLdc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class globalArguments {
    public static RegisterQueue registerQueue = new RegisterQueue();
    static String smailFilePath = "res/MainActivity.smali";
    public static ReadFile rf = new ReadFile(smailFilePath);
    public static int LineNumber = 0;   //编号
    
    public static Output ot = new Output();
    public static TranslateTable tt = new TranslateTable();
    public static Optimize op = new Optimize();
    public static ConstantPool cp = new ConstantPool();
    public static ReplaceLdc rl = new ReplaceLdc();
    public static CompleteData cd = new CompleteData();


    public static String className = "";
    public static String superClassName = "";
    public static ArrayList<String> classProPerty = new ArrayList<>();
    public static String methodName = "";											

    //记录翻译得到的代码
    public static ArrayList<String> finalByteCode = new ArrayList<>();
    public static int finalByteCodePC = 0;
    //优化后的代码
    public static ArrayList<String> optimizedByteCode = new ArrayList<>();
    public static int optimizedByteCodePC = 0;
    //标签转化后的代码
    public static ArrayList<String> traTabByteCode = new ArrayList<>();
    public static int traTabByteCodePC = 0;

    //linenember -> dex code number
    public static Map <Integer,Integer> lineToNumber = new HashMap<>();

    //遇到method清零
    public static int stackNumber = 0;
    public static int dexCodeNumber = 0; //dex指令编号

    /*		数组相关变量																	*/
    //记录数组标签和他后面的数据
    public static Map <String,ArrayList<String>> arrayData = new HashMap<>();

    //记录switch标签和后面的数据
    public static HashMap<String, ArrayList<SwitchData>> switchData = new HashMap<>();
    //default编号
    public static int switchDefaultIndex = 0;
    
    
    //常量池
    public static Map<Integer, String> const_id_type = new HashMap<>();
	public static Map<Integer, String> const_id_value = new HashMap<>();
    public static int const_id = 1;
    
    
    //接口信息
    public static int inter_count = 0;
    public static ArrayList<String> inter_name = new ArrayList<String>();
    public static ArrayList<Integer> inter_conpool_number = new ArrayList<Integer>();
    //字段信息
    public static int field_count = 0;
    // .field (访问权限) (static) (修饰关键字) 字段名:字段类型/描述符
    public static ArrayList<ArrayList<String>> field_info = new ArrayList<ArrayList<String>>(); 
    public static ArrayList<Integer> fieldName_conpool_number = new ArrayList<Integer>();
    public static ArrayList<Integer> fieldType_conpool_number = new ArrayList<Integer>();
    //方法信息
    public static int method_count = 0;
    public static ArrayList<String> method_name = new ArrayList<String>();
    public static ArrayList<String> method_type = new ArrayList<String>(); //参数和返回值
    public static ArrayList<Integer> method_reg_number = new ArrayList<Integer>();
    public static ArrayList<Integer> methodName_conpool_number = new ArrayList<Integer>();
    public static ArrayList<Integer> methodType_conpool_number = new ArrayList<Integer>();
    
    public static void clear(){
    	//清除寄存器信息
    	stackNumber = 0;
		registerQueue.clear();
		registerQueue.addNewRegister(new Register("p0", "this", globalArguments.stackNumber++));
		//清除数组信息
		arrayData.clear();
		
    }
		
}
