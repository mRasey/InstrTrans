package transToClass;

public class field_info {
    u2 access_flags = new u2();
    u2 name_index = new u2();
    u2 descriptor = new u2();
    u2 attributes_count = new u2();
    attribute_info[] attributes = new attribute_info[attributes_count.get() - 1];

    @Override
    public String toString() {
        String attributes_string = "";
        for(int i = 0; i < attributes_count.get(); i++)
            attributes_string += attributes[i].toString();
        return access_flags.toString()
                + name_index.toString()
                + descriptor.toString()
                + attributes_count.toString()
                + attributes_string;
    }
}
