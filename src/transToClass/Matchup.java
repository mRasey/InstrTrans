package transToClass;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static op.globalArguments.instrSizes;
import static op.globalArguments.instrToHex;

public class Matchup {
    ArrayList<String> codes = new ArrayList<>();
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
        this.codes = codes;
    }

    public String doTrans() {
        for(int i = 0; i < codes.size(); i++) {
            String code = codes.get(i);
            String instrName = code.split(" ")[0];
            int instrSize = instrSizes.get(instrName);
            if(instrName.contains("switch")) {
                result += instrToHex.get(instrName);

            }
            else if(instrSize == 1)
                result += instrToHex.get(instrName);
            else {
                result += instrToHex.get(instrName);
                result += code.split(" ")[1].substring(2);// 去掉0x
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
//        new Matchup();
    }
}
