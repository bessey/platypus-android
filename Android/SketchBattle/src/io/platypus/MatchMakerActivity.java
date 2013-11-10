package io.platypus;

import java.io.IOException;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import io.platypus.game.Game;
import io.platypus.game.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MatchMakerActivity extends Activity {

	public static final String TAG = "MATCH MAKER";
	
	public Player currentPlayer;

	private int dotCount = 1;
	private float rot = 0f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		this.currentPlayer = (Player) intent.getParcelableExtra("player");

		setContentView(R.layout.waiting_view);
		
		
		View button = findViewById(R.id.btn_menu);
		button.setVisibility(View.GONE);

		try {
			matchMaker();
		} catch (IOException e) {
			Log.e("MatchMaker", e.getMessage());
		}
	//	waitingTextAnimation();
		waitingImageAnimation();
		// moveToColorScreen();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void matchMaker() throws IOException {		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Game game;
				try {
					game = currentPlayer.findNewGame();
//					game.addPlayer(currentPlayer);
					waitForGameParticipants(game);
					Log.e(TAG, game.getId());
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
			}
		}).start();
	}
	
	private void waitForGameParticipants(final Game game) {
		final Firebase game_endpoint = new Firebase(
				Game.FIRBASE_GAME_URI + "/games/" + game.getId() + "/players/" + currentPlayer.getId());

		ValueEventListener participantListener = new ValueEventListener() {	
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				String role = (String) snapshot.getValue();
				game.getCurrentPlayer().setRole(role);
				moveToColorScreen(game);
			}
			
			@Override
			public void onCancelled() {}
		};
		
		game_endpoint.addListenerForSingleValueEvent(participantListener);		
	}
	
	
	private void waitingTextAnimation() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Do something after 5s = 5000ms
				// buttons[inew][jnew].setBackgroundColor(Color.BLACK);
				TextView findingText = (TextView) findViewById(R.id.drawobject_text);
				ImageView imageView = (ImageView) findViewById(R.id.img_waiting);

				String ftext = "Finding game";

				for (int i = 0; i < dotCount; i++) {
					ftext += ".";
				}

				findingText.setText(ftext);

				dotCount++;

				if (dotCount > 3) {
					dotCount = 1;
				}
				waitingTextAnimation();
			}
		}, 800);
	}
	
	
	private void waitingImageAnimation() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ImageView imageView = (ImageView) findViewById(R.id.img_waiting);
				imageView.setRotation(rot);
				rot += 2f;
				
				waitingImageAnimation();
			}
		}, 20);
	}

	private void moveToColorScreen(Game game) {
		Intent colorActivity = new Intent(this, ColorPickerActivity.class);

		// Object to draw
		String drawObject = "Dog";
		colorActivity.putExtra("drawObject", drawObject);
		colorActivity.putExtra("game", game);

		startActivity(colorActivity);
		finish();
	}

}
