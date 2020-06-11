package Utils;

import Utils.FXImageUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class UserHeaderPane extends Pane {

	Canvas canvas = new Canvas();
	Image image;

	public UserHeaderPane(){
		getChildren().add(canvas);
	}

	public void setImage(Image image){
		this.image = image;
		update();
	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		canvas.setWidth(w);
		canvas.setHeight(h);
		canvas.resizeRelocate(0,0, w, h);
		update();

	}

	private void update(){
		if (image==null) return;

		double w = getWidth();
		double h = getHeight();

		double imageW = image.getWidth();
		double imageH = image.getHeight();

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0,0, w, h);

		double radius = w/2;
		if( radius > h) radius = h/2;
		radius -= -4;

		gc.save();
		gc.beginPath();
		gc.arc(w/2, h/2, radius, radius, 0,360);

		gc.clip();

		Rectangle2D source = new Rectangle2D(0, 0, image.getWidth(), image.getHeight());
		Rectangle2D target = new Rectangle2D(0, 0, w, h);
		Rectangle2D fitRect = FXImageUtils.centerInside(source, target);
		drawImage(gc, image, fitRect);

		gc.restore();
	}

	private void drawImage(GraphicsContext gc, Image image, Rectangle2D r){
		gc.drawImage(image,
				0, 0, image.getHeight(),image.getHeight(),
				 r.getMaxX(), r.getMaxY(), r.getWidth(), r.getHeight());
	}
}
