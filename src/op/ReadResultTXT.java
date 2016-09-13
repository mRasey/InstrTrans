package op;

import transToClass.Matchup;

import java.io.*;
import java.security.Key;
import java.util.HashMap;

public class ReadResultTXT {

    public ReadResultTXT() {

    }

    public ReadResultTXT readAndBuildLineTable() throws IOException {
        File file = new File("res/result.txt");
        BufferedReader bfr = new BufferedReader(new FileReader(file));
        String readIn = bfr.readLine();
        while(readIn != null) {
            if(readIn.startsWith(".method")) {
                readIn = bfr.readLine();
                HashMap<Integer, Integer> lineNumberTable = new HashMap<>();
                while(readIn != null && !readIn.contains(".end method")) {
                    if(readIn.contains(".line")) {
                        int lineNumber = Integer.parseInt(readIn.split(" ")[1]);// 获取line号
                        do {
                            readIn = bfr.readLine();
                        } while (readIn.startsWith(".local"));// 跳过之前的.local
                        int byteCodeNumber = Integer.parseInt(readIn.split(" ")[0].substring(0, readIn.split(" ")[0].lastIndexOf(":")));
                        lineNumberTable.put(lineNumber, byteCodeNumber);
                    }
                    readIn = bfr.readLine();
                }
                globalArguments.lineNumberTables.add(lineNumberTable);
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
}
