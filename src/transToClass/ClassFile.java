package transToClass;

public class ClassFile {
    u4 magic = new u4();// 魔数 0xCAFEBABE
    u2 minor_version = new u2();// class文件副版本号
    u2 major_version = new u2();// class文件主版本号
    u2 constant_pool_count = new u2();// 常量池计数器
    cp_info[] constant_pool = new cp_info[constant_pool_count.get() - 1];// 常量池
    u2 access_flags = new u2();// 访问标志
    u2 this_class = new u2();// 类索引
    u2 super_class = new u2();// 父类索引
    u2 interfaces_count = new u2();// 接口计数器
    u2[] interfaces = new u2[interfaces_count.get() - 1];// 接口表
    u2 fields_count = new u2();// 字段计数器
    field_info[] fields = new field_info[fields_count.get() - 1];// 字段表
    u2 method_count = new u2();// 方法计数器
    method_info[] methods = new method_info[method_count.get() - 1];// 方法表
    u2 attributes_count = new u2();// 属性计数器
    attribute_info[] attributes = new attribute_info[attributes_count.get() - 1];// 属性表

    public ClassFile() {

    }

    @Override
    public String toString() {
        String constant_pool_string = "";
        for(int i = 0; i < constant_pool_count.get(); i++)
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
}
