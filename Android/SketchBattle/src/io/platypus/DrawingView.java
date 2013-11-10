package io.platypus;

import io.platypus.drawing.DrawPoint;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;



public class DrawingView extends View {

    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //initial colour
    private int paintColor = 0xFF660000;
    private int colorNumber = 1;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    //brush sizes
    private float brushSize, lastBrushSize;
    //erase flag
    private boolean erase=false;
    
    private boolean isDrawing = false;
    

    private ArrayList<DrawPoint> drawPoints = new ArrayList<DrawPoint>();

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }
    
    
    public void setDrawing(boolean isDrawing)
    {
    	this.isDrawing = isDrawing;
    }

    //setup drawing
    private void setupDrawing(){

        //prepare for drawing and setup paint stroke properties
        brushSize = getResources().getInteger(R.integer.small_size);
        lastBrushSize = brushSize;
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    //size assigned to view
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    //draw the view - will be called after touch event
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }
    
    
    
    

    //register user touches as drawing action
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	if(isDrawing == true)
    	{
    	
        float touchX = event.getX();
        float touchY = event.getY();
        DrawPoint drawPoint;
        
        //respond to down, move and up events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                drawPoint = new DrawPoint(touchX , touchY ,colorNumber ,true );
                drawPoints.add(drawPoint);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                drawPoint = new DrawPoint(touchX , touchY ,colorNumber ,true );
                drawPoints.add(drawPoint);
                
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPoint = new DrawPoint(touchX , touchY ,colorNumber ,true );
                drawPoints.add(drawPoint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //redraw
        invalidate();
        
        
    	}
    	else
    	{
    		// InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    		//    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    	}
        
        return true;
    }

    //update Color
    public void setColor( int activeColorID){
        invalidate();
     //   colorNumber = activeColorID;
        paintColor = Color.parseColor(getColor(activeColorID));
        drawPaint.setColor(paintColor);
    }
    
 

    //set brush size
    public void setBrushSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    //get and set last brush size
    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize(){
        return lastBrushSize;
    }

    public ArrayList<DrawPoint> getDrawPoints()
    {
    	return drawPoints;
    }
    
    //set erase true or false
    public void setErase(boolean isErase){
        erase=isErase;
        if(erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else drawPaint.setXfermode(null);
    }
    
    
    
    public void addPoint(DrawPoint point , DrawPoint end)
    {
    	
    	String colorString= getColor(point.color);
    	
    	 int tempColor = Color.parseColor(colorString);
    	 
         Paint tempPaint = new Paint();
         tempPaint.setColor(tempColor);
         tempPaint.setAntiAlias(true);
         tempPaint.setStrokeWidth(brushSize);
         tempPaint.setStyle(Paint.Style.STROKE);
         tempPaint.setStrokeJoin(Paint.Join.ROUND);
         tempPaint.setStrokeCap(Paint.Cap.ROUND);
        
    	Path tempPath = new Path();
    	tempPath.moveTo(point.x, point.y);
    	tempPath.lineTo(end.x, end.y);
    	 drawCanvas.drawPath(tempPath, tempPaint);
    	
    	 
    	  //redraw
         invalidate();
    }
    
    
    private String getColor(int id)
    {
    	String color = "#000000";
    	
    	 switch (id) 
         {
                 case 1:
                	 color =  "#FF660000";
                	 break;
                 case 2:
                	 color = "#FFFF0000";
                	 break;
                 case 3:
                	 color = "#FFFF6600";
                	 break;
                 case 4:
                	 color = "#FFFFCC00";
                	 break;
                 case 5:
                	 color = "#FF009900";
                	 break;
                 case 6:
                	 color = "#FF009999";
                	 break;
                 case 7:
                	 color = "#FF0000FF";
                	 break;
                 case 8:
                	 color = "#FF990099";
                	 break;
                 case 9:
                	 color = "#FFFF6666";
                	 break;
                 case 10:
                	 color = "#FFFFFFFF";
                	 break;
                 case 11:
                	 color = "#FF787878";
                	 break;
                 case 12:
                	 color = "#FF000000";
                	 break;
         }
    	
    	return color;
    }
    

    //start new drawing
    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}