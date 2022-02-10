import java.awt.*;

public class Sprite2D {
	
	private double x,y;
	private double xSpeed = 0;
	private Image myImage;
	
	//Constructor
	public Sprite2D(Image i) {
		myImage = i;
		x = Math.random()*500;
		y = Math.random()*500;
	}
	
	//Public interface
	public void moveEnemy() {
		x += 10 * (Math.random() - Math.random());
		y += 10 * (Math.random() - Math.random());
		
		// set a border so that these aliens won't go out of the canvas.
		if(x < 0) {
			x = 0;
		} else if(x > 500) {
			x = 500;
		} else if(y < 50) {
			y = 50;
		} else if(y > 400) {
			y = 400;
		}
	}

	public void setPosition(double xx, double yy) {
		x = xx;
		y = yy;
		
	}
	
	
	public void movePlayer() {
		x += xSpeed;
	}
	
	public void setXSpeed (double dx) {
		xSpeed = dx;
	}
	
	public void paint(Graphics g) {
		g.drawImage(myImage, (int)x, (int)y, null);
	}
}
