package com.udacity.catpoint.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.udacity.catpoint.data.*;
import com.udacity.image.service.*;


public class ImageServiceHandler implements InvocationHandler
{
	private SecurityRepository securityRepository;
	
	private ImageService fakeImageService;
	private ImageService awsImageService;
	private ImageService googleImageService;
	private ImageService opencvImageService;
	
	ImageServiceHandler(SecurityRepository securityRepository)
	{
		this.securityRepository = securityRepository;
		
		fakeImageService = new FakeImageService();

		awsImageService = AwsImageService.newInstance();
		
		if(awsImageService == null)
			awsImageService = fakeImageService;
		else
			ImageService.log.info("AWS is -- " + awsImageService.getClass() + "\n");

		opencvImageService = fakeImageService;
		
		googleImageService = new GoogleImageService();
	}
	
	@Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception
	{
		ImageService.log.info("Using Proxy\n");
		
		return switch( securityRepository.getImageService() )
        		{
        			case "AWS" -> method.invoke(awsImageService, args);
        	
        			case "GOOGLE" -> method.invoke(googleImageService, args);

        			case "OPENCV" -> method.invoke(opencvImageService, args);
        		
        			default -> method.invoke(fakeImageService, args);
        		};
	}
}
