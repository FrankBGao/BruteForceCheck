package org.processmining.plugins.BruteForceCheck;

import java.util.HashMap;

import org.deckfour.uitopia.api.event.TaskListener;
import org.deckfour.xes.classification.XEventAndClassifier;
import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.classification.XEventLifeTransClassifier;
import org.deckfour.xes.classification.XEventNameClassifier;
import org.deckfour.xes.info.XLogInfo;
import org.deckfour.xes.info.impl.XLogInfoImpl;
import org.deckfour.xes.model.XLog;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;


public class BruteForceCheckPlugin {
    @Plugin(
            name = "Brute Force Check",
            parameterLabels = {"Log"},
            returnLabels = {"Brute Force Check Result Petri Net"},
            returnTypes = {Petrinet.class},
            userAccessible = true,
            help = "produce places by brute force method. Check, remove and generate Petri Net"
    )
    @UITopiaVariant(
            affiliation = "RWTH-Aachen",
            author = "Junxiong Gao",
            email = "jx.gao@pads.rwth-aachen.de"
    )


    public static Petrinet run(UIPluginContext context, XLog log) {
        XEventClassifier defaultClassifier = null;

        if (log.getClassifiers().isEmpty()) {
            XEventClassifier nameCl = new XEventNameClassifier();
            XEventClassifier lifeTransCl = new XEventLifeTransClassifier();
            defaultClassifier = new XEventAndClassifier(nameCl, lifeTransCl);
        } else {
            defaultClassifier = log.getClassifiers().get(0);
        }
        XLogInfo loginfo = new XLogInfoImpl(log, defaultClassifier, log.getClassifiers());

        // gain parameter from interface
        ParametersPanel parameters = new ParametersPanel();
        parameters.removeAndThreshold();

        TaskListener.InteractionResult result = context.showConfiguration("Brute Force Check Parameters", parameters);
        if (result.equals(TaskListener.InteractionResult.CANCEL)) {
            context.getFutureResult(0).cancel(true);
        }
        HashMap<String,Object> parametersSettings = parameters.getSettings();

        return BruteForceCheckAlgorithm.bfcAlgorithm(loginfo, log, parametersSettings);
    }
}
