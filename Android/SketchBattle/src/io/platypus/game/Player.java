package io.platypus.game;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import android.os.Parcel;
import android.os.Parcelable;

import com.firebase.client.ServerValue;

public class Player implements Parcelable {
	
	private static final String MATCH_URI = "http://198.199.115.42:3000/match-make";

	protected String id;
	protected String name;
	protected String profile_picture_url;
	
	private String role;
	
	protected Game current_game;
	
	public Player(String id, String name, String profile_picture_url) {
		this.id = id;
		this.name = name;
		this.profile_picture_url = profile_picture_url;
	}
	
	public Player(Parcel obj) {
		this.id = obj.readString();
		this.name = obj.readString();
		this.profile_picture_url = obj.readString();
	}
	
	public Game findNewGame() throws IOException {
		URL matchUrl = new URL(MATCH_URI);
		HttpURLConnection matchConnection = (HttpURLConnection) matchUrl.openConnection();
		matchConnection.setConnectTimeout(300000);
		
		matchConnection.setRequestMethod("POST");
		matchConnection.setDoOutput(true);
		
		String postData = "fb_id=" + this.id;
		
		DataOutputStream out = new DataOutputStream(matchConnection.getOutputStream());
		out.writeBytes(postData);
		out.flush();
		out.close();
		
		int statusCode = matchConnection.getResponseCode();
		if(statusCode == HttpURLConnection.HTTP_OK) {
			// Get the game JSON and create the new game
			BufferedReader in = 
					new BufferedReader(new InputStreamReader(matchConnection.getInputStream()));
			StringBuffer response = new StringBuffer();
			String line;
			
			while((line = in.readLine()) != null) {
				response.append(line);
			}
			
			JSONParser parser = new JSONParser();
			Object rawObject = JSONValue.parse(response.toString());
			JSONObject jsonObject = (JSONObject) rawObject;
			
			Game g = new Game((String) jsonObject.get("game_id"));
			
			return g;
		} else {
			throw new IOException("Match returned: " + statusCode);
		}
	}
	
	public Map<String, Object> getFirebaseMap() {
		Map<String, Object> firebaseMap = new HashMap<String, Object>();
		firebaseMap.put("fb_id", this.id);
		firebaseMap.put("last_heartbeart", ServerValue.TIMESTAMP);
		
		return firebaseMap;
	}
	
	public String getId() {
		return this.id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.name);
		dest.writeString(this.profile_picture_url);
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
	
	// Parcelalisable
	public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
		public Player createFromParcel(Parcel in) { 
			return new Player(in);
		}   
		
		public Player[] newArray(int size) { 
			return new Player[size]; 
		} 
	};
}
