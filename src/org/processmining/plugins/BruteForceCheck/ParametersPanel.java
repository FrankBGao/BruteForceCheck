package org.processmining.plugins.BruteForceCheck;

// This part is from heuristic miner


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.deckfour.xes.classification.XEventAndClassifier;
import org.deckfour.xes.classification.XEventAttributeClassifier;
import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.classification.XEventLifeTransClassifier;
import org.deckfour.xes.classification.XEventNameClassifier;

import com.fluxicon.slickerbox.components.NiceIntegerSlider;
import com.fluxicon.slickerbox.components.NiceSlider.Orientation;
import com.fluxicon.slickerbox.factory.SlickerDecorator;
import com.fluxicon.slickerbox.factory.SlickerFactory;

public class ParametersPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6804610998661748174L;

	private HashMap<String,Object> settings;


	private JPanel thresholdsPanel, BFCPanel;
	
	private JLabel thresholdTitle, BFCTitle;
	/*
	 * [HV] Label l9 added for Ticket #3037.
	 */
	private JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
	//private NiceIntegerSlider t1, t2, t3, t4, t5, t6;
	private NiceIntegerSlider t6;
	private JSlider Fitness, Overfed, Underfed;
	/*
	 * [HV] Check box c3 added for Ticket #3037.
	 */
	private JCheckBox c1, c2, c3;

    public ClassifiersPanel classifiersPanel;

	public ParametersPanel() {
	    // Add fake classifier for parameters panel post-view
        HashSet<XEventClassifier> set = new HashSet<XEventClassifier>();
        settings = new HashMap<>();
		XEventClassifier nameCl = new XEventNameClassifier();
        XEventClassifier lifeTransCl = new XEventLifeTransClassifier();
        XEventAttributeClassifier attrClass = new XEventAndClassifier(nameCl, lifeTransCl);
        set.add(attrClass);
        this.classifiersPanel = new ClassifiersPanel(set);
        this.init();
	}


    private float ReadValue(int x) {
        return x / 10000.0f;
    }

	
	private void init(){

		SlickerFactory factory = SlickerFactory.instance();
		SlickerDecorator decorator = SlickerDecorator.instance();
		
		this.thresholdsPanel = factory.createRoundedPanel(15, Color.gray);

		this.thresholdTitle = factory.createLabel("Thresholds");
		this.thresholdTitle.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
		this.thresholdTitle.setForeground(new Color(40,40,40));

		this.t6 = factory.createNiceIntegerSlider("", 0, 100, (int) (100), Orientation.HORIZONTAL);
		
		
		FlowLayout customLayout = new FlowLayout(FlowLayout.LEFT);
		customLayout.setHgap(2);
		int customPanelWidth = 405;
		JPanel FitnessPanel =  new JPanel(customLayout);
		FitnessPanel.setBackground(Color.gray);
		JPanel OverfedPanel =  new JPanel(customLayout);
		OverfedPanel.setBackground(Color.gray);
		JPanel UnderfedPanel =  new JPanel(customLayout);
		UnderfedPanel.setBackground(Color.gray);

		
		final JLabel FitnessLabel1 = new JLabel(ReadValue(8000)+"");
		FitnessLabel1.setPreferredSize(new Dimension(60, 20));
		final JLabel OverfedLabel1 = new JLabel(ReadValue(2000)+"");
		OverfedLabel1.setPreferredSize(new Dimension(60, 20));
		final JLabel UnderfedLabel1 = new JLabel(ReadValue(2000)+"");
		UnderfedLabel1.setPreferredSize(new Dimension(60, 20));

		Fitness = new JSlider(0, 10000, 8000);
		Fitness.setPreferredSize(new Dimension(customPanelWidth - 60, 20));
		Fitness.setBackground(Color.gray);
		Fitness.addChangeListener(new ChangeListener() {
		    public void stateChanged(ChangeEvent e) {
				String labelStr = String.valueOf(ReadValue(Fitness.getValue()));
				FitnessLabel1.setText(labelStr);
			}
		});
		Overfed = new JSlider(0, 10000, 2000);
		Overfed.setPreferredSize(new Dimension(customPanelWidth - 60, 20));
		Overfed.setBackground(Color.gray);
		Overfed.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				String labelStr = String.valueOf(ReadValue(Overfed.getValue()));
				OverfedLabel1.setText(labelStr);
			}
		});
		Underfed = new JSlider(0, 10000, 2000);
		Underfed.setPreferredSize(new Dimension(customPanelWidth - 60, 20));
		Underfed.setBackground(Color.gray);
		Underfed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String labelStr = String.valueOf(ReadValue(Underfed.getValue()));
				UnderfedLabel1.setText(labelStr);
			}
		});

		FitnessPanel.add(Fitness);
		FitnessPanel.add(FitnessLabel1);
		OverfedPanel.add(Overfed);
		OverfedPanel.add(OverfedLabel1);
		UnderfedPanel.add(Underfed);
		UnderfedPanel.add(UnderfedLabel1);
		
		SlickerDecorator.instance().decorate(Fitness);
		SlickerDecorator.instance().decorate(FitnessLabel1);
		FitnessLabel1.setForeground(new Color(50,50,50));
		SlickerDecorator.instance().decorate(Overfed);
		SlickerDecorator.instance().decorate(OverfedLabel1);
		OverfedLabel1.setForeground(new Color(50,50,50));
		SlickerDecorator.instance().decorate(Underfed);
		SlickerDecorator.instance().decorate(UnderfedLabel1);
		UnderfedLabel1.setForeground(new Color(50,50,50));
		
		this.l1 = factory.createLabel("Fitness:");
		this.l1.setHorizontalAlignment(SwingConstants.RIGHT);
		this.l1.setForeground(new Color(40,40,40));
		this.l2 = factory.createLabel("Overfed:");
		this.l2.setHorizontalAlignment(SwingConstants.RIGHT);
		this.l2.setForeground(new Color(40,40,40));
		this.l3 = factory.createLabel("Underfed:");
		this.l3.setHorizontalAlignment(SwingConstants.RIGHT);
		this.l3.setForeground(new Color(40,40,40));

		this.thresholdsPanel.setLayout(null);
		this.thresholdsPanel.add(this.thresholdTitle);
		this.thresholdsPanel.add(this.l1);
		//this.thresholdsPanel.add(this.t1);
		this.thresholdsPanel.add(FitnessPanel);
		this.thresholdsPanel.add(this.l2);
		//this.thresholdsPanel.add(this.t2);
		this.thresholdsPanel.add(OverfedPanel);
		this.thresholdsPanel.add(this.l3);
		//this.thresholdsPanel.add(this.t3);
		this.thresholdsPanel.add(UnderfedPanel);

		this.thresholdsPanel.add(this.t6);

		this.thresholdsPanel.setBounds(0, 50, 520, 240);
		this.thresholdTitle.setBounds(10, 10, 200, 30);
		this.l1.setBounds(25, 50, 100, 20);
		this.l2.setBounds(25, 80, 100, 20);
		this.l3.setBounds(20, 110, 105, 20);

		this.t6.setBounds(122, 200, 360, 20);
		FitnessPanel.setBounds(122, 45, customPanelWidth+20, 25);
		OverfedPanel.setBounds(122, 75, customPanelWidth+20, 25);
		UnderfedPanel.setBounds(122, 105, customPanelWidth+20, 25);


		this.setLayout(null);
		this.add(this.thresholdsPanel);

		this.validate();
		this.repaint();
	}
	

	
	public void setEnabled(boolean status){
		
		Fitness.setEnabled(status);
		Overfed.setEnabled(status);
		Underfed.setEnabled(status);

		this.t6.setEnabled(status);

		
	}
	
	public void removeAndThreshold(){
	    this.thresholdsPanel.remove(this.t6);
		
		this.thresholdsPanel.setBounds(0, 50, 520, 210);
	}
	
	public boolean hasAndThreshold(){ return (this.l6 != null); }

	public HashMap<String,Object> getSettings(){
	    this.settings.put("Fitness",ReadValue(Fitness.getValue()));
		this.settings.put("Overfed",ReadValue(Overfed.getValue()));
		this.settings.put("Underfed",ReadValue(Underfed.getValue()));


		return this.settings;

	}
}
