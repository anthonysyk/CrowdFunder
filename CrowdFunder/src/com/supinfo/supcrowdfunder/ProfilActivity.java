package com.supinfo.supcrowdfunder;

import com.supinfo.supcrowdfunder.entity.User;
import com.supinfo.supcrowdfunder.rest.WebService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfilActivity extends Activity {
	private int idUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil);
		Intent intent  = this.getIntent();
		Bundle extra = intent.getExtras();
		if(extra != null){
		 idUser = extra.getInt("idUser");
		 getUser();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profil, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.menu.index:
			Intent intent = new Intent(this, IndexActivity.class);
			startActivity(intent);
			break;
		case R.menu.add_project:
			intent = new Intent(this, AddProjectActivity.class);
			break;
		
		}
		return false;
	}
	
	private void getUser(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				WebService service = new WebService();
				addUser(service.getUserById(idUser));
			}
		}).start();
	}
	
	private void addUser(User user){
		if(user != null){
			((TextView)findViewById(R.id.nameEditText)).setText(user.getName());
			((TextView)findViewById(R.id.txtContribute)).setText(user.getFirstName());
			((TextView)findViewById(R.id.editText2)).setText(user.getMail());			
		}
	}

}
