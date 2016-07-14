package com.supinfo.supcrowdfunder;

import com.supinfo.supcrowdfunder.entity.User;
import com.supinfo.supcrowdfunder.rest.WebService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//lors de l'appuie du button anonyme
		((Button)findViewById(R.id.cmdAnonyme)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
				startActivity(intent);
				finish();			
			}
		});
		
		//sur le click de s'enregistrer
		((Button)findViewById(R.id.cmdRegisterLogin)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final EditText emailET = (EditText) findViewById(R.id.emailEditText);
				final EditText passwordET = (EditText) findViewById(R.id.passwordEditText);
				if (emailET.getText().length() <= 1) {
					Toast.makeText(
							getApplicationContext(),
							"Attention veuuillez rentrer correctement votre email",
							Toast.LENGTH_LONG).show();
				} else if (passwordET.getText().length() <= 1) {
					Toast.makeText(
							getApplicationContext(),
							"Attention veuuillez rentrer correctement votre mot de passe",
							Toast.LENGTH_LONG).show();
				} else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							WebService service = new WebService();
							loggin(service.getUser(emailET.getText().toString(),
									passwordET.getText().toString()));
						}
					}).start();
				}
			}
		});
	}

	private void loggin(User user) {
		if (user != null) {
			Log.e("Test", "Bonjour");
			Intent intent = new Intent(getApplicationContext(),
					IndexActivity.class);
			intent.putExtra("idUser", user.getIdUser());
			startActivity(intent);
			finish();
		} else {
			Toast.makeText(getApplicationContext(),
					"Votre login/mot de passe ne sont pas bon.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.menu.register:
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.menu.index:
			intent = new Intent(this, IndexActivity.class);
			break;

		}
		return false;
	}
}
