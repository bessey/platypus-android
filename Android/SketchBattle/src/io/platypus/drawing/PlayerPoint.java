package io.platypus.drawing;

public class PlayerPoint {
	
	
	
	public String id;
	public float x;
	public float y;
	public boolean isEnd;
	public boolean isPrevEnd = false;
	
	public PlayerPoint(String id , float x , float y , boolean isEnd )
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.isEnd = isEnd;
	}

}
