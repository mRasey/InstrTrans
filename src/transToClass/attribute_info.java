package transToClass;

public class attribute_info {
    u2 attribute_name_index = new u2();
    u4 attribute_length = new u4();
    u1[] info = new u1[attribute_length.get() - 1];
}
