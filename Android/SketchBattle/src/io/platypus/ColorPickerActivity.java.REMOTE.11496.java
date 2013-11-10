package io.platypus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ColorPickerActivity extends Activity implements OnClickListener {

	private String drawObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.color_picker);

		// Intent gameIntent = new Intent(this, GameActivity.class);
		// startActivity(gameIntent);

		setUpHandlers();

	}

	private void setUpHandlers() {
		// View contentsButton = findViewById(R.id.btn_contents);
		// contentsButton.setOnClickListener(this);

		View colorbtn_dark_brown = findViewById(R.id.colorbtn_dark_brown);
		colorbtn_dark_brown.setOnClickListener(this);
		View colorbtn_red = findViewById(R.id.colorbtn_red);
		colorbtn_red.setOnClickListener(this);
		View colorbtn_orange = findViewById(R.id.colorbtn_orange);
		colorbtn_orange.setOnClickListener(this);
		View colorbtn_yellow = findViewById(R.id.colorbtn_yellow);
		colorbtn_yellow.setOnClickListener(this);
		View colorbtn_green = findViewById(R.id.colorbtn_green);
		colorbtn_green.setOnClickListener(this);
		View colorbtn_light_blue = findViewById(R.id.colorbtn_light_blue);
		colorbtn_light_blue.setOnClickListener(this);

		View colorbtn_dark_blue = findViewById(R.id.colorbtn_dark_blue);
		colorbtn_dark_blue.setOnClickListener(this);
		View colorbtn_purple = findViewById(R.id.colorbtn_purple);
		colorbtn_purple.setOnClickListener(this);
		View colorbtn_pink = findViewById(R.id.colorbtn_pink);
		colorbtn_pink.setOnClickListener(this);
		View colorbtn_white = findViewById(R.id.colorbtn_white);
		colorbtn_white.setOnClickListener(this);
		View colorbtn_grey = findViewById(R.id.colorbtn_grey);
		colorbtn_grey.setOnClickListener(this);
		View colorbtn_black = findViewById(R.id.colorbtn_black);
		colorbtn_black.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View v) {
		try {
			View colorbutton;
			switch (v.getId()) {
			case R.id.colorbtn_dark_brown:
				colorbutton = findViewById(R.id.colorbtn_dark_brown);
				colorSelected(colorbutton);
				finish();
				break;
			case R.id.colorbtn_red:
				colorbutton = findViewById(R.id.colorbtn_red);
				colorSelected(colorbutton);
				finish();
				break;
			case R.id.colorbtn_orange:
				colorbutton = findViewById(R.id.colorbtn_orange);
				colorSelected(colorbutton);

				break;
			case R.id.colorbtn_yellow:
				colorbutton = findViewById(R.id.colorbtn_yellow);
				colorSelected(colorbutton);
				break;
			case R.id.colorbtn_green:
				colorbutton = findViewById(R.id.colorbtn_green);
				colorSelected(colorbutton);
				break;
			case R.id.colorbtn_light_blue:
				colorbutton = findViewById(R.id.colorbtn_light_blue);
				colorSelected(colorbutton);
				break;
			case R.id.colorbtn_dark_blue:
				colorbutton = findViewById(R.id.colorbtn_dark_blue);
				colorSelected(colorbutton);
				break;
			case R.id.colorbtn_purple:
				colorbutton = findViewById(R.id.colorbtn_purple);
				colorSelected(colorbutton);
				break;
			case R.id.colorbtn_pink:
				colorbutton = findViewById(R.id.colorbtn_pink);
				colorSelected(colorbutton);
				break;
			case R.id.colorbtn_white:
				colorbutton = findViewById(R.id.colorbtn_white);
				colorSelected(colorbutton);
				break;
			case R.id.colorbtn_grey:
				colorbutton = findViewById(R.id.colorbtn_grey);
				colorSelected(colorbutton);
				break;
			case R.id.colorbtn_black:
				colorbutton = findViewById(R.id.colorbtn_black);
				colorSelected(colorbutton);
				break;
			}
		} catch (Exception ex) {
			Context context = getApplicationContext();
			CharSequence text = ex.toString();
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

	private void colorSelected(View colorbutton) {
		String color = colorbutton.getTag().toString();

		Intent gameIntent = new Intent(this, GameActivity.class);
		gameIntent.putExtra("color", color);
		String drawObject = "Dog";
		gameIntent.putExtra("drawObject", drawObject);
		startActivity(gameIntent);

	}

}
