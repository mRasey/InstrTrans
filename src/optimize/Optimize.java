package optimize;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Optimize {

    static final String txtPath = "C:\\Users\\Billy\\Desktop\\test\\result.txt";
    ArrayList<String> byteCodes = new ArrayList<>();
    ArrayList<SingleMethod> singleMethods = new ArrayList<>();

    public Optimize() throws IOException {
        File file = new File(txtPath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String readIn = bufferedReader.readLine();
        while(readIn != null) {
            this.byteCodes.add(readIn);
            readIn = bufferedReader.readLine();
        }
    }

    public Optimize dispatchCodes() {
        String code;
        for(int i = 0; i < byteCodes.size(); i++) {
            code = byteCodes.get(i);
            if(code.contains(".method")) {
                SingleMethod singleMethod = new SingleMethod(code);
                for(++i; !code.contains(".end"); ) {
                    code = byteCodes.get(i);
                    if(code.contains(".line")) {
                        SingleLine singleLine = new SingleLine();
                        code = byteCodes.get(++i);
                        while(!code.contains(".line") && !code.contains(".end")) {
                            singleLine.addByteCode(code);
                            code = byteCodes.get(++i);
                        }
                        singleMethod.addNewLine(singleLine);
                    }
                }
                singleMethods.add(singleMethod);
            }
        }
        return this;
    }

    public Optimize output() {
        for(int methodIndex = 0; methodIndex < singleMethods.size(); methodIndex++) {
            SingleMethod singleMethod = singleMethods.get(methodIndex);
            System.out.println(singleMethod.methodName);
            for(int lineIndex = 0; lineIndex < singleMethod.lines.size(); lineIndex++) {
                System.out.println(".line");
                singleMethod.lines.get(lineIndex).print();
            }
            System.out.println(".end method");
        }
        return this;
    }

    public Optimize deal() {
        for(int i = 0; i < singleMethods.size(); i++) {
            dealSingleMethod(singleMethods.get(i));
        }
        return this;
    }

    /**
     * 处理单个方法
     * @param singleMethod 单独一个方法
     */
    public Optimize dealSingleMethod(SingleMethod singleMethod) {
        for(int i = 0; i < singleMethod.lines.size(); i++) {
            dealSingleLine(singleMethod.lines.get(i), i);
        }
        return this;
    }

    /**
     * 处理单行
     * @param singleLine 单独一个方法
     * @param lineNumber 行号
     */
    public Optimize dealSingleLine(SingleLine singleLine, int lineNumber) {
        HashMap<Integer, SingleRegister> stackNumToState = new HashMap<>();
        //同一个寄存器先出现store在出现load
        ArrayList<String> byteCodes = singleLine.byteCodes;
        for(int i = 0; i < byteCodes.size(); i++) {
            String code = byteCodes.get(i);
            if(code.contains("store")) {
                int stackNum = Integer.parseInt(code.split(" ")[2]);
                stackNumToState.put(stackNum, new SingleRegister(State.store, i));
            }
            else if(code.contains("load")) {
                int stackNum = Integer.parseInt(code.split(" ")[2]);
                SingleRegister singleRegister = stackNumToState.get(stackNum);
                if(singleRegister != null && singleRegister.state == State.store) {
                    byteCodes.set(singleRegister.index, "");
//                    System.err.println("index:" + singleRegister.index);
                    byteCodes.set(i, "");
//                    System.err.println("index:" + i);
                    stackNumToState.remove(stackNum);
                }
            }
        }
        Iterator<String> iterator = byteCodes.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().equals(""))
                iterator.remove();
        }
        return this;
    }

    public static void main(String[] args) throws IOException {
        new Optimize().dispatchCodes().deal().output();
    }
}
