package transToClass;

public class LVT_attribute_info {
	    u2 attribute_name_index = new u2((short) 5);
	    u4 attribute_length = new u4();  //后面这些东西的总长
	    u2 attribute_count = new u2();
	    u2 [] table;
	    
	    int method_id = 0;
	    
	    public LVT_attribute_info(int method_id) {
	    	this.method_id = method_id;
	        
	    }

	    @Override
	    public String toString() {  
	        return attribute_name_index.toString()
	                + attribute_length.toString();
	               
	    }
}
