package io.platypus;

import java.io.IOException;

import io.platypus.game.Game;
import io.platypus.game.Player;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class LoginActivity extends Activity {

	private static final String TAG = "MainActivity";

	private UiLifecycleHelper uiHelper;

	private LoginButton facebookLoginButton;

	private Session facebookSession;

	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		facebookLoginButton = (LoginButton) findViewById(R.id.btnFacebookLogin);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		Log.e(TAG, state.toString());
		if (state.isOpened()) {
			Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if(user != null) {
						String user_id = user.getId();
						String name = user.getName();
						String profile_picture_url = "https://graph.facebook.com/" + user_id + "/picture";
						
						final Player player = new Player(user_id, name, profile_picture_url);
						goToMatchMake(player);
						
						// TODO: Move to MatchMaking activity						
					} else {
						Log.e(TAG, "User is null");
					}
				}
			});
			request.executeAsync();
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}
	
	protected void goToMatchMake(Player player) {
		Intent matchMakeActivity = new Intent(this, MatchMakerActivity.class);
		matchMakeActivity.putExtra("player", player);
        startActivity(matchMakeActivity);		
	}

	@Override
	public void onResume() {
		super.onResume();

		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
