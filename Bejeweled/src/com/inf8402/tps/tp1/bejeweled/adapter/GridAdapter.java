package com.inf8402.tps.tp1.bejeweled.adapter;

import java.util.ArrayList;

import android.content.Context;
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
	private ArrayList<Item> items = null;

	public GridAdapter(Context context, ArrayList<Item> items) {
		this.context = context;
		this.items = items;
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
		ImageView imageView;
		int width = parent.getWidth() / 8;
		int height = parent.getHeight() / 8;
		if (convertView == null) {
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(width, height));
			imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		} else {
			imageView = (ImageView) convertView;
			if (imageView.getHeight() == 0)
				imageView.setLayoutParams(new GridView.LayoutParams(width,
						height));
		}
		imageView.setImageResource(items.get(position).getItemID());
		return imageView;
	}

}
