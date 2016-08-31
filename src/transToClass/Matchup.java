package transToClass;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static op.globalArguments.instrSizes;
import static op.globalArguments.instrToHex;
import static op.globalArguments.inter_name;

public class Matchup {
    String result = "";

    public Matchup(ArrayList<String> codes) throws IOException {
        File file = new File("res/Infos.txt");
        BufferedReader bfr = new BufferedReader(new FileReader(file));
        String readIn = bfr.readLine();
        while(readIn != null) {
            String[] strings = readIn.split(" ");
            instrToHex.put(strings[0], strings[1]);
            readIn = bfr.readLine();
        }
        for(String string : instrToHex.keySet()) {
            System.out.println(string + " " + instrToHex.get(string));
        }
    }

    /**
     * 判断字符串是否是指令
     * @param code 字符串
     * @return boolean
     */
    public boolean isInstr(String code) {
        if('a' <= code.charAt(0) && code.charAt(0) < 'z')
            return true;
        return false;
    }

    /**
     * 将字节码翻译成16进制码
     * @return
     */
    public String doTrans(ArrayList<String> codes) {
        for(int i = 0; i < codes.size(); i++) {
            String code = codes.get(i);
            if(isInstr(code)) {
                String instrName = code.split(" ")[0];
                int instrSize = instrSizes.get(instrName);
                if (instrName.contains("switch")) {
                    if(instrName.equals("tableswitch")) {
                        result += instrToHex.get(instrName);
                    }
                    else if(instrName.equals("lookupswitch")) {
                        result += instrToHex.get(instrName);
                    }
                } else if (instrSize == 1)
                    result += instrToHex.get(instrName);
                else {
                    result += instrToHex.get(instrName);
                    result += code.split(" ")[1].substring(2);// 去掉0x
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
//        new Matchup();
    }
}
