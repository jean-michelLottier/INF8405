package com.inf8402.tps.tp1.bejeweled.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.inf8402.tps.tp1.bejeweled.dao.Item;

public class GridAdapter extends BaseAdapter {

	private final Context context;
	// private Item[] items = null;
	private ArrayList<CustomImageView> imageList = null;
	private ArrayList<Item> items = null;
	
	public GridAdapter(Context context, ArrayList<Item> items) {
		this.context = context;
		this.items = items;
		this.initializeImages();
	}

	private void initializeImages()
	{
		imageList = new ArrayList<CustomImageView>();
		for(int i=0; i<items.size(); i++)
		{
			imageList.add(null);
		}
	}
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Initialisation
		if( imageList.get(position) == null)
		{
			int width = parent.getWidth() / 8;
			int height = parent.getHeight() / 8;
			CustomImageView img = new CustomImageView(context);
			img.getImageView().setLayoutParams(new GridView.LayoutParams(width, height));
			img.getImageView().setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageList.set(position, img);
		}
		else
		if( imageList.get(position).getImageView().getHeight()==0)
		{
			int width = parent.getWidth() / 8;
			int height = parent.getHeight() / 8;
			imageList.get(position).getImageView().setLayoutParams(new GridView.LayoutParams(width, height));
		}
		// Attribution d'image
		imageList.get(position).getImageView().setImageResource(items.get(position).getResourceImage());
		
		// Animation
		if(items.get(position).isAnimated())
		{
			imageList.get(position).startAnimation();
		}
		else
		{
			if(imageList.get(position).isAnimated())
				imageList.get(position).stopAnimation();
		}
		
		return imageList.get(position).getImageView();
	}
	
	public class CustomImageView{
		ImageView img=null;
		AnimationDrawable anim = null;
		public CustomImageView(Context c)
		{
			this.img = new ImageView(c);
			this.anim = new AnimationDrawable();
		}

		public CustomImageView(Context c, ImageView i)
		{
			this.img = i;
			this.anim = new AnimationDrawable();
		}
		
		public void setImageView(ImageView i)
		{
			this.img = i;
		}

		public ImageView getImageView()
		{
			return this.img;
		}
		public void setAnimationDrawable(AnimationDrawable i)
		{
			this.anim = i;
		}
		public boolean isAnimated()
		{
			return anim.isRunning();
		}
		public AnimationDrawable getAnimationDrawable()
		{
			return this.anim;
		}
		public void startAnimation()
		{
			if(img!=null && anim!=null)
			{
				anim = (AnimationDrawable) img.getDrawable();
				anim.start();
			}
		}

		public void stopAnimation()
		{
			if(img!=null && anim!=null && anim.isRunning())
			{
				anim.stop();
			}
		}
		
	}
}
