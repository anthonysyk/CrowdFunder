package com.supinfo.supcrowdfunder.adapter;

import java.util.ArrayList;

import com.supinfo.supcrowdfunder.R;
import com.supinfo.supcrowdfunder.entity.Project;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LstProjectAdpater extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<Project> lstProject;
	
	public LstProjectAdpater(Context context, ArrayList<Project> lstProject){
		this.inflater = LayoutInflater.from(context);
		this.lstProject = lstProject;
	}
	@Override
	public int getCount() {
		return this.lstProject.size();
	}

	@Override
	public Object getItem(int i) {
		return this.lstProject.get(i);
	}

	@Override
	public long getItemId(int i) {
		return this.lstProject.get(i).getIdProject();
	}
	
	public class Holder{
		TextView txtIdproject;
		TextView txtTitle;
		TextView txtCompletion;
		TextView txtDescript;
	}

	@SuppressLint("SimpleDateFormat") @Override
	public View getView(int i, View v, ViewGroup parent) {
		Holder holder;
		if(v == null){
			holder = new Holder();
			v = inflater.inflate(R.layout.list_project, null);
			holder.txtIdproject = (TextView)v.findViewById(R.id.id_project);
			holder.txtTitle = (TextView)v.findViewById(R.id.title);
			holder.txtCompletion = (TextView)v.findViewById(R.id.completion_date);
			holder.txtDescript = (TextView)v.findViewById(R.id.Lstprojectdescript);
			v.setTag(holder);
		}else{
			holder = (Holder)v.getTag();
		}
		holder.txtIdproject.setText(String.valueOf(this.lstProject.get(i).getIdProject()));
		holder.txtTitle.setText(this.lstProject.get(i).getTitle());
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//holder.txtCompletion.setText(this.lstProject.get(i).getCompletionDate().toGMTString());
		holder.txtDescript.setText(this.lstProject.get(i).getDescript());		
		return v;
	}

}
