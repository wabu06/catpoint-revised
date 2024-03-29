package com.udacity.catpoint.application;

import com.udacity.catpoint.data.PretendDatabaseSecurityRepositoryImpl;
import com.udacity.catpoint.data.SecurityRepository;
//import com.udacity.catpoint.service.FakeImageService;
//import com.udacity.catpoint.service.SecurityService;
import com.udacity.catpoint.service.*;

import com.udacity.image.service.*;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javax.inject.Inject;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the primary JFrame for the application that contains all the top-level JPanels.
 *
 * We're not using any dependency injection framework, so this class also handles constructing
 * all our dependencies and providing them to other classes as necessary.
 */
public class CatpointGui extends JFrame
{
    private SecurityRepository securityRepository;
	
	private SecurityService securityService;
	
    private DisplayPanel displayPanel;
    private SensorPanel sensorPanel;
	private ImagePanel imagePanel;
	private ControlPanel controlPanel;
    //private ImagePanel imagePanel = new ImagePanel(securityService);

    public CatpointGui()
	{
		this.securityRepository = new PretendDatabaseSecurityRepositoryImpl();
		
		this.securityService = new SecurityService(securityRepository);
		
		this.displayPanel = new DisplayPanel(securityService);
    	this.sensorPanel = new SensorPanel(securityService);
		this.imagePanel = new ImagePanel(securityService);
		this.controlPanel = new ControlPanel(securityService);
	
        //setLocation(100, 100);
		//setLocation(50, 50);
        //setSize(600, 850);
        
        setLocation(-600, -720);
        
		setSize(600, 720);
        setTitle("NoCats");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try( InputStream is = getClass().getClassLoader().getResourceAsStream("no-cats.jpeg") )
		{
			ImageIcon icon = new ImageIcon( is.readAllBytes() );
			setIconImage( icon.getImage() );
		}
		catch(Exception exp)
		{
			Logger log = LoggerFactory.getLogger(CatpointGui.class);
			log.info("No Icon", exp);
		}

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());
        mainPanel.add(displayPanel, "wrap");
        mainPanel.add(imagePanel, "wrap");
        mainPanel.add(controlPanel, "wrap");
        mainPanel.add(sensorPanel);

        getContentPane().add(mainPanel);

    }
}
