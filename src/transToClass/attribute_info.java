package transToClass;

public class attribute_info {
    u2 attribute_name_index = new u2();
    u4 attribute_length = new u4();
    u1[] info = new u1[attribute_length.get() - 1];

    @Override
    public String toString() {
        String info_string = "";
        for(int i = 0; i < attribute_length.get(); i++)
            info_string += info[i].toString();
        return attribute_name_index.toString()
                + attribute_length.toString()
                + info_string;
    }
}
