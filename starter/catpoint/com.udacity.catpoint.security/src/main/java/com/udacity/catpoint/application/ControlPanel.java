package com.udacity.catpoint.application;

import com.udacity.catpoint.data.ArmingStatus;
import com.udacity.catpoint.service.SecurityService;
import com.udacity.catpoint.service.StyleService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JPanel containing the buttons to manipulate arming status of the system.
 */
public class ControlPanel extends JPanel {

    private SecurityService securityService;
    private Map<ArmingStatus, JButton> buttonMap;
	
	SensorPanel sensors;

    public ControlPanel(SecurityService securityService, SensorPanel sensors, ImagePanel image)
	{
        super();
		
        setLayout(new MigLayout());
		
		this.sensors = sensors;
		
        this.securityService = securityService;

        JLabel panelLabel = new JLabel("System Control");
        panelLabel.setFont(StyleService.HEADING_FONT);

        add(panelLabel, "span 3, wrap");

        	//create a map of each status type to a corresponding JButton
        buttonMap = Arrays.stream(ArmingStatus.values())
                .collect(Collectors.toMap(status -> status, status -> new JButton(status.getDescription())));

        	//add an action listener to each button that applies its arming status and recolors all the buttons
        buttonMap.forEach( (k, v) ->
		{
            v.addActionListener(e ->
			{
                buttonMap.get( securityService.getArmingStatus() ).setEnabled(true);
                
                securityService.setArmingStatus(k);
                buttonMap.forEach((status, button) -> button.setBackground(status == k ? status.getColor() : null));
				
				//sensors.extUpdateSensorList();
				
				v.setEnabled(false);
				
				if( k == ArmingStatus.ARMED_HOME )
					securityService.processImage( securityService.getCurrentImage() );
            });
        });

        	//map order above is arbitrary, so loop again in order to add buttons in enum-order
        Arrays.stream(ArmingStatus.values()).forEach(status -> add(buttonMap.get(status)));

        ArmingStatus currentStatus = securityService.getArmingStatus();
        
        buttonMap.get(currentStatus).setBackground( currentStatus.getColor() );
        buttonMap.get(currentStatus).setEnabled(false);
    }
}
