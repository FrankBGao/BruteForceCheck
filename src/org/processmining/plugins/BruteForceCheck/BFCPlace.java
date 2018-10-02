package org.processmining.plugins.BruteForceCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BFCPlace {
    private HashSet<Object> InputSet;
    private HashSet<Object> OutputSet;

    private HashMap<Object, Integer> CheckResult = new HashMap<>();
    private HashMap<Object, Float> CheckResultRate = new HashMap<>();

    private String state = "";

    public BFCPlace(HashSet<Object> inputSet, HashSet<Object> outputSet) {
        InputSet = inputSet;
        OutputSet = outputSet;

        CheckResult.put("fit", 0);
        CheckResult.put("overfed", 0);
        CheckResult.put("underfed", 0);
        CheckResult.put("inactivate", 0);

        CheckResultRate.put("fit_rate", 0f);
        CheckResultRate.put("overfed_rate", 0f);
        CheckResultRate.put("underfed_rate", 0f);
        CheckResultRate.put("inactivate_rate", 0f);
    }

    // gain info
    public HashSet<Object> GainInputSet() {
        return InputSet;
    }

    public HashSet<Object> GainOutputSet() {
        return OutputSet;
    }

    public String GainState() {
        return state;
    }

    public int GainCheckResultKey(Object key) {
        return CheckResult.get(key);
    }

    public float GainCheckResultKeyRate(Object key, int LogSize) {
        return CheckResult.get(key) / (float) LogSize;
    }

    public String GainPlaceName() {
        String input_name = "";
        for(Object i: InputSet){
            input_name = input_name.concat("+").concat(i.toString());
        }
        input_name = input_name.substring(1);

        String output_name = "";
        for(Object i: OutputSet){
            output_name = output_name.concat("+").concat(i.toString());
        }
        output_name = output_name.substring(1);

        return input_name.concat(",").concat(output_name);
    }

    public HashMap<Object, Integer> GainCheckResult() {
        return CheckResult;
    }

    public HashMap<Object, Float> GainCheckResultRate() {
        return CheckResultRate;
    }

    // Update
    private void UpdateCheckResult(Object key) {
        int inter = CheckResult.get(key) + 1;
        CheckResult.put(key, inter);
    }

    public HashMap<Object, Float> UpdateCheckResultAllRate(int LogSize) {
        for (Object key : CheckResult.keySet()) {
            CheckResultRate.put(key+"_rate", CheckResult.get(key) / (float) LogSize);
        }
        return CheckResultRate;
    }

    public String UpdataState(String new_state) {
        state = new_state;
        return state;
    }

    // main algorithm
    public Object CheckLog(ArrayList<Object> Log) {
        int token = 0;
        boolean token_move = false;

        for (Object activity : Log
        ) {
            if (InputSet.contains(activity)) {
                token += 1;
                continue;
            }

            if (OutputSet.contains(activity)) {
                if (token <= 0) {
                    UpdateCheckResult("underfed");
                    return "underfed";
                }
                token = token - 1;
                token_move = true;
            }

        }

        if (token_move){
            if (token > 0) {
                UpdateCheckResult("overfed");
                return "overfed";
            } else if (token == 0) {
                UpdateCheckResult("fit");
                return "fit";
            }
        }
        UpdateCheckResult("inactivate");
        return "inactivate";

    }

    public static void main(Object[] args) {
        HashSet<Object> InputSetTest = new HashSet<>();
        HashSet<Object> OutputSetTest = new HashSet<>();
        ArrayList<Object> LogTest = new ArrayList<>();


        InputSetTest.add("a");
        InputSetTest.add("b");

        OutputSetTest.add("c");
        OutputSetTest.add("d");
        //OutputSetTest.add("e");

        LogTest.add("a");
        LogTest.add("b");
        LogTest.add("c");
        LogTest.add("d");
//        LogTest.add("a");
//        LogTest.add("b");

        BFCPlace BFCPlace = new BFCPlace(InputSetTest, OutputSetTest);
        System.out.println(BFCPlace.CheckLog(LogTest));
        System.out.println(BFCPlace.CheckResult);

    }
}














