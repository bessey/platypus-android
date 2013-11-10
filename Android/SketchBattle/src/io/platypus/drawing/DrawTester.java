package io.platypus.drawing;

import java.util.Random;

import android.os.Handler;
import io.platypus.DrawingView;

public class DrawTester
{

	private DrawingView drawView;
	private int color = 3;
	
	private float lastX = 0.5f;
	private float lastY = 0.5f;
	
	Random random = new Random();
	
	public DrawTester(DrawingView drawView, int color)
	{
		this.drawView = drawView;
		this.color = color;
		
		lastX = random.nextFloat();
		lastY = random.nextFloat();
	}
	
	
	
	public void autoDraw()
	{
	
	
		final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	            // Do something after 5s = 5000ms
	       //    buttons[inew][jnew].setBackgroundColor(Color.BLACK);
	        	drawPoints();
	        	autoDraw();
	        }
	    }, 100);

	}
	
	
	
	private void drawPoints()
	{
		
		float xE = 0;		
		float yE = 0;
		float distance = 999;
		
		do
		{
			 xE = random.nextFloat();
			 yE = random.nextFloat();
			
		 distance = (float)Math.sqrt((lastX-xE)*(lastX-xE) + (lastY-yE)*(lastY-yE));
		
		}while(distance > 0.1f);
			
		DrawPoint drawPoint = new DrawPoint(lastX,lastY,color,false);
		DrawPoint drawPointE = new DrawPoint(xE,yE,color,false);
		
		lastX = xE;
		lastY = yE;
		
		drawView.addPoint(drawPoint,drawPoint);
	 
	}
	
}
