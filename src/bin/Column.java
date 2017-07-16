package bin;

import java.util.Random;

public class Column extends BaseObject {
	
	public static final int RANGE=140;
	
	private int ceny;
	
	Column(int n){
		bg=FlappyBird.column;
		x=240+(n-1)*260;
		Random r=new Random();
		ceny=r.nextInt(370)+70;
		width=bg.getWidth();
		height=bg.getHeight();
		y=ceny-height/2;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		x-=FlappyBird.XSPEED;

	}
	@Override
	public boolean outOfBounds() {
		return x<-bg.getWidth();
	}
	public void reset(int cx) {
		x=cx+260;
		Random r=new Random();
		y=r.nextInt(220)+220-height/2;
	}

}
