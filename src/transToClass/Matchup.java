package transToClass;

import java.io.*;
import java.util.HashMap;

public class Matchup {
    HashMap<String, String> instrToHex = new HashMap<>();

    public Matchup() throws IOException {
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

    public static void main(String[] args) throws IOException {
        new Matchup();
    }
}
