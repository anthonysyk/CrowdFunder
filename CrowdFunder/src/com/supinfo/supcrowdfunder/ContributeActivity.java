package com.supinfo.supcrowdfunder;

import java.sql.Date;

import com.supinfo.supcrowdfunder.entity.Contribution;
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
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ContributeActivity extends Activity {

	private Project project;
	private boolean result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contribute);
		Intent intent = this.getIntent();
		project = (Project) intent.getExtras().get("project");
		((TextView) findViewById(R.id.stateTextView)).setText(project
				.getTitle());
		((TextView) findViewById(R.id.descript2TextView)).setText(project
				.getDescript2());
		((TextView) findViewById(R.id.creatorTextView)).setText(project
				.getUser().getName() + " " + project.getUser().getFirstName());
		((TextView) findViewById(R.id.categoryTextView)).setText(project
				.getCategory().getLibelle());

		// sur le bouton annuler
		((Button) findViewById(R.id.cmdCancelContribute))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getApplicationContext(),
								IndexActivity.class);
						startActivity(intent);
						finish();
					}
				});
		// la progresse bar
		((SeekBar) findViewById(R.id.contributeSeekBar))
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						((TextView) findViewById(R.id.txtContribute))
								.setText(String.valueOf(seekBar.getProgress()));
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
					}
				});
		// sur le bouton valider
		((Button) findViewById(R.id.cmdContribute))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (((TextView) findViewById(R.id.txtContribute))
								.getText().toString().length() <= 0) {
							Toast.makeText(
									getApplicationContext(),
									"Attention,  il faut remplir le champ de contribution.",
									Toast.LENGTH_LONG).show();
						} else {
							Contribution contribution = new Contribution();
							java.util.Date day = new java.util.Date();
							contribution.setDateContribut(new Date(day
									.getTime()));
							contribution.setMontant(Double
									.valueOf(((TextView) findViewById(R.id.txtContribute))
											.getText().toString()));
							contribution.setProject(project);
							contribution.setUser(project.getUser());
							if (addContribution(contribution)) {
								// contribution bien ajouter
								Intent intent = new Intent(
										getApplicationContext(),
										IndexActivity.class);
								startActivity(intent);
								finish();
							} else {
								Toast.makeText(
										getApplicationContext(),
										"Votre contribution au projet ne sais pas réalisée. Un problème est survenu",
										Toast.LENGTH_LONG).show();
							}
						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contribute, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.menu.index:
			Intent intent = new Intent(this, IndexActivity.class);
			startActivity(intent);
			break;
		case R.menu.add_project:
			intent = new Intent(this, AddProjectActivity.class);
			break;
		case R.menu.profil:
			intent = new Intent(this, ProfilActivity.class);
			break;
		}
		return false;
	}

	/**
	 * permet d'ajouter une contribution via le webservice
	 * @param contribution
	 * @return
	 */
	private boolean addContribution(final Contribution contribution) {
		  result = false ;
		new Thread(new Runnable() {
			@Override
			public void run() {
				WebService service = new WebService();
				result = service.addContribution(contribution);
			}
		}).start();
		return result;
	}

}
