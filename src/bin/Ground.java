package bin;

public class Ground extends BaseObject {
	
	Ground(){
		
		bg=FlappyBird.groundpicture;
		x=0;
		y=FlappyBird.background.getHeight()-bg.getHeight();
		
	}
	

	@Override
	public void step() {
		// TODO Auto-generated method stub
		x-=FlappyBird.XSPEED;

	}
	@Override
	public boolean outOfBounds() {
		return x<-100;
	}
	public void reset() {
		x=0;
	}

}
