package org.processmining.plugins.BruteForceCheck;

import java.util.HashMap;
import java.util.HashSet;

public class TrySomeThings {
    public static void main(String[] args) {
        int a = 1;
        float b = 1.0f;

        System.out.println(a/b);

        HashMap< String, Integer> c = new HashMap<>();
        c.put("a",1);
        System.out.println(c.get("a")/b);

        boolean d = true;
        System.out.println(!d);

        HashSet<String> e = new HashSet<>();
        e.add("a");
        e.add("a");
        e.add("b");
        e.add("a");
        for(String i: e){
            System.out.println(i);
        }
    }
}
