package com.sehack1.myday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostArrayAdapter extends BaseAdapter {

String[] dayNum;
Context ctxt;
LayoutInflater postInflater;

	public PostArrayAdapter(String[] arr, Context c){
		dayNum = arr;
		ctxt = c;
		postInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(dayNum.length <=0){
			return 1;
		}
		return dayNum.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Create the cell (View) and populate it with an element
		// an element of the array.
		
		if(convertView==null)
			convertView = postInflater.inflate(R.layout.postlayout, null);
		
			TextView dayTitle = (TextView)convertView.findViewById(R.id.tvPostTitle);
			TextView dayCaption = (TextView)convertView.findViewById(R.id.tvCaption);
			ImageView dayPic = (ImageView)convertView.findViewById(R.id.ivPost);
		
		dayTitle.setText(""+ dayNum[position]);
		if(position == 0){
			dayCaption.setText("This is a caption to the picture. Here, the user provides a quick insight to how their day went.");
		}
		else{
			dayCaption.setText("The feed then becomes a personal journal that chronicles the moments that are most memorable.");
		}
		if(position == 0){
			dayPic.setImageResource(R.raw.sample);
		}
		else{
			dayPic.setImageResource(R.raw.default_pic);
		}
		return convertView;
	}
	
}
