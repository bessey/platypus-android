package io.platypus.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

public class Game implements Parcelable {

	public static final String FIRBASE_GAME_URI = "https://platypus-launchhack.firebaseio.com";
	
	protected String id;
	
	private Firebase firebase;
	
	private Player current_player;
	
	public Game(String id) {
		this.id = id;
		firebase = new Firebase(FIRBASE_GAME_URI);
	}
	
	public Game(Parcel obj) {
		this.id = obj.readString();
		this.current_player = obj.readParcelable(Player.class.getClassLoader());
	}
	
	public void addPlayer(Player player) throws IOException {	
		current_player = player;
		
		final Firebase game_node = firebase.getRoot().child("games/" + this.getId());
		Firebase player_node = firebase.getRoot().child("games/" + this.getId() + "/players/" + player.getId());

		player_node.setValue(player.getFirebaseMap());	
		game_node.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				Object value = snapshot.getValue();
				Long current_count = (Long) ((Map) value).get("player_count");
				++current_count;
				
				game_node.child("player_count").setValue(current_count);
			}
			
			@Override
			public void onCancelled() {}
		});
	}
	
	public String getId() {
		return this.id;
	}
	
	public Player getCurrentPlayer() {
		return this.current_player;
	}
	
	public void addPoint(float x, float y) {
		Firebase points_node = firebase.getRoot().child("games/" + this.getId() + "/points");
		Firebase new_point_node = points_node.push();
		
		Map<String, Float> firebaseMap = new HashMap<String, Float>();
		
		firebaseMap.put("x", x);
		firebaseMap.put("y", y);
		new_point_node.setValue(firebaseMap);		
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeParcelable(getCurrentPlayer(), 0);
	}
	
	// Parcelalisable
	public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
		public Game createFromParcel(Parcel in) { 
			return new Game(in);
		}   
		
		public Game[] newArray(int size) { 
			return new Game[size]; 
		} 
	};
}
