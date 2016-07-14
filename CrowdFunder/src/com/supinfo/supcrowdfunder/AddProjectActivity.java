package com.supinfo.supcrowdfunder;


import java.sql.Date;
import com.supinfo.supcrowdfunder.entity.Project;
import com.supinfo.supcrowdfunder.rest.WebService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProjectActivity extends Activity {
	
	Project newProject;
	int fullFields = 0;
	boolean projectCreated = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_project);
			
		Button addProjectButton = (Button) findViewById(R.id.addProjectButton);
			
			addProjectButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					final EditText projectNameET = (EditText) findViewById(R.id.projectNameEditText);
					final EditText projectDescript1ET = (EditText) findViewById(R.id.descript1EditText);
					final EditText projectDescript2ET = (EditText) findViewById(R.id.descript2EditText); 
					final EditText projectThresholdET = (EditText) findViewById(R.id.thresholdEditText);
					final EditText projectCompletionDateET = (EditText) findViewById(R.id.completionEditText);
					
					new Thread(new Runnable() {			
						@Override
						public void run() {
							
							// Si les 5 champs sont remplis, alors on créé l'objet newProject
							if (projectNameET.getText().length() <= 1 || projectDescript1ET.getText().length() <= 1 || projectDescript2ET.getText().length() <= 1 || projectThresholdET.getText().length() <= 1 || projectCompletionDateET.getText().length() <= 1) {
								Toast.makeText(
										getApplicationContext(),
										"Attention veuillez remplir tous les champs",
										Toast.LENGTH_LONG).show();
							} else {
								
							WebService service = new WebService();
							
							newProject.setTitle(projectNameET.getText().toString());
							newProject.setDescript(projectDescript1ET.getText().toString());
							newProject.setDescript2(projectDescript2ET.getText().toString());
							newProject.setThreshold(Double.parseDouble(projectThresholdET.getText().toString()));
							newProject.setCompletionDate(Date.valueOf(projectCompletionDateET.getText().toString()));
							newProject.setCreationDate(new java.sql.Date(new java.util.Date().getTime())); // Date d'aujourd'hui avec SimpleDateFormat
							
							service.addProject(newProject);
							projectCreated = true;
							}
						}
					}).start();
					
					// On passe à l'index activity si user existe.
					
					if (projectCreated == true){
					Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
					startActivity(intent);
				}
			}
		});			
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
		getMenuInflater().inflate(R.menu.add_project, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.menu.profil:
			Intent intent = new Intent(this, ProfilActivity.class);
			startActivity(intent);
			break;
		case R.menu.index:
			intent = new Intent(this, IndexActivity.class);
			break;
		}
		return false;
	}

}
