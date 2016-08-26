package transToClass;

import java.util.ArrayList;

/**
 * 常量池
 */
public class cp_info {
    u1 tag = new u1();
    ArrayList<u1> info = new ArrayList<>();

    @Override
    public String toString() {
        String info_string = "";
        for(u1 u1 : info)
            info_string += u1.toString();
        return tag.toString()
                + info_string;
    }
}
