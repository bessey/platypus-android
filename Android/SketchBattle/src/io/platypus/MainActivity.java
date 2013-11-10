package io.platypus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//   Intent colorActivity = new Intent(this, MatchMakerActivity.class);
         //  startActivity(colorActivity);
		
		String color = "#FF660000";
		Intent mainActivity = new Intent(this, GameActivity.class);
		mainActivity.putExtra("color", color);
    	String drawObject = "Dog";
    	mainActivity.putExtra("drawObject", drawObject);
        startActivity(mainActivity);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
