import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class tankbodySprite implements MovableSprite, DisplayableSprite {
	
	//a sprite that can be displayed and moves based on its own polling of the keyboard object

    private static Image[] rotatedImages = new Image[360];
    private Image rotatedImage;
    private double ROTATION_SPEED = 120;    //degrees per second    
    private double currentAngle = 0;
    private int currentImageAngle = 0;
	private static Image image;	
	
	
	private double centerX = 0;
	private double centerY = 0;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;	

	private final double FORWARD = 2;
	private final double DECELERATION = 1.5 * FORWARD;
    private final double MAX_VELOCITY = 200;
    private final double BRAKE = 2.5 * FORWARD;
    private final double BACKWARD = 1;
    private String filename = "res/tankbody.png";
    private double velocity = 0;
	private double velocityX = 0;
	private double velocityY = 0;
	
	public tankbodySprite() {
		if (image == null) {
			try {
				image = ImageIO.read(new File(filename));
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}
		if (image != null) {
            for (int i = 0; i < 360; i++) {
                rotatedImages[i] = ImageRotator.rotate(image, i);           
            }
        }
	}


    public Image getImage() {
        return rotatedImages[(int)currentAngle];
		
	}
	
	//DISPLAYABLE
	
	public boolean getVisible() {
		return true;
	}
	
	public double getMinX() {
		return centerX - (width / 2);
	}

	public double getMaxX() {
		return centerX + (width / 2);
	}

	public double getMinY() {
		return centerY - (height / 2);
	}

	public double getMaxY() {
		return centerY + (height / 2);
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getCenterX() {
		return width/2;
	};

	public double getCenterY() {
		return height/2;
	};
	
	
	public boolean getDispose() {
		return dispose;
	}

	public void setDispose(boolean dispose) {
		this.dispose = dispose;
	}


	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		
	    double angleInRadians = Math.toRadians(currentAngle);
	    //LEFT  
        if (keyboard.keyDown(37)) {
            currentAngle -= (ROTATION_SPEED * (actual_delta_time * 0.001));
        }
        // RIGHT
        if (keyboard.keyDown(39)) {
            currentAngle += (ROTATION_SPEED * (actual_delta_time * 0.001));
        }
        if ((keyboard.keyDown(38)) && this.velocity < this.MAX_VELOCITY) {
            this.velocity += FORWARD;
        }       // DOWN
        else if ((keyboard.keyDown(40)) && this.velocity > - this.MAX_VELOCITY) {
        	if (this.velocity > 0) {
        		this.velocity -= BRAKE;
        	} else {
        		this.velocity -= BACKWARD;
        	}
        	
        } else {
        	if (this.velocity > 0) {
        		this.velocity -= Math.min(DECELERATION, this.velocity);
        	}
        	else {
        		this.velocity += Math.min(DECELERATION, -this.velocity);
        	}
        }
        
        if (currentAngle >= 360) {
            currentAngle -= 360;
        }
        if (currentAngle < 0) {
            currentAngle += 360;
        }   
        
        currentAngle %= 360;

		//calculate new position based on velocity and time elapsed
		this.centerX += actual_delta_time * 0.001 * this.velocity * Math.cos(angleInRadians);
		this.centerY += actual_delta_time * 0.001 * this.velocity * Math.sin(angleInRadians);
				
	}


    @Override
    public void setCenterX(double centerX) {
        // TODO Auto-generated method stub
        this.centerX = centerX;
        
    }


    @Override
    public void setCenterY(double centerY) {
        // TODO Auto-generated method stub
        this.centerY = centerY;
    }


    @Override
    public void setVelocityX(double pixelsPerSecond) {
        // TODO Auto-generated method stub
        this.velocityX = pixelsPerSecond;
    }


    @Override
    public void setVelocityY(double pixelsPerSecond) {
        // TODO Auto-generated method stub
        this.velocityY = pixelsPerSecond;
    }

}
