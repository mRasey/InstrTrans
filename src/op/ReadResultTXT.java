package op;

import transToClass.Matchup;

import java.io.*;
import java.security.Key;
import java.util.HashMap;

public class ReadResultTXT {

    public ReadResultTXT() throws IOException {

    }

    public ReadResultTXT readAndBuildLineTable() throws IOException {
        File file = new File("res/result.txt");
        BufferedReader bfr = new BufferedReader(new FileReader(file));
        String readIn = bfr.readLine();
        while(readIn != null) {
            if(readIn.startsWith(".method")) {
                readIn = bfr.readLine();
                while(readIn != null) {
                    int lineNumber;
                    if(readIn.contains(".line")) {
                        HashMap<Integer, Integer> lineNumberTable = new HashMap<>();
                        lineNumber = Integer.parseInt(readIn.split(" ")[1]);// 获取line号
                        do {
                            readIn = bfr.readLine();
                        } while (readIn.startsWith(".local"));// 跳过之前的.local
                        int byteCodeNumber = Integer.parseInt(readIn.split(" ")[0].substring(0, readIn.split(" ")[0].lastIndexOf(":")));
                        lineNumberTable.put(lineNumber, byteCodeNumber);
                        globalArguments.lineNumberTables.add(lineNumberTable);
                    }
                    readIn = bfr.readLine();
                }
            }
            else {
                readIn = bfr.readLine();
            }
        }
        return this;
    }

    public String lineNumberTableToString(HashMap<Integer, Integer> lineNumberTable) {
        String result = "";
        int size = lineNumberTable.size();
        result += Matchup.getHexN(size, 4);
        for(int lineNumber : lineNumberTable.keySet()) {
            result = result + Matchup.getHexN(lineNumber, 4) + Matchup.getHexN(lineNumberTable.get(lineNumber), 4);
        }
        return result;
    }

    public static String test() {
        String s = "123:";
        return s.substring(0, s.lastIndexOf(":"));
    }

    public static void main(String[] args) {
        System.out.println(ReadResultTXT.test());
    }
}
