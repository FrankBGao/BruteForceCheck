package org.processmining.plugins.BruteForceCheck;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

import org.deckfour.xes.info.XLogInfo;
import org.deckfour.xes.model.XLog;

import org.processmining.models.graphbased.directed.petrinet.Petrinet;



public class BruteForceCheckAlgorithm {

    public static Petrinet bfcAlgorithm(XLogInfo loginfo, XLog log) {
        HashMap eventFrequency = UtilMethods.CountEventFrequency(loginfo, log);

        //generate the combination set of activities
        //ArrayList<HashSet> combination_set = Combination.GainAllPlace(new ArrayList<Object>(eventFrequency.keySet())); // too many combination
        ArrayList<Object> activities = new ArrayList<Object>(eventFrequency.keySet());
        activities.add("A_Start");
        activities.add("A_End");

        ArrayList<HashSet<Object>> combination_set = Combination.GainNumPlace(activities, 1);
        ArrayList<BFCPlace> combination_BFC_place = new ArrayList<>();
        System.out.println(combination_set.size());

        // create place for every combination
        for (HashSet<Object> input_set : combination_set) {
            for (HashSet<Object> output_set : combination_set) {

                BFCPlace BFCPlace = new BFCPlace(input_set, output_set);
                combination_BFC_place.add(BFCPlace);

            }
        }

        // transfer XLog into ArrayList for checking, and add start, end event into Log
        ArrayList<ArrayList<Object>> plain_log = UtilMethods.TransferLogArrayList(log);
        // initial the checking object with threshold
        //CheckPlace check_place = new CheckPlace(0.8f,0.2f,0.2f,0.2f, plain_log.size(),plain_log);
        CheckPlace check_place = new CheckPlace(0.5f,0.5f,0.5f,0.5f, plain_log.size(),plain_log);

        // gain all remaining BFC place
        ArrayList<BFCPlace> new_BFC_place = new ArrayList<>();
        for(BFCPlace BFCPlace : combination_BFC_place){
            check_place.CheckPlaceRun(BFCPlace);
            if(BFCPlace.GainState().equals("remain")){
                new_BFC_place.add(BFCPlace);
            }
        }


        // generate Petri Net and return
        return GeneratePN.GainPetriNet(new_BFC_place);
    }


}


//        HashSet<Object> a = new HashSet<>();
//        a.add("A_End");
//        HashSet<Object> b = new HashSet<>();
//        b.add("A_Start");

//                if(!BFCPlace.GainInputSet().equals(b) && !BFCPlace.GainOutputSet().equals(a)){
//                    System.out.println(BFCPlace.GainInputSet());
//                    System.out.println(BFCPlace.GainOutputSet());
//                    System.out.println("---------");
//                    new_BF_place.add(BFCPlace);
//                }