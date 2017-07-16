package bin;

public class Bird extends BaseObject {
	public static final double G=4;
	public static final double V0=20;
	public static final double INTERVEL=0.25;
	double speed;
	double alpha;
	double s;
	
	Bird(){
		bg=FlappyBird.birdpicture;
		width=bg.getWidth();
		height=bg.getHeight();
		x=130;
		y=280;
		s=0;
		alpha=0;
		speed=0;
	}

	@Override
	public void step(){
		// TODO Auto-generated method stub
		s=speed*INTERVEL-G*INTERVEL*INTERVEL;
		y-=(int)s;
		speed-=G*INTERVEL;
		alpha=Math.atan(s/8);

	}
	
	public void flappy() {
		speed=V0;
	}
	
	public boolean hit(Column c) {
		
		int ceny=y+height/2;
		
		int cceny=c.getY()+c.getHeight()/2;
		return x>c.getX()&&x<c.getX()+c.getWidth()&&!(Math.abs(ceny-cceny)<(c.RANGE-height)/2);
		
	}
	public boolean hit(Ground g) {
		return y>g.getY();
		
	}
	
	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}

}
