package org.processmining.plugins.BruteForceCheck;

import org.deckfour.xes.classification.XEventClass;
import org.deckfour.xes.info.XLogInfo;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class UtilMethods {

    // count the occurrence of each events into a HashMap
    public static HashMap CountEventFrequency(XLogInfo logInfo, XLog log){
        HashMap<String, Integer> result = new HashMap<>();
        Collection<XEventClass> inter_classes = logInfo.getEventClasses().getClasses();
        for(XEventClass i: inter_classes){
            result.put(i.toString(),0);
        }

        for(XTrace trace: log){
            for(XEvent event: trace){
                String name = event.getAttributes().get("concept:name").toString()
                        + "+" + event.getAttributes().get("lifecycle:transition").toString();

                result.put(name, result.get(name) + 1);
            }
        }
        return result;
    }

    // transfer Xlog into a array list
    public static ArrayList<ArrayList<Object>> TransferLogArrayList(XLog log){
        ArrayList<ArrayList<Object>> result = new ArrayList<>();

        for(XTrace trace: log){
            ArrayList<Object> inter_log = new ArrayList<>();
            inter_log.add("A_Start");
            for(XEvent event: trace){
                String name = event.getAttributes().get("concept:name").toString()
                        + "+" + event.getAttributes().get("lifecycle:transition").toString();

                inter_log.add(name);
            }
            inter_log.add("A_End");
            result.add(inter_log);
        }
        return result;
    }
}
