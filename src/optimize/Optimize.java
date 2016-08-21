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

    /**
     * 初始化分配指令
     * @return
     */
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

    /**
     * 输出结果
     * @return
     */
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

    /**
     * 处理方法
     * @return
     */
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
            dealSingleLine(singleMethod.lines.get(i), i);//删除多余指令
            simplifySingleLine(singleMethod.lines.get(i));//简化指令形式
        }
        return this;
    }

    /**
     * 删除多余指令
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

    /**
     * 简化指令形式，将load 1变成load_1
     * @param singleLine 单独一行
     * @return this
     */
    public Optimize simplifySingleLine(SingleLine singleLine) {
        ArrayList<String> byteCodes = singleLine.byteCodes;
        for(int i = 0; i < byteCodes.size(); i++) {
            String code = byteCodes.get(i);
            if(code.split(" ").length == 3 && code.split(" ")[2].length() == 1) {
                int stackNum = Integer.parseInt(code.split(" ")[2]);
                String op = code.split(" ")[1];
                if (0 <= stackNum && stackNum <= 3 && (op.substring(1).equals("load") || op.substring(1).equals("store")))
                    code = code.substring(0, code.lastIndexOf(" ")) + "_" + stackNum;
                else if (0 <= stackNum && stackNum <= 5 && op.equals("iconst"))
                    code = code.substring(0, code.lastIndexOf(" ")) + "_" + stackNum;
                else if (0 <= stackNum && stackNum <= 2 && op.equals("fconst"))
                    code = code.substring(0, code.lastIndexOf(" ")) + "_" + stackNum;
                else if (0 <= stackNum && stackNum <= 1 && op.substring(1).equals("const"))
                    code = code.substring(0, code.lastIndexOf(" ")) + "_" + stackNum;
                byteCodes.set(i, code);
            }
        }
        return this;
    }

    public static void main(String[] args) throws IOException {
        new Optimize().dispatchCodes().deal().output();
    }
}
