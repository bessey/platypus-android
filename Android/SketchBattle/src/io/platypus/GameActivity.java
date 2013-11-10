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
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
    private ImageButton menuButton;
    
    //sizes
    private float smallBrush, mediumBrush, largeBrush;

    private String drawObject;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //get drawing view
        drawView = (DrawingView)findViewById(R.id.drawing);

        
        
        
        //set draw item
        
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        String color = b.getString("color");
        
        drawView.setColor(color);
        
        //get the palette and first color button
     //   LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
      //  currPaint = (ImageButton)paintLayout.getChildAt(0);
     //   currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        //sizes from dimensions
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        //draw button
    //    drawBtn = (ImageButton)findViewById(R.id.draw_btn);
     //   drawBtn.setOnClickListener(this);

        //set initial size
        drawView.setBrushSize(mediumBrush);

        //erase button
    //    eraseBtn = (ImageButton)findViewById(R.id.erase_btn);
   //     eraseBtn.setOnClickListener(this);

        //new button
   //     newBtn = (ImageButton)findViewById(R.id.new_btn);
   //     newBtn.setOnClickListener(this);

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

    //user clicked paint
    public void paintClicked(View view){
        //use chosen color

        //set erase false
        drawView.setErase(false);
        drawView.setBrushSize(drawView.getLastBrushSize());

        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            //update ui
      //      imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
       //     currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint=(ImageButton)view;
        }
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

}