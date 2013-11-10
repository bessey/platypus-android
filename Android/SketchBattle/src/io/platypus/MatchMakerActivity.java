package io.platypus;

import java.io.IOException;

import io.platypus.game.Game;
import io.platypus.game.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MatchMakerActivity extends Activity {

	public static final String TAG = "MATCH MAKER";
	
	public Player currentPlayer;
	public Game currentGame;

	private int dotCount = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		this.currentPlayer = (Player) intent.getParcelableExtra("player");

		setContentView(R.layout.waiting_view);

		try {
			matchMaker();
		} catch (IOException e) {
			Log.e("MatchMaker", e.getMessage());
		}
		waitingTextAnimation();
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
					game.addPlayer(currentPlayer);
					Log.e(TAG, game.getId());
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
			}
		}).start();
	}

	private void waitingTextAnimation() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// Do something after 5s = 5000ms
				// buttons[inew][jnew].setBackgroundColor(Color.BLACK);
				TextView findingText = (TextView) findViewById(R.id.waiting_text);

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

	private void moveToColorScreen() {
		Intent colorActivity = new Intent(this, ColorPickerActivity.class);

		// Object to draw
		String drawObject = "Dog";
		colorActivity.putExtra("drawObject", drawObject);

		startActivity(colorActivity);
	}

}
