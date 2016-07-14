package com.supinfo.supcrowdfunder;

import com.supinfo.supcrowdfunder.entity.Project;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProjectDetailsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_details);
		Intent intent = this.getIntent();
		Project project = (Project)intent.getExtras().get("project");
		((TextView)findViewById(R.id.stateTextView)).setText(project.getTitle());
		((TextView)findViewById(R.id.descript2TextView)).setText(project.getDescript2());
		((TextView)findViewById(R.id.creatorTextView)).setText(project.getUser().getName() + " " + project.getUser().getFirstName());
		((TextView)findViewById(R.id.categoryTextView)).setText(project.getCategory().getLibelle());
		((Button)findViewById(R.id.cmdBackToIndex)).setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
				startActivity(intent);
				finish();
			}
		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project_details, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.menu.register:
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.menu.login:
			intent = new Intent(this, LoginActivity.class);
			break;
		case R.menu.index:
			intent = new Intent(this, IndexActivity.class );
			break;		
		}
		return false;
	}

}
