package de.fhbielefeld.pmdungeon.quibble.util;

import com.badlogic.gdx.math.Vector2;

public class GeometryUtil
{
	public static boolean intersectLineSegments(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		//Convert vector 2 and 4 to direction vectors
		x2 -= x1;
		y2 -= y1;
		
		x4 -= x3;
		y4 -= y3;
		
		float crossRS = crossProduct(x2, y2, x4, y4);
		
		if(crossRS == 0.0F)
		{
			return false;
		}
		
		float t = crossProduct(x3 - x1, y3 - y1, x4 / crossRS, y4 / crossRS);
		float u = crossProduct(x3 - x1, y3 - y1, x2 / crossRS, y2 / crossRS);
		
		return u >= 0.0F && u <= 1.0F && t >= 0.0F && t <= 1.0F;
	}
	
	public static boolean intersectLineSegmentRectangle(float x1, float y1, float x2, float y2, float rx, float ry, float rw, float rh)
	{
		return intersectLineSegments(x1, y1, x2, y2, rx, ry, rx, ry + rh) || //Left rect side
			intersectLineSegments(x1, y1, x2, y2, rx + rw, ry, rx + rw, ry + rh) || //Right rect side
			intersectLineSegments(x1, y1, x2, y2, rx, ry + rh, rx + rw, ry + rh) || //Top rect side
			intersectLineSegments(x1, y1, x2, y2, rx, ry, rx + rw, ry); //Bottom rect side
	}
	
	public static float crossProduct(float x1, float y1, float x2, float y2)
	{
		return x1 * y2 - y1 * x2;
	}
	
	public static boolean pointProximity(Vector2 a, Vector2 b, float e)
	{
		boolean b1 = Math.abs(b.x - a.x) < e && Math.abs(b.y - a.y) < e;
		return b1;
	}
}
