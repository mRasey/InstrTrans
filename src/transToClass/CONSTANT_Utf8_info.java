package transToClass;

/**
 * utf8结构
 */
public class CONSTANT_Utf8_info {
    u1 tag = new u1();
    u2 length = new u2();
    u1[] bytes = new u1[length.get()];
}
