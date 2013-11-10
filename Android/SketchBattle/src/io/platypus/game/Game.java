package io.platypus.game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

public class Game implements Parcelable {

	public static final String FIRBASE_GAME_URI = "https://platypus-launchhack.firebaseio.com";
	
	protected String id;
	
	private Firebase firebase;
	
	private Player current_player;
	
	private String answer;
	
	private static final String ROLE_GUESSER  = "guesser";
	private static final String ROLE_DRAWER   = "drawer";
	
	private String current_player_role;
	
	private int no_guesses = 0;
	
	public Game(String id) {
		this.id = id;
		firebase = new Firebase(FIRBASE_GAME_URI);
		
		// Get the answer
		firebase.child("games/" + this.id + "/word").addListenerForSingleValueEvent(new ValueEventListener() {	
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				answer = (String) snapshot.getValue();
			}
			
			@Override
			public void onCancelled() {}
		});
		
		firebase.child("games/" + this.id + "/players/" + current_player.getId() + "/role")
			.addListenerForSingleValueEvent(new ValueEventListener() {
				
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				current_player_role = (String) snapshot.getValue();
				Log.e("ROLE", current_player_role);
			}
				
			@Override
			public void onCancelled() {}
		});
	}

	public Game(Parcel obj) {
		this.id = obj.readString();
		this.current_player = obj.readParcelable(Player.class.getClassLoader());
		this.answer = obj.readString();
		
		/*if(this.answer == null || this.answer.length() <= 0) {
			// Get the answer
			firebase.child("word").addListenerForSingleValueEvent(new ValueEventListener() {	
				@Override
				public void onDataChange(DataSnapshot snapshot) {
					answer = (String) snapshot.getValue();
				}
				
				@Override
				public void onCancelled() {}
			});			
		}*/
		firebase = new Firebase(FIRBASE_GAME_URI);
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
		
		player_node.child("role").addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				current_player_role = (String) snapshot.getValue();
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
	
	public void addPoint(float x, float y, int colorID, boolean isEnd) {
		Firebase points_node = firebase.getRoot().child("games/" + this.getId() + "/points");
		Firebase new_point_node = points_node.push();
		
		Map<String, Object> firebaseMap = new HashMap<String, Object>();
		
		firebaseMap.put("x", x);
		firebaseMap.put("y", y);
		firebaseMap.put("color_id", colorID);
		firebaseMap.put("is_end", isEnd);
		firebaseMap.put("player_id", current_player.getId());
		new_point_node.setValue(firebaseMap);	
	}
	
	public String getAnswer() {
		return this.answer;
	}
	
	public boolean checkAnswer(String guess) {
		++no_guesses;
		if(guess.toLowerCase() == answer.toLowerCase()) {
			return true;
		}
		return false;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeParcelable(getCurrentPlayer(), 0);
		dest.writeString(this.answer);
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
