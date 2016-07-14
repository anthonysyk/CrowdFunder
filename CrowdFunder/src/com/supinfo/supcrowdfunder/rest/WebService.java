package com.supinfo.supcrowdfunder.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import android.util.Log;

import com.supinfo.supcrowdfunder.entity.Categories;
import com.supinfo.supcrowdfunder.entity.Category;
import com.supinfo.supcrowdfunder.entity.Contribution;
import com.supinfo.supcrowdfunder.entity.Project;
import com.supinfo.supcrowdfunder.entity.Projects;
import com.supinfo.supcrowdfunder.entity.Type;
import com.supinfo.supcrowdfunder.entity.Types;
import com.supinfo.supcrowdfunder.entity.User;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonParser;

public class WebService {
	private final String URL_CATEGORY = "http://jeanbaptisteth.zapto.org:8080/3JVA_SupCrowdfunder2/resources/categories";
	private final String URL_PROJECTS = "http://jeanbaptisteth.zapto.org:8080/3JVA_SupCrowdfunder2/resources/projects";
	private final String URL_TYPE = "http://jeanbaptisteth.zapto.org:8080/3JVA_SupCrowdfunder2/resources/types";
	private final String URL_USER = "http://jeanbaptisteth.zapto.org:8080/3JVA_SupCrowdfunder2/resources/users";
	private final String URL_CONTRIBUTION = "http://jeanbaptisteth.zapto.org:8080/3JVA_SupCrowdfunder2/resources/contributions";
	private JsonFactory factory = new JsonFactory();
	private ObjectMapper objectMapper = new ObjectMapper();
	private HttpURLConnection urlConnection;

	public WebService() {
	}

