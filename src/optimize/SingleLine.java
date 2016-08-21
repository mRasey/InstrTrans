package optimize;

import java.util.ArrayList;

public class SingleLine {

    ArrayList<String> byteCodes = new ArrayList<>();

    public SingleLine() {

    }

    public void addByteCode(String byteCode) {
        byteCodes.add(byteCode);
    }

    public SingleLine print() {
        for(String code : byteCodes) {
            System.out.println(code);
        }
        return this;
    }
}
