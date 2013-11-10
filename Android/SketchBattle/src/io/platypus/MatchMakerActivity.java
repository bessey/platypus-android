package io.platypus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.TextView;



public class MatchMakerActivity extends Activity {

	
	private int dotCount = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting_view);
		
		
		   //MatchMaking Logic here?
		
		
		waitingTextAnimation();
		matchmakingLogic(); // ?
		//moveToColorScreen();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	
	
	private void matchmakingLogic()
	{
		final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
           //    buttons[inew][jnew].setBackgroundColor(Color.BLACK);
            	moveToColorScreen();
            }
        }, 4000);
	}
	

	
	private void waitingTextAnimation()
	{
		final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
           //    buttons[inew][jnew].setBackgroundColor(Color.BLACK);
            	TextView findingText = (TextView)findViewById(R.id.drawobject_text);
            	
            	String ftext = "Finding game";
        		
        		for(int i = 0 ; i <dotCount ; i++)
        		{
        			ftext+= ".";
        		}
        		
            	findingText.setText(ftext);
            	
            	dotCount++;
            	
            	if(dotCount > 3)
            	{
            		dotCount = 1; 
            		}
            	waitingTextAnimation();
            }
        }, 800);
	}
	
	private void moveToColorScreen()
	{
		Intent colorActivity = new Intent(this, ColorPickerActivity.class);
		
		
		// Object to draw
		String drawObject = "Dog";
		colorActivity.putExtra("drawObject", drawObject);
		
        startActivity(colorActivity);
	}
	
}
