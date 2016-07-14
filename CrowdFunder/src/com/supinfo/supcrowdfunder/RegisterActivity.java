package com.supinfo.supcrowdfunder;

import com.supinfo.supcrowdfunder.entity.User;
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

public class RegisterActivity extends Activity {
	
	private User newUser;
	int fullFields = 0;
	boolean userCreated = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		Button registerButton = (Button) findViewById(R.id.registerButton);
		
		registerButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				final EditText nameET = (EditText) findViewById(R.id.nameEditText);
				final EditText firstNameET = (EditText) findViewById(R.id.firstNameTextView);
				final EditText emailET = (EditText) findViewById(R.id.emailEditText);
				final EditText passwordET = (EditText) findViewById(R.id.passwordEditText);
				final EditText confirmationET = (EditText) findViewById(R.id.confirmationEditText);
				
				new Thread(new Runnable() {			
					@Override
					public void run() {
						
						// Si les 5 champs sont remplis, alors on créé l'objet newProject
						if (nameET.getText().length() <= 1 || firstNameET.getText().length() <= 1 || emailET.getText().length() <= 1 || passwordET.getText().length() <= 1 || confirmationET.getText().length() <= 1) {
							Toast.makeText(
									getApplicationContext(),
									"Attention veuillez remplir tous les champs",
									Toast.LENGTH_LONG).show();
						} else if(passwordET.getText() == confirmationET.getText()){
							Toast.makeText(
									getApplicationContext(),
									"Votre mot de passe n\'est pas le même",
									Toast.LENGTH_LONG).show();
						}
						
						else {
							
						WebService service = new WebService();
						
						newUser.setName(nameET.getText().toString());
						newUser.setFirstName(firstNameET.getText().toString());
						newUser.setMail(emailET.getText().toString());
						newUser.setPassword(newUser.encryptPassword(passwordET.getText().toString()));
						
						service.addUser(newUser);
						userCreated = true;
						}
					}
				}).start();
			}
			
		});
		
		if (userCreated == true){
			Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
			startActivity(intent);
		}
		((Button)findViewById(R.id.cmdBackToIndex)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	
	// Vérifie que les champs sont bien remplis
	public void isNotEmpty(String verifyET, EditText ET ){
		
		verifyET = ET.getText().toString();
		
		if(verifyET.matches("")){
			Toast.makeText(this, "Il faut remplir ce champ.", Toast.LENGTH_SHORT).show();
			return;
		}
		else{
			fullFields ++; 
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.menu.login:
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			break;
		case R.menu.index:
			intent = new Intent(this, IndexActivity.class);
			startActivity(intent);
			break;
		
		}
		return false;
	}

}
