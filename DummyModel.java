import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {


	private static final int NBROFBALLS = 4;
	private final double areaWidth;
	private final double areaHeight;
	private double gravityConstant = 9.82;
	private ArrayList<Ball> myBalls;
	private double speedX = 5.0;
	private static final double maxRadius = 4.0;
	private static final double density = 5.1;

	private final Random r;


	public DummyModel(double width, double height) {
		r = new Random();
		this.areaWidth = width;
		this.areaHeight = height;
		this.myBalls = new ArrayList<Ball>();
		initBalls();
	}

	@Override
	public void tick(double deltaT) {
		for (Ball ball : myBalls) {
			checkForCollisions(ball);
			ball.setvY(ball.getvY()-gravityConstant*deltaT);
			ball.move(ball.getvX() * deltaT, ball.getvY() * deltaT);
		}
	}

	private void checkForCollisions(Ball ball) {
		checkWalls(ball);
		checkOtherBalls(ball);
	}

	private void checkOtherBalls(Ball original) {
		for (Ball ball: myBalls) {
			if(!ball.equals(original)){
				//check if colliding
				if(colliding(ball, original)){
					//do collision
					collide(ball, original);
				}
			}
		}
	}

	private boolean colliding(Ball a, Ball b) {
		return ((a.getRadius() + b.getRadius())-Math.sqrt(Math.pow(a.getX()-b.getX(),2)+Math.pow(a.getY() - b.getY(),2))>=0);
	}

	private void collide(Ball a, Ball b) {
		Vector aVect = a.getVect();
		Vector bVect = b.getVect();


	}

	private void checkWalls(Ball ball) {
		if (ball.getX() < ball.getRadius() || ball.getX() > areaWidth - ball.getRadius()) {
            ball.setvX(ball.getvX()*-1);
        }
		if (ball.getY() < ball.getRadius() || ball.getY() > areaHeight - ball.getRadius() || ball.getY() < ball.getRadius()) {
            ball.setvY(ball.getvY()*-1);
        }
	}


	/**
	 * Initializes the ball list
	 */
	private void initBalls() {
		for (int i = 0; i<NBROFBALLS; i++) {
			double radius = r.nextDouble()*maxRadius;
			double startX = r.nextDouble()*(areaWidth-40)+20;
			double startY = r.nextDouble()*(areaWidth-40)+20;
			double weight = radius*radius*3.14*density;
			double vX = r.nextDouble()*speedX;
			myBalls.add(new Ball(radius, startX,startY, weight, vX));
		}
	}



	@Override
	public List<Ball> getBalls() {
		return myBalls;
	}
}
