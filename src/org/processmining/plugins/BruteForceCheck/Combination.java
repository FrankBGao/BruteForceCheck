package org.processmining.plugins.BruteForceCheck;

import java.util.ArrayList;
import java.util.HashSet;

// this algorithm refers combination function from Python's itertools package

public class Combination {
    private ArrayList<Object> active_list;
    private int combination_num;

    private Combination(ArrayList<Object> list,int num) {
        HashSet<Object> active_set = new HashSet<>(list);
        active_list = new ArrayList<>(active_set);
        combination_num = num;
    }

    private Boolean CheckNum(){
        return active_list.size() >= combination_num;
    }

    private ArrayList<HashSet<Object>> Transfer(ArrayList<HashSet> indices_result){
        ArrayList<HashSet<Object>> combination_result = new ArrayList<>();
        for (HashSet i: indices_result
             ) {
            ArrayList<Object>inter_set = new ArrayList<>();
            for (Object j: i
                 ) {
                inter_set.add(active_list.get((int)j));
            }
            combination_result.add(new HashSet<>(inter_set));
        }
        return combination_result;
    }

    private ArrayList<HashSet<Object>> GainCombination(){
        ArrayList<HashSet> indices_result = new ArrayList<>();

        int n = active_list.size();
        ArrayList<Integer> inter_indices = new ArrayList<>();
        for (int i = 0; i < combination_num; i++) {
            inter_indices.add(i);
        }
        indices_result.add(new HashSet<>(inter_indices));

        if(active_list.size() == combination_num){ return Transfer(indices_result);}

        while (true){
            int index = -1;
            for(int i = combination_num - 1; i >= 0; i--){
                if(inter_indices.get(i) != i + n - combination_num){
                    index = i;
                    break;
                }
            }
            if (index == -1){
                break;
            }
            inter_indices.set(index, inter_indices.get(index) + 1);

            for (int i = index + 1; i < combination_num; i++) {
                inter_indices.set(i, inter_indices.get(i-1) + 1);
            }
            indices_result.add(new HashSet<>(inter_indices));
        }

        return Transfer(indices_result);
    }

    public static ArrayList<HashSet> GainAllPlace(ArrayList<Object> InputSetTest){
        ArrayList<HashSet> result = new ArrayList<>();
        for (int i = 1; i < InputSetTest.size() + 1; i++) {
            Combination combination = new Combination(InputSetTest, i);
            if(combination.CheckNum()){
                ArrayList<HashSet<Object>> inter = combination.GainCombination();
                result.addAll(inter);
            }
        }
        return result;
    }

    public static ArrayList<HashSet<Object>> GainNumPlace(ArrayList<Object> InputSetTest, int num){
        Combination combination = new Combination(InputSetTest, num);
        if(combination.CheckNum()) {
            return combination.GainCombination();
        }
        return new ArrayList<>();
    }


    public static void main(String[] args) {
//        ArrayList<Object> InputSetTest = new ArrayList<>();
//
//        InputSetTest.add("a");
//        InputSetTest.add("b");
//        InputSetTest.add("c");
//        InputSetTest.add("d");
//        InputSetTest.add("e");

        ArrayList<Object> InputSetTest = new ArrayList<>();

        HashSet<Object> a = new HashSet<>();
        HashSet<Object> b = new HashSet<>();
        HashSet<Object> c = new HashSet<>();
        a.add("a");
        a.add("b");
        a.add("c");

        b.add("a");
        b.add("b");

        c.add("a");
        c.add("b");
        c.add("d");

        InputSetTest.add(a);
        InputSetTest.add(b);
        InputSetTest.add(c);

        ArrayList<HashSet<Object>> result = GainNumPlace(InputSetTest, 2);
        for (HashSet act: result
        ) {
            System.out.println(act);
        }


//        ArrayList<HashSet> result2 = GainAllPlace(InputSetTest);
//        for (HashSet act: result2
//        ) {
//            System.out.println(act);
//        }

    }
}
