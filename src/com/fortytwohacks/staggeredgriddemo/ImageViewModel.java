/**
 * Simple demo to show how i implemented the staggered grid view
 * 
 * http://www.42hacks.com/notes/en/20130511-lessons-learned-from-building-my-first-android-app-part1/
 * 
 * @author DmitriChapkine
 */
package com.fortytwohacks.staggeredgriddemo;


/**
 * This is the view model for your image
 */
public class ImageViewModel {
	
	/**
	 * Image ID
	 */
	public String id = "";
	
	/**
	 * Column # in layout (zero based)
	 */
	public int column = 0;
	
	/**
	 * Image url
	 */
	public String img = "";
	
	/**
	 * Aspect ratio of an image
	 */
	public float ratio = 1.0f;

	/**
	 * Image Size
	 */
	public int width = -1;
	public int height = -1;

	/**
	 * X coordinates of the image
	 */
	public int leftStart = 0;
	public int leftEnd = 0;
	
	/**
	 * Y coordinates of the image
	 */
	public int topStart = 0;
	public int topEnd = 0;
	
	/**
	 * Flag to know if this image is on screen
	 */
	public boolean isOnScreen = false;
	
	
	/**
	 * We MUST supply image width (= parent width / columnCount), then height will be calculated.
	 * 
	 * @param w
	 * @return
	 */
	public int computeHeight(int w)
	{
		if (width != -1 && width == w)
			return height;
		
		width = w;
		height = (int) ((float)w * ratio);
		return height;
	}
}
