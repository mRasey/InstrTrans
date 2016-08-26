package transToClass;

public class field_info {
    u2 access_flags = new u2();
    u2 name_index = new u2();
    u2 descriptor = new u2();
    u2 attributes_count = new u2();
    attribute_info[] attributes = new attribute_info[attributes_count.get() - 1];
}