	/**
	 * envoie un requête au serveur pour récuperer le flux de données
	 * 
	 * @param URL
	 *            url
	 * @return InputStream
	 * @throws Exception
	 */
	private InputStream sendRequestGet(URL url) throws Exception {
		try {
			// Ouverture de la connexion
			urlConnection = (HttpURLConnection) url
					.openConnection();
			// Connexion à l'URL
			urlConnection.connect();
			//urlConnection.setRequestMethod("GET");
			// Si le serveur nous répond avec un code OK
			if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return urlConnection.getInputStream();
			}
		} catch (Exception e) {
			throw new Exception("Impossible de ce connecter");
		}
		return null;
	}

	/**
	 * envoie une requête au serveur pour envoyer un flux de données
	 * 
	 * @param params
	 * @return boolean
	 * @throws Exception
	 */
	private boolean sendRequestPost(String url, String params) throws Exception {
		InputStream inputStream = null;
		boolean result = false;
		try {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		StringEntity se = new StringEntity(params);
		httpPost.setEntity(se);
		
		httpPost.setHeader("Accept","application/json");
		httpPost.setHeader("Content-type","application/json");
		HttpResponse httpResponse = httpClient.execute(httpPost);
		inputStream = httpResponse.getEntity().getContent();
		if(inputStream != null){
			result = true;
		}
		}catch(IllegalArgumentException e){
			Log.e("Erreur", "Problème lors des données transmis au serveur.");
		}
		catch (Exception e) {
			Log.e("Erreur", "Problème lors de l'envoie des données au serveur.");
		}
		return result;
	}

	// ////////////////////////////////////////////////////Getter
	// service/////////////////////////////////////////////////////
	/**
	 * getter all project
	 * 
	 * @return ArrayList<Project>
	 */
	public ArrayList<Project> getAllProjects() {
		ArrayList<Project> lstProject = new ArrayList<Project>();
		try {
			Projects projects = null;
			InputStream stream = sendRequestGet(new URL(URL_PROJECTS));
			if (stream != null) {
				InputStreamReader reader = new InputStreamReader(stream);
				JsonParser jp = factory.createJsonParser(reader);
				projects = objectMapper.readValue(jp, Projects.class);
				lstProject = projects.get("project");
				reader.close();
			}
			stream.close();
			this.urlConnection.disconnect();
		} catch (MalformedURLException e) {
			Log.e("Erreur", "Erreur sur l'adresse url.");
		} catch (IOException e) {
			Log.e("Erreur", "Impossible de lire le flux des données. " + e.getMessage());
		} catch (Exception e) {
			Log.e("Erreur", "Impossible de lire le flux des données. " + e.getMessage());
		}
		return lstProject;
	}

	/**
	 * getter de project
	 * 
	 * @param int idProject
	 * @return Project
	 */
	public Project getProjectById(int idProject) {
		Project project = null;
		try {
			InputStream stream = sendRequestGet(new URL(URL_PROJECTS + "/"
					+ String.valueOf(idProject)));
			if (stream != null) {
				InputStreamReader reader = new InputStreamReader(stream);
				JsonParser jp = factory.createJsonParser(reader);
				project = objectMapper.readValue(jp, Project.class);
				reader.close();
			}
			stream.close();
			this.urlConnection.disconnect();
		} catch (MalformedURLException e) {
			Log.e("Erreur", "Erreur sur l'adresse url.");
		} catch (IOException e) {
			Log.e("Erreur", "Erreur sur le flux de données.");
		} catch (Exception e) {
			Log.e("Erreur", "Impossible de lire le flux des données.");
		}
		return project;
	}

	/**
	 * getter liste de project suivant sa categorie
	 * 
	 * @param int idCategory
	 * @return ArrayList<Project>
	 */
	public ArrayList<Project> getAllProjectByCategory(int idCategory) {
		ArrayList<Project> lstproject = new ArrayList<Project>();
		Projects projects = null;
		try {
			InputStream stream = sendRequestGet(new URL(URL_PROJECTS
					+ "/search/" + String.valueOf(idCategory)));
			if (stream != null) {
				InputStreamReader reader = new InputStreamReader(stream);
				JsonParser jp = factory.createJsonParser(reader);
				projects = objectMapper.readValue(jp, Projects.class);
				lstproject = projects.get("project");
				reader.close();
			}
			stream.close();
			this.urlConnection.disconnect();
		} catch (MalformedURLException e) {
			Log.e("Erreur", "Erreur sur l'adresse url.");
		} catch (IOException e) {
			Log.e("Erreur", "Impossible de lire le flux des données.");
		} catch (Exception e) {
			Log.e("Erreur", "Impossible de lire le flux des données.");
		}
		return lstproject;
	}

	/**
	 * liste des categories
	 * 
	 * @return Arraylist<Category>
	 */
	public ArrayList<Category> getAllCategory() {
		ArrayList<Category> lstCategory = new ArrayList<Category>();
		try {
			Categories categories = null;
			InputStream stream = sendRequestGet(new URL(URL_CATEGORY));
			if (stream != null) {
				InputStreamReader reader = new InputStreamReader(stream);
				JsonParser jp = factory.createJsonParser(reader);
				categories = objectMapper.readValue(jp, Categories.class);
				lstCategory = categories.get("category");
				reader.close();
			}
			stream.close();
			this.urlConnection.disconnect();
		} catch (MalformedURLException e) {
			Log.e("Erreur", "Erreur sur l'adresse url.");
		} catch (IOException e) {
			Log.e("Erreur", "Impossible de lire le flux des données.");
		} catch (Exception e) {
			Log.e("Erreur", "Impossible de lire le flux des données.");
		}
		return lstCategory;
	}

	/**
	 * getter Categorie
	 * 
	 * @param int idCategory
	 * @return Category
	 */
	public Category getCategoryById(int idCategory) {
		Category category = null;
		try {
			InputStream stream = sendRequestGet(new URL(URL_CATEGORY + "/"
					+ String.valueOf(idCategory)));
			if (stream != null) {
				InputStreamReader reader = new InputStreamReader(stream);
				JsonParser jp = factory.createJsonParser(reader);
				category = objectMapper.readValue(jp, Category.class);
				reader.close();
			}
			stream.close();
			this.urlConnection.disconnect();
		} catch (MalformedURLException e) {
			Log.e("Erreur", "Erreur sur l'adresse url.");
		} catch (IOException e) {
			Log.e("Erreur", "Impossible de lire le flux des données.");
		} catch (Exception e) {
			Log.e("Erreur", "Impossible de lire le flux des données.");
		}
		return category;
	}

	/**
	 * liste des types d'utilisateurs
	 * 
	 * @return ArrayList<Type>
	 */
	public ArrayList<Type> getAllType() {
		ArrayList<Type> lstType = new ArrayList<Type>();
		try {
			Types types = null;
			InputStream stream = sendRequestGet(new URL(URL_TYPE));
			if (stream != null) {
				InputStreamReader reader = new InputStreamReader(stream);
				JsonParser jp = factory.createJsonParser(reader);
				types = objectMapper.readValue(jp, Types.class);
				lstType = types.get("type");
				reader.close();
			}
			stream.close();
			this.urlConnection.disconnect();
		} catch (MalformedURLException e) {
			Log.e("Erreur", "Erreur sur l'adresse url.");
		} catch (IOException e) {
			Log.e("Erreur", "Impossible de lire le flux des données.");
		} catch (Exception e) {
			Log.e("Erreur", "Impossible de lire le flux des données.");
		}
		return lstType;
	}

	/**
	 * récupere un utilisateur
	 * 
	 * @param String
	 *            email
	 * @param String
	 *            password
	 * @return User
	 */
	public User getUser(String email, String password) {
		User user = null;
		try {
			InputStream stream = sendRequestGet(new URL(URL_USER
					+ "/search/" + email + "/" + password));
			if (stream != null) {
				InputStreamReader reader = new InputStreamReader(stream);
				JsonParser jp = factory.createJsonParser(reader);
				user = objectMapper.readValue(jp, User.class);
				reader.close();
			}
			stream.close();
			this.urlConnection.disconnect();
		} catch (MalformedURLException e) {
			Log.e("Erreur", "Erreur sur l'adresse url." + e.getMessage());
		} catch (IOException e) {
			Log.e("Erreur", "Impossible de lire le flux des données." + e.getMessage());
		} catch (Exception e) {
			Log.e("Erreur", "Impossible de lire le flux des données." + e.getMessage());
		}
		return user;
	}
	public User getUserById(int idUser) {
		User user = null;
		try {
			InputStream stream = sendRequestGet(new URL(URL_USER
					+ "/" + idUser ));
			if (stream != null) {
				InputStreamReader reader = new InputStreamReader(stream);
				JsonParser jp = factory.createJsonParser(reader);
				user = objectMapper.readValue(jp, User.class);
				reader.close();
			}
			stream.close();
			this.urlConnection.disconnect();
		} catch (MalformedURLException e) {
			Log.e("Erreur", "Erreur sur l'adresse url." + e.getMessage());
		} catch (IOException e) {
			Log.e("Erreur", "Impossible de lire le flux des données." + e.getMessage());
		} catch (Exception e) {
			Log.e("Erreur", "Impossible de lire le flux des données." + e.getMessage());
		}
		return user;
	}

	// ////////////////////////////////////////////////////////////////Setter
	// service//////////////////////////////////////////////////////////
	/**
	 * permet d'ajouter un nouvelle utilisateur
	 * 
	 * @param user
	 */
	public boolean addUser(User user) {
		ObjectMapper mapper = new ObjectMapper();
		boolean result = false;
		try {
			String u = mapper.writeValueAsString(user);
			result = sendRequestPost(URL_USER +"/add",u);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * permet d'ajouter un nouveau projet
	 * 
	 * @param project
	 */
	public boolean addProject(Project project) {
		ObjectMapper mapper = new ObjectMapper();
		boolean result = false;
		try {
			String p = mapper.writeValueAsString(project);
			result = sendRequestPost(URL_PROJECTS + "/add",p);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * permet d'ajouter une contribution au project
	 * 
	 * @param contribution
	 */
	public boolean addContribution(Contribution contribution) {
		ObjectMapper mapper = new ObjectMapper();
		boolean result = false;
		try {
			String contribu = mapper.writeValueAsString(contribution);
			result = sendRequestPost(URL_CONTRIBUTION +"/add",contribu);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	///////////////////////////////////////////////////////////////update///////////////////////////////////
	/**
	 * Permet de mettre à jour des infomations de l'utilisateur
	 * @param user
	 */
	public void updateUser(User user){
		
	}
}
