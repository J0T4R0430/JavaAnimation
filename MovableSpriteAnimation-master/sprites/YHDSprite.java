import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class YHDSprite implements MovableSprite, DisplayableSprite {
	
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

	private final double ACCELERATION = 2;
    private double velocityX = 0;
    private double velocityY = 0;
    private final double MAX_ACCELERATION = 125;
    private String direction = "r";
    private String filename = "res/tankup.png";
	
	public YHDSprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;
		
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
//        if (this.direction.equals("u")) {
//            this.filename = "res/tankup.png";
//        } else if (this.direction.equals("d")) {
//            this.filename = "res/tankdown.png";
//        } else if (this.direction.equals("l")) {
//            this.filename = "res/tankleft.png";
//        } else if (this.direction.equals("r")){
//            this.filename = "res/tankright.png";
//        } else {
//            
//        }
//        try {
//            image = ImageIO.read(new File(filename));
//        }
//        catch (IOException e) {
//            System.out.println(e.toString());
//        }
//		return image;
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
	    double multiplier = 1;
	    //LEFT  
        if (keyboard.keyDown(37)) {
            currentAngle -= (ROTATION_SPEED * (actual_delta_time * 0.001));
            multiplier = 1;
        }
        // RIGHT
        if (keyboard.keyDown(39)) {
            currentAngle += (ROTATION_SPEED * (actual_delta_time * 0.001));
            multiplier = 1;
        }
        if (keyboard.keyDown(40)) {
            
            velocityX += Math.cos(angleInRadians) * ACCELERATION;
            velocityY += Math.sin(angleInRadians) * ACCELERATION;
        }       // DOWN
        else if (keyboard.keyDown(38)) {
            velocityX -= Math.cos(angleInRadians) * ACCELERATION;
            velocityY -= Math.sin(angleInRadians) * ACCELERATION;
        } else {
            if (this.velocityX < 0) {
                this.velocityX += Math.min(ACCELERATION, 0-this.velocityX)*Math.abs(Math.cos(angleInRadians));
            } else {
                this.velocityX -= Math.min(ACCELERATION, this.velocityX)*Math.abs(Math.cos(angleInRadians));
            }
            if (this.velocityY < 0) {
                this.velocityY += Math.min(ACCELERATION, 0-this.velocityY)*Math.abs(Math.sin(angleInRadians));
            } else {
                this.velocityY -= Math.min(ACCELERATION, this.velocityY)*Math.abs(Math.sin(angleInRadians));
            }
        }
        
        if (currentAngle >= 360) {
            currentAngle -= 360;
        }
        if (currentAngle < 0) {
            currentAngle += 360;
        }   
        
        currentAngle %= 360;

//		if (keyboard.keyDown(37)) {
//		    this.direction = "l";
//		} else if (keyboard.keyDown(38)) {
//            this.direction = "u";
//        } else if (keyboard.keyDown(39)) {
//            this.direction = "r";
//        } else if (keyboard.keyDown(40)) {
//            this.direction = "d";
//        }else {
//            
//        }
//		//set velocity based on current state of the keyboard
//		
//		//LEFT ARROW
//		if (keyboard.keyDown(37) && velocityX > -MAX_ACCELERATION) {
//			velocityX -= ACCELERATION;
//		}
//		//RIGHT ARROW
//		else if (keyboard.keyDown(39) && velocityX < MAX_ACCELERATION) {
//            velocityX += ACCELERATION;
//        }
//		else {
//		    if (velocityX < -ACCELERATION) {
//		        velocityX += ACCELERATION;
//		    }
//		    else if (velocityX > ACCELERATION){
//		        velocityX -= ACCELERATION;
//		    }
//		    else {
//		        velocityX = 0;
//		    }
//		}
//		//UP ARROW
//		if (keyboard.keyDown(38) && velocityY > -MAX_ACCELERATION) {
//			velocityY -= ACCELERATION;			
//		}
//		
//		// DOWN ARROW
//		else if (keyboard.keyDown(40) && velocityY < MAX_ACCELERATION) {
//			velocityY += ACCELERATION;			
//		}
//		else {
//            if (velocityY < -ACCELERATION) {
//                velocityY += ACCELERATION;
//            }
//            else if (velocityY > ACCELERATION){
//                velocityY -= ACCELERATION;
//            }
//            else {
//                velocityY = 0;
//            }
//        }

		//calculate new position based on velocity and time elapsed
		this.centerX += actual_delta_time * 0.001 * velocityX * multiplier;
		this.centerY += actual_delta_time * 0.001 * velocityY * multiplier;
				
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
