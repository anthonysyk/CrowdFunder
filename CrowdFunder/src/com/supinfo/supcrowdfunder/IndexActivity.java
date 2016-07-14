package com.supinfo.supcrowdfunder;

import java.util.ArrayList;
import java.util.List;

import com.supinfo.supcrowdfunder.adapter.LstProjectAdpater;
import com.supinfo.supcrowdfunder.entity.Category;
import com.supinfo.supcrowdfunder.entity.Project;
import com.supinfo.supcrowdfunder.rest.WebService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class IndexActivity extends Activity {
	private ArrayList<Category> lstCategory;
	private ArrayList<Project> lstProject = new ArrayList<Project>();
	private int idUser = -1;// init et non connecté

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		this.init();
		Bundle extra = this.getIntent().getExtras();
		if(extra != null){
			idUser = extra.getInt("idUser");
		}
		// pour le choix d'une catégorie
		((Spinner) findViewById(R.id.categoryListView))
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// affichage de la liste suivant les catégories
						// si 0 alors tout les projets
						if (arg3 == 0) {
							// on ne fait rien
						} else {
							// on garde notre Arraylist<Projetct> déjà fait mais
							// on le tri
							ArrayList<Project> lstProjectTemp = new ArrayList<Project>();
							for (int i = 0; i < lstProject.size(); i++) {
								if (lstProject.get(i).getCategory()
										.getIdCategory() == lstCategory.get(
										arg2).getIdCategory()) {
									lstProjectTemp.add(lstProject.get(i));
								}
							}
							// nous obtenons notre liste trié
							addListProject(lstProjectTemp);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}

				});
		// sur le click du btn all project
		((Button) findViewById(R.id.cmdAllProject))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						addListProject(lstProject);
					}
				});
		// sur le choix d'un projet
		((ListView) findViewById(R.id.projectListView))
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent;
						if (idUser > 0) {
							//utilisateur connecté
							intent = new Intent(getApplicationContext(),
									ContributeActivity.class);
						} else {
							//utilisateur non connecté
							intent = new Intent(getApplicationContext(),
									ProjectDetailsActivity.class);
						}
						intent.putExtra("project", lstProject.get(arg2));
						startActivity(intent);
					}
				});
		
		////////////bar menu///////////////
		 Button profilMenuButton = (Button) findViewById(R.id.profilMenuButton);
		 Button addProjectMenuButton = (Button) findViewById(R.id.addProjectMenuButton);
		 Button logoutMenuButton = (Button) findViewById(R.id.logoutMenuButton);
		 //pour voir le profil
		 profilMenuButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {	
				Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
				intent.putExtra("idUser", idUser);
				startActivity(intent);
			}
		});
		 //pour ajouter un projet
		 addProjectMenuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AddProjectActivity.class);
				intent.putExtra("idUser",idUser);
				startActivity(intent);
				finish();
			}
		});
		 //pour ce déconnecter de son compte et revenir au login
		 logoutMenuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				idUser = -1;
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RESULT_OK:
			Bundle extra = data.getExtras();
			if (extra != null) {
				this.idUser = extra.getInt("idUser");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.menu.register:
			intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			finish();
			Log.e("Test", "menu register");
			break;
		case R.menu.login:
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			Log.e("Test", "menu login");
			break;
		case R.menu.add_project:
			intent = new Intent(this, AddProjectActivity.class);
			startActivity(intent);
			finish();
			Log.e("Test", "menu add project");
			break;
		case R.menu.profil:
			intent = new Intent(this, ProfilActivity.class);
			startActivity(intent);
			finish();
			Log.e("Test", "menu profil");
			break;
		}
		return false;
	}

	public void init() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				WebService service = new WebService();
				lstCategory = service.getAllCategory();
				lstProject = service.getAllProjects();
				addListCategory();
				addListProject(lstProject);
			}
		}).start();
	}

	private void addListProject(final ArrayList<Project> lst) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				LstProjectAdpater adapterProject = new LstProjectAdpater(
						IndexActivity.this, lst);
				ListView lst = (ListView) findViewById(R.id.projectListView);
				lst.setAdapter(adapterProject);
			}
		});

	}

	private void addListCategory() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				List<String> listCategory = new ArrayList<String>();
				// listCategory.add("");//index0
				// listCategory.add("Tout les projets");
				for (Category c : lstCategory) {
					listCategory.add(c.getLibelle());
				}
				ArrayAdapter<String> adpaterCategory = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_dropdown_item_1line,
						listCategory);
				((Spinner) findViewById(R.id.categoryListView))
						.setAdapter(adpaterCategory);
			}
		});
	}
}
