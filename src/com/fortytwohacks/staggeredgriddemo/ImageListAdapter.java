/**
 * Simple demo to show how i implemented the staggered grid view
 * 
 * http://www.42hacks.com/notes/en/20130511-lessons-learned-from-building-my-first-android-app-part1/
 * 
 * @author DmitriChapkine
 */
package com.fortytwohacks.staggeredgriddemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ImageListAdapter extends ArrayAdapter<ImageViewModel>
{

	public ImageListAdapter(Context context)
	{
		super(context, android.R.layout.simple_list_item_1);
	}

	public int populateFromJsonArray(JSONArray arr, int screenWidth)
	{
		// init
		clear();
		
		// How many columns you want in your staggered grid view ?
		int columnCount = 4;
		
		// each column width
		int width = screenWidth/columnCount;
		
		// y coordinate of next item in each column
		int[] topStart = new int[columnCount];
		for (int ii = 0; ii < columnCount; ii ++)
		{
			topStart[ii] = 0;
		}

		// x coordinate (left border) of next item in each column
		int[] leftStart = new int[columnCount];
		for (int ii = 0; ii < columnCount; ii ++)
		{
			leftStart[ii] = ii*width;
		}
		
		// x coordinate (right border) of next item in each column
		int[] leftEnd = new int[columnCount];
		for (int ii = 0; ii < columnCount; ii ++)
		{
			leftEnd[ii] = ii*width+width;
		}
		
		// here you can set your progress bar max item count using: prods.length()
		
		for (int i = 0; i < arr.length(); i++)
		{
			try
			{
				JSONObject json = arr.getJSONObject(i);
				
				ImageViewModel pvm = new ImageViewModel();
				
				pvm.id = json.getString("id");
				
				pvm.column = i%columnCount;
				pvm.img = json.getString("img");
				pvm.ratio = (float) json.getDouble("ratio");
				
				int height = pvm.computeHeight(width);

				pvm.topStart = topStart[pvm.column];
				pvm.topEnd = topStart[pvm.column] + height;
				
				pvm.leftStart = leftStart[pvm.column];
				pvm.leftEnd = leftEnd[pvm.column];
				
				// calculate next item y coordinate
				topStart[pvm.column] += height;
				
				// populate the adapter
				add(pvm);
				
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			
			// here you might update your progress bar or something like that
		}
		
		// calculate view heigth
		int maxHeight = 0;
		for (int i = 0; i < topStart.length; i++)
		{
			if (topStart[i] > maxHeight) maxHeight = topStart[i];
		}
		
		return maxHeight;
	}

}
