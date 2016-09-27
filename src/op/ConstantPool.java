
package op;

import java.util.ArrayList;
import java.util.Map;
import op.globalArguments;

public class ConstantPool {
    public int insNum = 0;
    String regex = "\\d+:";
    String number = "";

    public ConstantPool() {
    }

    public void strConstPool() {
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Class");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + (globalArguments.const_id + 1));
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), globalArguments.className.substring(1, globalArguments.className.length() - 1));
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Class");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + (globalArguments.const_id + 1));
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), globalArguments.superClassName.substring(1, globalArguments.superClassName.length() - 1));
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "Code");
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "LineNumberTable");
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "LocalVariableTable");
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "this");
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), globalArguments.className);
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "SourceFile");
        ++globalArguments.const_id;
        globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
        globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), globalArguments.sourceFile);
        ++globalArguments.const_id;
        this._interface();
        this._fieldName();
        this._fieldType();
        this._methodName();
        this._methodType();
        this._localNameAndType();

        for(; this.insNum < globalArguments.traTabByteCodePC; ++this.insNum) {
            String code = (String)globalArguments.traTabByteCode.get(this.insNum);
            String[] byteCodes = code.split(" ");
            this.number = byteCodes[0];
            if(byteCodes[0].matches(this.regex) && globalArguments.rf.ifAnInstruction(byteCodes[1])) {
                if(byteCodes[1].contains("invoke")) {
                    globalArguments.traTabByteCode.set(this.insNum, this._invoke(byteCodes));
                } else if(byteCodes[1].contains("field")) {
                    globalArguments.traTabByteCode.set(this.insNum, this._field(byteCodes));
                } else if(byteCodes[1].contains("ldc")) {
                    globalArguments.traTabByteCode.set(this.insNum, this._ldc(byteCodes));
                } else if(byteCodes[1].equals("new")) {
                    globalArguments.traTabByteCode.set(this.insNum, this._new(byteCodes));
                } else if(!byteCodes[1].equals("putstatic") && !byteCodes[1].equals("getstatic")) {
                    if(byteCodes[1].equals("anewarray")) {
                        globalArguments.traTabByteCode.set(this.insNum, this._anewarray(byteCodes));
                    } else if(byteCodes[1].equals("instanceof")) {
                        globalArguments.traTabByteCode.set(this.insNum, this._instanceof(byteCodes));
                    }
                } else {
                    globalArguments.traTabByteCode.set(this.insNum, this._pgstatic(byteCodes));
                }
            }
        }

    }

    public void _localNameAndType() {
        boolean i = false;
        boolean j = false;

        for(int var9 = 0; var9 < globalArguments.method_local.size(); ++var9) {
            ArrayList local = (ArrayList)globalArguments.method_local.get(var9);
            ArrayList local_name = new ArrayList();
            ArrayList local_type = new ArrayList();
            local_name.add(Integer.valueOf(8));
            local_type.add(Integer.valueOf(9));

            for(int var10 = 1; var10 < local.size(); ++var10) {
                String str = ((String)local.get(var10)).split(" ")[2];
                String name = str.substring(1, str.lastIndexOf(":") - 1);
                String type = str.substring(str.indexOf(":") + 1);
                if(globalArguments.const_id_value.containsValue(name) && ((String)globalArguments.const_id_type.get(Integer.valueOf(this.getKey(globalArguments.const_id_value, name)))).equals("Utf8")) {
                    local_name.add(Integer.valueOf(this.getKey(globalArguments.const_id_value, name)));
                } else {
                    globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
                    globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), name);
                    local_name.add(Integer.valueOf(globalArguments.const_id));
                    ++globalArguments.const_id;
                }

                if(globalArguments.const_id_value.containsValue(type) && ((String)globalArguments.const_id_type.get(Integer.valueOf(this.getKey(globalArguments.const_id_value, type)))).equals("Utf8")) {
                    local_type.add(Integer.valueOf(this.getKey(globalArguments.const_id_value, type)));
                } else {
                    globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
                    globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), type);
                    local_type.add(Integer.valueOf(globalArguments.const_id));
                    ++globalArguments.const_id;
                }
            }

            globalArguments.local_name_index.add(local_name);
            globalArguments.local_type_index.add(local_type);
        }

    }

    public void _methodName() {
        boolean i = false;

        for(int var4 = 0; var4 < globalArguments.method_count; ++var4) {
            ArrayList inf = (ArrayList)globalArguments.method_info.get(var4);
            String name = ((String)inf.get(inf.size() - 1)).split("\\(")[0];
            if(globalArguments.const_id_value.containsValue(name) && ((String)globalArguments.const_id_type.get(Integer.valueOf(this.getKey(globalArguments.const_id_value, name)))).equals("Utf8")) {
                globalArguments.methodName_conpool_number.add(Integer.valueOf(this.getKey(globalArguments.const_id_value, name)));
            } else {
                globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
                globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), name);
                globalArguments.methodName_conpool_number.add(Integer.valueOf(globalArguments.const_id));
                ++globalArguments.const_id;
            }
        }

    }

    public void _methodType() {
        boolean i = false;

        for(int var4 = 0; var4 < globalArguments.method_count; ++var4) {
            ArrayList inf = (ArrayList)globalArguments.method_info.get(var4);
            String type = "(" + ((String)inf.get(inf.size() - 1)).split("\\(")[1];
            if(globalArguments.const_id_value.containsValue(type) && ((String)globalArguments.const_id_type.get(Integer.valueOf(this.getKey(globalArguments.const_id_value, type)))).equals("Utf8")) {
                globalArguments.methodType_conpool_number.add(Integer.valueOf(this.getKey(globalArguments.const_id_value, type)));
            } else {
                globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
                globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), type);
                globalArguments.methodType_conpool_number.add(Integer.valueOf(globalArguments.const_id));
                ++globalArguments.const_id;
            }
        }

    }

    public void _interface() {
        boolean i = false;

        for(int var3 = 0; var3 < globalArguments.inter_count; ++var3) {
            String str = (String)globalArguments.inter_name.get(var3);
            if(str.startsWith("L")) {
                str = str.substring(1);
            }

            if(str.endsWith(";")) {
                str = str.substring(0, str.length() - 1);
            }

            if(globalArguments.const_id_value.containsValue(str) && ((String)globalArguments.const_id_type.get(Integer.valueOf(this.getKey(globalArguments.const_id_value, str)))).equals("Utf8")) {
                globalArguments.inter_conpool_number.add(Integer.valueOf(this.getKey(globalArguments.const_id_value, str) - 1));
            } else {
                globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Class");
                globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + (globalArguments.const_id + 1));
                globalArguments.inter_conpool_number.add(Integer.valueOf(globalArguments.const_id));
                ++globalArguments.const_id;
                globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
                globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), str);
                ++globalArguments.const_id;
            }
        }

    }

    public void _fieldName() {
        boolean i = false;
        String name = "";

        for(int var4 = 0; var4 < globalArguments.field_count; ++var4) {
            ArrayList inf = (ArrayList)globalArguments.field_info.get(var4);
            name = ((String)inf.get(inf.size() - 1)).split(":")[0];
            if(globalArguments.const_id_value.containsValue(name) && ((String)globalArguments.const_id_type.get(Integer.valueOf(this.getKey(globalArguments.const_id_value, name)))).equals("Utf8")) {
                globalArguments.fieldName_conpool_number.add(Integer.valueOf(this.getKey(globalArguments.const_id_value, name)));
            } else {
                globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
                globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), name);
                globalArguments.fieldName_conpool_number.add(Integer.valueOf(globalArguments.const_id));
                ++globalArguments.const_id;
            }
        }

    }

    public void _fieldType() {
        boolean i = false;
        String type = "";

        for(int var4 = 0; var4 < globalArguments.field_count; ++var4) {
            ArrayList inf = (ArrayList)globalArguments.field_info.get(var4);
            type = ((String)inf.get(inf.size() - 1)).split(":")[1];
            if(globalArguments.const_id_value.containsValue(type) && ((String)globalArguments.const_id_type.get(Integer.valueOf(this.getKey(globalArguments.const_id_value, type)))).equals("Utf8")) {
                globalArguments.fieldType_conpool_number.add(Integer.valueOf(this.getKey(globalArguments.const_id_value, type)));
            } else {
                globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
                globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), type);
                globalArguments.fieldType_conpool_number.add(Integer.valueOf(globalArguments.const_id));
                ++globalArguments.const_id;
            }
        }

    }

    public String _ldc(String[] byteCodes) {
        String newCode = "";
        String value = byteCodes[2].replace("\"", "");
        if(globalArguments.const_id_value.containsValue(value)) {
            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + this.getKey(globalArguments.const_id_value, value);
        } else {
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), value);
            ++globalArguments.const_id;
            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + (globalArguments.const_id - 1);
        }

        return newCode;
    }

    public String _invoke(String[] byteCodes) {
        boolean id_1 = false;
        boolean id_2 = false;
        boolean id_3 = false;
        boolean id_4 = false;
        String _Methodref = byteCodes[2];
        String _Class = _Methodref.split("\\.")[0];
        if(_Class.charAt(0) == 76) {
            _Class = _Class.substring(1);
        }

        _Class.replace(";", "");
        String _NameAndType = _Methodref.split("\\.")[1];
        String _name = _NameAndType.split(":")[0];
        String _type = _NameAndType.split(":")[1];
        String newCode = "";
        int var12;
        if(globalArguments.const_id_value.containsValue(_Class)) {
            var12 = this.getKey(globalArguments.const_id_value, _Class) - 1;
        } else {
            var12 = globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Class");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + (globalArguments.const_id + 1));
            ++globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), _Class);
            ++globalArguments.const_id;
        }

        int var14;
        if(globalArguments.const_id_value.containsValue(_name)) {
            var14 = this.getKey(globalArguments.const_id_value, _name);
        } else {
            var14 = globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), _name);
            ++globalArguments.const_id;
        }

        int var15;
        if(globalArguments.const_id_value.containsValue(_type)) {
            var15 = this.getKey(globalArguments.const_id_value, _type);
        } else {
            var15 = globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), _type);
            ++globalArguments.const_id;
        }

        int var13;
        if(globalArguments.const_id_value.containsValue("#" + var14 + ":" + "#" + var15)) {
            var13 = this.getKey(globalArguments.const_id_value, "#" + var14 + ":" + "#" + var15);
        } else {
            var13 = globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "NameAndType");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + var14 + ":" + "#" + var15);
            ++globalArguments.const_id;
        }

        if(globalArguments.const_id_value.containsValue("#" + var12 + "." + "#" + var13)) {
            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + this.getKey(globalArguments.const_id_value, "#" + var12 + "." + "#" + var13);
        } else {
            if(byteCodes[1].equals("invokeinterface")) {
                globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "InterfaceMethodref");
                globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + var12 + "." + "#" + var13);
                ++globalArguments.const_id;
            } else {
                globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Methodref");
                globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + var12 + "." + "#" + var13);
                ++globalArguments.const_id;
            }

            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + (globalArguments.const_id - 1);
        }

        return newCode;
    }

    public String _field(String[] byteCodes) {
        boolean id_1 = false;
        boolean id_2 = false;
        boolean id_3 = false;
        boolean id_4 = false;
        String _Fieldref = byteCodes[2];
        String _Class = _Fieldref.split(";->")[0];
        if(_Class.charAt(0) == 76) {
            _Class = _Class.substring(1);
        }

        _Class.replace(";", "");
        String _NameAndType = _Fieldref.split(";->")[1];
        String _name = _NameAndType.split(":")[0];
        String _type = _NameAndType.split(":")[1];
        String newCode = "";
        int var12;
        if(globalArguments.const_id_value.containsValue(_Class)) {
            var12 = this.getKey(globalArguments.const_id_value, _Class) - 1;
        } else {
            var12 = globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Class");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + (globalArguments.const_id + 1));
            ++globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), _Class);
            ++globalArguments.const_id;
        }

        int var14;
        if(globalArguments.const_id_value.containsValue(_name)) {
            var14 = this.getKey(globalArguments.const_id_value, _name);
        } else {
            var14 = globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), _name);
            ++globalArguments.const_id;
        }

        int var15;
        if(globalArguments.const_id_value.containsValue(_type)) {
            var15 = this.getKey(globalArguments.const_id_value, _type);
        } else {
            var15 = globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), _type);
            ++globalArguments.const_id;
        }

        int var13;
        if(globalArguments.const_id_value.containsValue("#" + var14 + ":" + "#" + var15)) {
            var13 = this.getKey(globalArguments.const_id_value, "#" + var14 + ":" + "#" + var15);
        } else {
            var13 = globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "NameAndType");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + var14 + ":" + "#" + var15);
            ++globalArguments.const_id;
        }

        if(globalArguments.const_id_value.containsValue("#" + var12 + "." + "#" + var13)) {
            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + this.getKey(globalArguments.const_id_value, "#" + var12 + "." + "#" + var13);
        } else {
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Fieldref");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + var12 + "." + "#" + var13);
            ++globalArguments.const_id;
            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + (globalArguments.const_id - 1);
        }

        return newCode;
    }

    public String _new(String[] byteCodes) {
        String _Class = byteCodes[2];
        if(_Class.charAt(0) == 76) {
            _Class = _Class.substring(1);
        }

        _Class.replace(";", "");
        String newCode = "";
        if(globalArguments.const_id_value.containsValue(_Class)) {
            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + (this.getKey(globalArguments.const_id_value, _Class) - 1);
        } else {
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Class");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + (globalArguments.const_id + 1));
            ++globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), _Class);
            ++globalArguments.const_id;
            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + (globalArguments.const_id - 2);
        }

        return newCode;
    }

    public String _instanceof(String[] byteCodes) {
        return this._new(byteCodes);
    }

    public String _pgstatic(String[] byteCodes) {
        return this._field(byteCodes);
    }

    public String _anewarray(String[] byteCodes) {
        String _Class = byteCodes[2];
        if(_Class.charAt(1) == 76) {
            _Class = _Class.substring(2);
            _Class.replace(";", "");
        } else if(_Class.charAt(1) == 91) {
            _Class = _Class.substring(1);
        }

        String newCode = "";
        if(globalArguments.const_id_value.containsValue(_Class)) {
            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + (this.getKey(globalArguments.const_id_value, _Class) - 1);
        } else {
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Class");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), "#" + (globalArguments.const_id + 1));
            ++globalArguments.const_id;
            globalArguments.const_id_type.put(Integer.valueOf(globalArguments.const_id), "Utf8");
            globalArguments.const_id_value.put(Integer.valueOf(globalArguments.const_id), _Class);
            ++globalArguments.const_id;
            newCode = byteCodes[0] + " " + byteCodes[1] + " " + "#" + (globalArguments.const_id - 2);
        }

        return newCode;
    }

    public int getKey(Map<Integer, String> id_type, String value) {
        for(int i = 1; i < globalArguments.const_id; ++i) {
            if(((String)id_type.get(Integer.valueOf(i))).equals(value)) {
                return i;
            }
        }

        return -1;
    }
}

