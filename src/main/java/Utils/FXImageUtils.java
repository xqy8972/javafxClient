package Utils;

import javafx.geometry.Rectangle2D;

public class FXImageUtils
{

	public static Rectangle2D fitXY(Rectangle2D source, Rectangle2D target)
	{
		return target;
	}
		
	public static Rectangle2D fitCenter(Rectangle2D source, Rectangle2D target)
	{
		double target_w = target.getWidth();
		double target_h = target.getHeight();

		double image_w = source.getWidth();
		double image_h = source.getHeight();

		if(image_w <= 0) image_w = 1;
		if(image_h <= 0) image_h = 1;

		double scaled_w = target_w;
		double scaled_h = image_h * target_w / image_w;
		if(scaled_h > target_h)
		{
			scaled_h = target_h;
			scaled_w = target_h * image_w / image_h;
		}

		// 坐在中心
		double x = (target_w - scaled_w)/2;
		double y = (target_h - scaled_h)/2;
		x += target.getMinX();
		y += target.getMinY();
		return new Rectangle2D(x, y, scaled_w, scaled_h);
	}
	

	public static Rectangle2D centerInside(Rectangle2D source, Rectangle2D target)
	{
		if( source.getWidth() > target.getWidth() || source.getHeight() > target.getHeight())
			return fitCenter(source, target);
		
		double x = ( target.getWidth() - source.getWidth() ) / 2;
		double y = ( target.getHeight() - source.getHeight() ) / 2;
		x += target.getMinX();
		y += target.getMinY();
		
		return new Rectangle2D(x, y, source.getWidth(), source.getHeight());		
	}
	

	public static Rectangle2D centerCrop(Rectangle2D source, Rectangle2D target)
	{
		return null;
	}
	
}
