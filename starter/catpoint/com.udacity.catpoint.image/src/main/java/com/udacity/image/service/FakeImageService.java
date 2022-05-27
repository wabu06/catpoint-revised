package com.udacity.image.service;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Service that tries to guess if an image displays a cat.
 */
public class FakeImageService implements ImageService
{
    private final Random r = new Random();

    @Override
	public boolean imageContainsCat(BufferedImage image, float confidenceThreshhold)
	{
		return (r.nextFloat()*confidenceThreshhold) > (3*confidenceThreshhold/4);
    }
}
