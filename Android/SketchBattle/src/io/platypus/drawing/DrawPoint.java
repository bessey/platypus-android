package io.platypus.drawing;




public class DrawPoint
{
	public float x;
	public float y;
	public int color;
	public boolean isEnd;
	public long timestamp;
	
	public DrawPoint(float x , float y , int color , boolean isEnd)
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.isEnd = isEnd;
		this.timestamp = System.currentTimeMillis();
		
	}
	
	


}