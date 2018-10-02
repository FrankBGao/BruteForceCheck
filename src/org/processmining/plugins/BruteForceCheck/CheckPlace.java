package org.processmining.plugins.BruteForceCheck;

import java.util.ArrayList;

public class CheckPlace {
    private ArrayList<ArrayList<Object>> WholeLog;
    private float Fitness_Bound;
    private float Overfed_Bound;
    private float Underfed_Bound;
    private float Inactivate_Bound;
    private int Log_Size;

    public CheckPlace(float fitness, float overfed, float underfed, float inactivate, int log_size, ArrayList<ArrayList<Object>> log) {
        Log_Size = log_size;
        WholeLog = log;
        Fitness_Bound = fitness * Log_Size;
        Overfed_Bound = overfed * Log_Size;
        Underfed_Bound = underfed * Log_Size;
        Inactivate_Bound = inactivate * Log_Size;
    }

    public BFCPlace CheckPlaceRun(BFCPlace BFCPlace) {

        for (ArrayList<Object> trace : WholeLog) {
            BFCPlace.CheckLog(trace);
            boolean need_next = BFCPlace.GainCheckResultKey("underfed") < Underfed_Bound &&
                                BFCPlace.GainCheckResultKey("overfed") < Overfed_Bound &&
                                BFCPlace.GainCheckResultKey("inactivate") < Inactivate_Bound;
            if (!need_next) {
                BFCPlace.UpdataState("remove");
                return BFCPlace;
            }
        }
        BFCPlace.UpdataState("remain");
        BFCPlace.UpdateCheckResultAllRate(Log_Size);
        return BFCPlace;
    }

}
