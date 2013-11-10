package io.platypus;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import io.platypus.drawing.DrawTester;

import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {

    //custom drawing view
    private DrawingView drawView;
    //buttons
    private ImageButton currPaint;
    private ImageButton menuButton;
    
    //sizes
    private float  activeBrush;

   
    private int colorID;
    private boolean isDrawing;
    
    
    DrawTester drawtester;
    DrawTester drawtester2;
    DrawTester drawtester3;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //get drawing view
        drawView = (DrawingView)findViewById(R.id.drawing);

        drawView.setDrawing(false);

        //Extract Bundle
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        String color = b.getString("color");
        colorID = b.getInt("colorID" );
        
        drawView.setColor(colorID);
        activeBrush = getResources().getInteger(R.integer.small_size);
        
        //set initial size
        drawView.setBrushSize(activeBrush);

 
        
        drawtester = new DrawTester(drawView , 3);
        drawtester2 = new DrawTester(drawView , 7);
        drawtester3 = new DrawTester(drawView , 8);
        drawtester.autoDraw();
        drawtester2.autoDraw();
        drawtester3.autoDraw();
        
        //save button
        menuButton = (ImageButton)findViewById(R.id.btn_menu);
        menuButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
    @Override
    public void onClick(View view){
    	  switch (view.getId()) 
          {
                  case R.id.btn_menu:
                          Intent menuIntent = new Intent(this, MenuActivity.class);
                          startActivity(menuIntent);
                          break;
          }
    }
    
    
    public DrawingView getDrawView()
    {
    	return drawView;
    }

}