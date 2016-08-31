package transToClass;

import optimize.Optimize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static op.globalArguments.*;

public class Matchup {
    private ArrayList<String> codes = new ArrayList<>();

    public Matchup() throws IOException {
        File file = new File("res/Infos.txt");
        BufferedReader bfr = new BufferedReader(new FileReader(file));
        String readIn = bfr.readLine();
        while(readIn != null) {
            String[] strings = readIn.split(" ");
            instrToHex.put(strings[0], strings[1]);
            readIn = bfr.readLine();
        }
        bfr.close();
//        for(String string : instrToHex.keySet())
//            System.out.println(string + " " + instrToHex.get(string));

        file = new File("res/test.txt");
        bfr = new BufferedReader(new FileReader(file));
        readIn = "";
        while(!readIn.equals("{")) {
            readIn = bfr.readLine();
        }
        readIn = bfr.readLine();
        do {
            codes.add(readIn);
            readIn = bfr.readLine();
        }while(!readIn.equals("}"));
        for(String s : codes)
            System.out.println(s);
    }

    /**
     * 判断字符串是否是指令
     * @param code 字符串
     * @return boolean
     */
    public boolean isInstr(String code) {
        if('a' <= code.charAt(0) && code.charAt(0) <= 'z')
            return true;
        return false;
    }

    /**
     * 将字节码翻译成16进制码
     * @return
     */
    public String singleMethodDoTrans(ArrayList<String> singleMethodCodes) {
        String result = "";
        for(int i = 1; i < singleMethodCodes.size(); i++) {
            String code = singleMethodCodes.get(i);
            if(isInstr(code.substring(code.indexOf(" ") + 1))) {
                String instrName = code.split(" ")[1];
//                System.err.println(instrName);
                int instrSize = instrSizes.get(instrName);
                if (instrName.contains("switch")) {
                    if(instrName.equals("tableswitch")) {
                        result += instrToHex.get(instrName);
                        ArrayList<String> tabs = new ArrayList<>();
                        String tab = "";
                        do {
                            tab = singleMethodCodes.get(++i);
                            tabs.add(tab);
                        }while (!tab.startsWith("default"));
                        String defaultHex = getHexN(Integer.parseInt(tabs.get(tabs.size() - 1).split(" ")[1]), 8);
                        String startHex = getHexN(Integer.parseInt(tabs.get(0).split(" ")[0]), 8);
                        String endHex = getHexN(Integer.parseInt(tabs.get(tabs.size() - 2).split(" ")[0]), 8);
                        result = result + defaultHex + startHex + endHex;
                        for(int index = 0; i < tabs.size() - 2; index++) {
                            result += getHexN(Integer.parseInt(tabs.get(index).split(" ")[1]), 8);
                        }
                    }
                    else if(instrName.equals("lookupswitch")) {
                        result += instrToHex.get(instrName);
                        ArrayList<String> tabs = new ArrayList<>();
                        String tab = "";
                        do {
                            tab = singleMethodCodes.get(++i);
                            tabs.add(tab);
                        }while(!tab.startsWith("default"));
                        String defaultHex = getHexN(Integer.parseInt(tabs.get(tabs.size() - 1).split(" ")[1]), 8);
                        String sumHex = getHexN(tabs.size() - 1, 8);
                        result = result + defaultHex + sumHex;
                        for(int index = 0; index < tabs.size() - 1; index++) {
                            result = result
                                    + getHexN(Integer.parseInt(tabs.get(i).split(" ")[0]), 8)
                                    + getHexN(Integer.parseInt(tabs.get(i).split(" ")[1]), 8);
                        }
                    }
                }
                else if (instrSize == 1)
                    result += instrToHex.get(instrName);
                else {
                    if(code.contains("#"))
                        result = result + instrToHex.get(instrName)
                                + getHexN(Integer.parseInt(code.split(" ")[2].substring(1)), (instrSize - 1) * 2);
                    else
                        result = result + getHexN(Integer.parseInt(code.split(" ")[2]), (instrSize - 1) * 2);
                }
            }
        }
        return result;
    }

    /**
     * 翻译整个方法区
     * @return 最终16进制码
     */
    public Matchup buildTransCode() {
        ArrayList<String> singleMethodCodes = new ArrayList<>();
        for(String code : codes) {
            if(code.startsWith(".end")) {
                method_codes.add(singleMethodDoTrans(singleMethodCodes));
                singleMethodCodes.clear();
            }
            else if(code.startsWith(".line") || code.startsWith(":"))
                continue;
            else {
                singleMethodCodes.add(code);
            }
        }
        return this;
    }

    /**
     * 将int型转换成N位16进制
     * @return
     */
    public String getHexN(int i, int n) {
        String result = Integer.toHexString(i);
        while(result.length() < n)
            result = "0" + result;
        return result;
    }

    public static void main(String[] args) throws IOException {
        new Optimize().initInstrSize();
        new Matchup().buildTransCode();
        System.out.println(method_codes.get(0));
    }
}
