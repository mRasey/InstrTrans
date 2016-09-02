package transToClass;

public class attribute_info {
    u2 attribute_name_index = new u2();
    u4 attribute_length = new u4();
    String info ;

    public attribute_info() {
    }

    public attribute_info(u2 attribute_name_index, u4 attribute_length, String info) {
        this.attribute_name_index = attribute_name_index;
        this.attribute_length = attribute_length;
        this.info = info;
    }

    @Override
    public String toString() {  
        return attribute_name_index.toString()
                + attribute_length.toString()
                + this.info;
    }
}
