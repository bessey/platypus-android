package io.platypus.game;

import java.io.IOException;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

public class Game {

	private static final String FIRBASE_GAME_URI = "https://platypus-launchhack.firebaseio.com";
	
	protected String id;
	
	private Firebase firebase;
	
	public Game(String id) {
		this.id = id;
		firebase = new Firebase(FIRBASE_GAME_URI);
	}
	
	public void addPlayer(Player player) throws IOException {	
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
}
