package org.processmining.plugins.BruteForceCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.processmining.models.graphbased.directed.petrinet.Petrinet;
import org.processmining.models.graphbased.directed.petrinet.elements.Place;
import org.processmining.models.graphbased.directed.petrinet.elements.Transition;
import org.processmining.models.graphbased.directed.petrinet.impl.PetrinetFactory;

public class GeneratePN {
    private ArrayList<BFCPlace> bfc_place;
    private Petrinet net = PetrinetFactory.newPetrinet("Petrinet mined by BFC_Miner");
    private ArrayList<HashMap<String, Object>> placeDict = new ArrayList<>();
    private HashSet<Object> transitionCollection = new HashSet<>();
    private HashMap<String, Transition> transitionDict = new HashMap<>();

    private GeneratePN(ArrayList<BFCPlace> bfcPlace) {
        bfc_place = bfcPlace;
    }

    private void AddPlace() {
        for (BFCPlace inter_bfc_place : bfc_place) {
            String place_name = inter_bfc_place.GainPlaceName();

            Place p = net.addPlace(place_name);

            HashMap<String, Object> inter_place_hashmap = new HashMap<>();
            inter_place_hashmap.put("name", place_name);
            inter_place_hashmap.put("Place", p);
            inter_place_hashmap.put("BFCPlace", inter_bfc_place);

            placeDict.add(inter_place_hashmap);

            transitionCollection.addAll(inter_bfc_place.GainInputSet());
            transitionCollection.addAll(inter_bfc_place.GainOutputSet());
        }
    }

    private void AddTransition() {
        for (Object i : transitionCollection) {
            Transition transition = net.addTransition(i.toString());
            transitionDict.put(i.toString(), transition);
        }
    }

    private void AddArc() {
        for (HashMap<String, Object> inter_place_dict : placeDict) {
            BFCPlace AddArc_BFCPlace = (BFCPlace) inter_place_dict.get("BFCPlace");
            Place AddArc_Place = (Place) inter_place_dict.get("Place");

            // input set
            for (Object i : AddArc_BFCPlace.GainInputSet()) {
                net.addArc(transitionDict.get(i.toString()), AddArc_Place, AddArc_BFCPlace.GainCheckResultKey("fit"));
            }

            // output set
            for (Object i : AddArc_BFCPlace.GainOutputSet()) {
                net.addArc(AddArc_Place, transitionDict.get(i.toString()), AddArc_BFCPlace.GainCheckResultKey("fit"));
            }

        }

    }

    private Petrinet GainNet() {return net;}

    public static Petrinet GainPetriNet(ArrayList<BFCPlace> bfcPlace) {
        GeneratePN generator = new GeneratePN(bfcPlace);
        generator.AddPlace();
        generator.AddTransition();
        generator.AddArc();
        return generator.GainNet();
    }

}
