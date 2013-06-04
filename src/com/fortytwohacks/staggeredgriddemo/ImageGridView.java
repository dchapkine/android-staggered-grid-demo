/**
 * Simple demo to show how i implemented the staggered grid view
 * 
 * http://www.42hacks.com/notes/en/20130511-lessons-learned-from-building-my-first-android-app-part1/
 * 
 * @author DmitriChapkine
 */
package com.fortytwohacks.staggeredgriddemo;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.loopj.android.image.SmartImageView;
import com.staggeredgriddemo.R;


/**
 * This view will hold your images
 */
public class ImageGridView extends ScrollView
{
	private RelativeLayout _layout;
	private ArrayAdapter<ImageViewModel> _adapter;
	
	// @todo get height dynamicly depending on the root view container
	private int _screenHeight = 800;

	/**
	 * Ctor
	 * 
	 * @param context
	 */
    public ImageGridView(Context context)
    {
		super(context);
		
		//
		_adapter = null;
		
		// Create relative layout to hold images
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 6000);
        _layout = new RelativeLayout(context);
        _layout.setLayoutParams(params);
        this.addView(_layout);
	}
    
	/**
	 * Removes everything from the view
	 */
	public void cleanView()
	{
		_layout.removeAllViews();
	}
	
	/**
	 * Force y scroll position
	 * @param y
	 */
	public void scrollToY(int y)
	{
		scrollTo(0, y);
	}
	
	/**
	 * Item click/tap handler
	 * @param id
	 */
	public void onItemTap(String id)
	{
		// tap on a grid item with item.id = id
	}
	
	
	/**
	 * Load images for y scroll position = 0
	 */
	public void triggerLazyLoading()
	{
		triggerLazyLoadingY(0);
	}
	
	/**
	 * Load images for y scroll position = y
	 */
	public void triggerLazyLoadingY(int y)
	{
		if (_adapter!=null)
    	{
    		int y2 = y + _screenHeight;
    		for (int i = 0; i < _adapter.getCount(); i++)
    		{	
    			ImageViewModel pvm = _adapter.getItem(i);
    			
    			// if out of screen, unload the image
    			if (y > pvm.topEnd || y2 < pvm.topStart)
    			{
    				if (pvm.isOnScreen)
    				{
						FrameLayout toRemove = (FrameLayout) _layout.findViewWithTag(pvm.id);
						if (toRemove != null)
						{
							SmartImageView img = (SmartImageView) toRemove.getChildAt(0);
							_layout.removeView(toRemove);
							img = null;
							toRemove = null;
						}
						
						pvm.isOnScreen = false;
    				}
    			}
    			// else, If not already on screen we add it
    			else if (!pvm.isOnScreen)
    			{
    				// build image view container
    				FrameLayout frame = new FrameLayout(getContext());
    				frame.setMinimumWidth(pvm.width);
    				frame.setMinimumHeight(pvm.height);
    				
    				frame.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0)
						{
							onItemTap((String) arg0.getTag());
						}
			 		});
    				
    				frame.setTag(pvm.id);
    				
    		        RelativeLayout.LayoutParams fparams;
    		        fparams = new RelativeLayout.LayoutParams(pvm.width, pvm.height);
    		        fparams.leftMargin = pvm.leftStart;
    		        fparams.topMargin = pvm.topStart;
    		        
    				
    				// build image view
    				SmartImageView imageView = new SmartImageView(getContext());
    		        imageView.setImageUrl(pvm.img, R.drawable.image_loading_100x100, R.drawable.image_loading_100x100);
    		        
    		        LayoutParams imageLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    		        imageLayoutParams.setMargins(20, 20, 20, 20);
    		        imageView.setLayoutParams(imageLayoutParams);
    		        
    		        
    		        
    		        // add views
    		        frame.addView(imageView);
    		        _layout.addView(frame, fparams);
    		        
    		        // image is on screen now
    		        pvm.isOnScreen = true;
    			}
    			
    		}
    		
    	}
	}
	
	/**
	 * Scroll listener
	 */
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy)
    {
    	super.onScrollChanged(x, y, oldx, oldy);
    	
    	
    	if (Math.abs(oldy - y) < 2)
    	{
        	triggerLazyLoadingY(y);
    	}
    }

    /**
     * 
     * @param pa
     */
	public void setAdapter(ArrayAdapter<ImageViewModel> pa) {
		
		_adapter = pa;
		
	}

	/**
	 * 
	 * @param maxHeight
	 */
	public void setContentHeight(int maxHeight) {
		
		if (_layout != null && maxHeight > 0)
		{
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, maxHeight);
	        _layout.setLayoutParams(params);
		}
	}
}
