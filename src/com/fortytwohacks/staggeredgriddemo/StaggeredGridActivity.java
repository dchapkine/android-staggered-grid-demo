/**
 * Simple demo to show how i implemented the staggered grid view
 * 
 * http://www.42hacks.com/notes/en/20130511-lessons-learned-from-building-my-first-android-app-part1/
 * 
 * @author DmitriChapkine
 */
package com.fortytwohacks.staggeredgriddemo;

import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;

public class StaggeredGridActivity extends Activity {

	private ImageGridView _imageGridView;
	private ImageListAdapter _imagesAdapter;
	private boolean _isInitialized;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		
		_isInitialized = false;

		// http://stackoverflow.com/questions/12319194/android-httpurlconnection-throwing-eofexception
    	System.setProperty("http.keepAlive", "false");
		
		// The adapter will hold parsed json data
        _imagesAdapter = new ImageListAdapter(this);
        
        // Create the "staggered grid view"
		_imageGridView = new ImageGridView(this);
		_imageGridView.setAdapter(_imagesAdapter);
		setContentView(_imageGridView);
	};
	
	/**
	 * In the real app, instead of calling "onDataReady" here, call it when your data is fully loaded...
	 * 
	 * For the purpose of this demo, i am calling it here, in "onWindowFocusChanged" instead of "onCreate",
	 * because when onCreate is called, the layout is not ready and _imageGridView.getWidth() returns 0... and we need width
	 * 
	 * @see http://stackoverflow.com/questions/3591784/android-get-width-returns-0
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
		onDataReady();
	};
	
	
	/**
	 * Imagine that you load a json array from somewhere. This should be called just after your json is
	 * loaded and parsed (asyncroniously of course).
	 * 
	 * For the purpose of this demo, i am generating some fake data instead of loading the real thing
	 */
	private void onDataReady()
	{
		if (!_isInitialized)
		{
			// your real parsed json data should be here
			JSONArray arr = generateSomeRandomData();

			// let's populate the adapter
			int maxHeight = _imagesAdapter.populateFromJsonArray(arr, _imageGridView.getWidth());

			// start loading images
			_imageGridView.cleanView();
			_imageGridView.setContentHeight(maxHeight);
			_imageGridView.scrollToY(0);
			_imageGridView.triggerLazyLoading();
			
			//
			_isInitialized = true;
		}
	}
	
	/**
	 * Generating 5000 random images
	 * 
	 * @return JSONArray
	 */
	private JSONArray generateSomeRandomData()
	{
		Random r = new Random();
		JSONArray arr = new JSONArray();
		
		for (int i = 0; i < 5000 ; i++)
		{
			JSONObject o = new JSONObject();
			try
			{
				int w = r.nextInt(400-100) + 100;
				r = new Random();
				int h = r.nextInt(400-100) + 100;
				
				o.put("id", "whatever_id_"+i);
				o.put("ratio", (double)h/(double)w);
				o.put("img", "http://lorempixel.com/" + w + "/" + h + "/");	
				arr.put(i, o);
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		
		return arr;
	}
}
